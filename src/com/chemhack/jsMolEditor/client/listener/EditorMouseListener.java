package com.chemhack.jsMolEditor.client.listener;

import com.google.gwt.user.client.ui.*;
import com.chemhack.jsMolEditor.client.renderer.CordTransformer;
import com.chemhack.jsMolEditor.client.controller.*;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Point2D;
import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.model.Bond;
import com.chemhack.jsMolEditor.client.math.Vector2D;

import java.util.Set;


public class EditorMouseListener implements MouseListener, MouseWheelListener {
    private EditorController controller;

    enum MouseStatus {
        onAtom, onBond, onWhiteSpace
    }

    boolean isMouseDown = false;
    boolean isClick = false;

    Point2D lastCord = new Point2D();
    Atom dragStartedAtom;
    MouseStatus mouseStatus = MouseStatus.onWhiteSpace;

    DrawBondModel drawBondModel = new DrawBondModel();
    MoveAtomModel moveAtomModel = new MoveAtomModel();
    DrawRingModel drawRingModel = new DrawRingModel();

    public EditorMouseListener(EditorController controller) {
        this.controller = controller;
    }

    public void onMouseDown(Widget sender, int x, int y) {
        isMouseDown = true;
        isClick = true;
        if (mouseStatus == MouseStatus.onAtom || ((controller.currentAction == EditorController.EditActions.drawSingleBond || controller.currentAction == EditorController.EditActions.drawDoubleBond || controller.currentAction == EditorController.EditActions.drawTrippleBond) && mouseStatus == MouseStatus.onWhiteSpace)) {
            Atom mouseAtom = controller.getClosestAtom(new Point2D(x, y));
            if (mouseAtom == null) { //Draw on blank canvas
                Point2D realCord = controller.graphCordToRealCord(new Point2D(x, y));
                mouseAtom = controller.getMolecule().addAtom("C", realCord.x, realCord.y, 0);
                Set<Atom> highLightedAtoms = controller.getRenderer().getRendererModel().getHighlightedAtoms();
                highLightedAtoms.clear();
                highLightedAtoms.add(mouseAtom);
                mouseStatus = MouseStatus.onAtom;
                controller.refreshView();
            }

            switch (controller.currentAction) {
                case drawSingleBond:
                    drawBondModel.bondType = 1;
                    drawBondModel.activated = true;
                    drawBondModel.startAtom = mouseAtom;
                    drawBondModel.newAtom = null;
                    break;
                case drawDoubleBond:
                    drawBondModel.bondType = 2;
                    drawBondModel.activated = true;
                    drawBondModel.startAtom = mouseAtom;
                    drawBondModel.newAtom = null;
                    break;
                case drawTrippleBond:
                    drawBondModel.bondType = 3;
                    drawBondModel.activated = true;
                    drawBondModel.startAtom = mouseAtom;
                    drawBondModel.newAtom = null;
                    break;
                case drawRing:
                    drawRingModel.ringSize = controller.currentRingSize;
                    drawRingModel.activated = true;
                    drawRingModel.startAtom = mouseAtom;
                    drawRingModel.isBenzene = false;
                    break;
                case drawBenzene:
                    drawRingModel.ringSize = controller.currentRingSize;
                    drawRingModel.activated = true;
                    drawRingModel.startAtom = mouseAtom;
                    drawRingModel.isBenzene = true;
                    break;
                case eraser:
                    break;
                case moveAtom:
                    moveAtomModel.activated = true;
                    moveAtomModel.moveAtom = mouseAtom;
                    break;
            }
        }
        lastCord.setLocation(x, y);
    }

    public void onMouseUp(Widget sender, int x, int y) {
        isMouseDown = false;

        if (mouseStatus == MouseStatus.onBond) {
            Bond bond = controller.getClosestBond(new Point2D(x, y));
            int bondType = bond.getType();
            switch (controller.currentAction) {
                case drawSingleBond:
                    if (bondType >= 2) bondType = 0;
                    bond.setType(bondType + 1);
                    break;
                case drawDoubleBond:
                    bondType = bondType == 2 ? 1 : 2;
                    bond.setType(bondType);
                    break;
                case drawTrippleBond:
                    bondType = bondType == 3 ? 1 : 3;
                    bond.setType(bondType);
                    break;
                case eraser:
                    Atom source = bond.getSource();
                    Atom target = bond.getTarget();
                    controller.getMolecule().removeBond(bond);
                    if (source.countNeighbors() == 0) controller.getMolecule().removeAtom(source);
                    if (target.countNeighbors() == 0) controller.getMolecule().removeAtom(target);
                    break;
                default:

                    break;
            }
            controller.refreshView();

        } else if (mouseStatus == MouseStatus.onAtom) {
            Atom currentAtom = controller.getClosestAtom(new Point2D(x, y));
            switch (controller.currentAction) {
                case drawSingleBond:
                    if (isClick) {
                        Point2D newPoint = MoleculePlacer.calcNewAtomPlace(currentAtom, controller.getRenderer().getRendererModel().getDefaultBondLength());
                        Atom newAtom = controller.getMolecule().addAtom(controller.currentElement, newPoint.x, newPoint.y, 0);
                        controller.getMolecule().connect(currentAtom, newAtom, 1);
                    }
                    break;
                case drawDoubleBond:
                    if (isClick) {
                        Point2D newPoint = MoleculePlacer.calcNewAtomPlace(currentAtom, controller.getRenderer().getRendererModel().getDefaultBondLength());
                        Atom newAtom = controller.getMolecule().addAtom(controller.currentElement, newPoint.x, newPoint.y, 0);
                        controller.getMolecule().connect(currentAtom, newAtom, 2);
                    }
                    break;
                case drawTrippleBond:
                    if (isClick) {
                        Point2D newPoint = MoleculePlacer.calcNewAtomPlace(currentAtom, controller.getRenderer().getRendererModel().getDefaultBondLength());
                        Atom newAtom = controller.getMolecule().addAtom(controller.currentElement, newPoint.x, newPoint.y, 0);
                        controller.getMolecule().connect(currentAtom, newAtom, 3);
                    }
                    break;
                case drawBenzene:
                case drawRing:
                    if (isClick) {
                        if(currentAtom.countNeighbors()==1){
                          MoleculePlacer.placeRingOnCurrentAtom(currentAtom, controller.getRenderer().getRendererModel().getDefaultBondLength(), drawRingModel.ringSize, drawRingModel.isBenzene,currentAtom.getNeighbors()[0]);  
                        }else{
                            Atom newAtom = MoleculePlacer.placeNewRing(currentAtom, controller.getRenderer().getRendererModel().getDefaultBondLength(), drawRingModel.ringSize, drawRingModel.isBenzene);
                            controller.getMolecule().connect(currentAtom, newAtom, 1);
                        }
                    }
                    break;
                case eraser:
                    controller.getMolecule().removeAtom(currentAtom);
                    break;
                case drawAtom: //Swtich atom
                    currentAtom.setSymbol(controller.currentElement);
                    break;
                default:

                    break;
            }

            controller.refreshView();
        } else if (mouseStatus == MouseStatus.onWhiteSpace) {
            switch (controller.currentAction) {
                case drawAtom: //Swtich atom
                    if (isClick) {
                        Point2D realCord = controller.graphCordToRealCord(new Point2D(x, y));
                        Atom newAtom = controller.getMolecule().addAtom(controller.currentElement, realCord.x, realCord.y, 0);
                    }
                    break;
                case drawRing:
                    if (isClick) {
                        Point2D realCord = controller.graphCordToRealCord(new Point2D(x, y));
                        Atom newAtom = controller.getMolecule().addAtom("C", realCord.x, realCord.y, 0);
                        MoleculePlacer.placeRingOnCurrentAtom(newAtom, controller.getRenderer().getRendererModel().getDefaultBondLength(), controller.currentRingSize, false);  
                    }
                    break;
                default:
                    break;
            }

            controller.refreshView();
        }

        if (drawBondModel.activated) {
            drawNewBondTo(x, y, false);
            drawBondModel.activated = false;
        }
//        if (moveAtomModel.activated) {
//            moveAtomModel.activated = false;
//        }
    }

    public void onMouseEnter(Widget sender) {
        mouseStatus = MouseStatus.onWhiteSpace;
        isMouseDown = false;
    }

    public void onMouseLeave(Widget sender) {
        mouseStatus = MouseStatus.onWhiteSpace;
    }

    public void onMouseMove(Widget sender, int x, int y) {
        if (isMouseDown) isClick = false;


        //process dragging the view
        if (isMouseDown && mouseStatus == MouseStatus.onWhiteSpace) {
            CordTransformer cordTransformer = controller.getRenderer().getTransformer();
            cordTransformer.translate((x - lastCord.getX()) / cordTransformer.getScaleX(), (y - lastCord.getY()) / cordTransformer.getScaleY());
            lastCord.setLocation(x, y);
            controller.refreshView();
            return;
        }

        //process bond drawing
        if (isMouseDown && drawBondModel.activated) {
            drawNewBondTo(x, y, true);
            return;
        }

        if (isMouseDown && moveAtomModel.activated) {
            Point2D realCord = controller.graphCordToRealCord(new Point2D(x, y));
            moveAtomModel.moveAtom.move(realCord.getX(), realCord.getY(), 0);
            controller.refreshView();
            return;
        }

        //process normal move
        processNormalMove(x, y);
    }

    private void processNormalMove(int x, int y) {
        Atom atom = controller.getClosestAtom(new Point2D(x, y));
        Set<Atom> atoms = controller.getRenderer().getRendererModel().getHighlightedAtoms();
        Bond bond = controller.getClosestBond(new Point2D(x, y));
        Set<Bond> bonds = controller.getRenderer().getRendererModel().getHighlightedBonds();
        boolean needRefresh = atoms.size() > 0 || bonds.size() > 0; //indicates if necessary to refreh view
        mouseStatus = MouseStatus.onWhiteSpace;
        atoms.clear();
        bonds.clear();
        if (atom != null) {
            atoms.add(atom);
            mouseStatus = MouseStatus.onAtom;
            controller.refreshView();
            return;
        }
        if (bond != null) {
            bonds.add(bond);
            mouseStatus = MouseStatus.onBond;
            controller.refreshView();
            return;
        }
        if (needRefresh) {
            controller.refreshView();
        }
    }


    private void drawNewBondTo(int x, int y, boolean isPreview) {
        Atom mouseAtom = controller.getClosestAtom(new Point2D(x, y));
        if (mouseAtom != drawBondModel.startAtom) {
            if (mouseAtom != null) {
                if (mouseAtom != drawBondModel.newAtom) {
                    if (drawBondModel.newAtom != null) {
                        controller.getMolecule().removeAtom(drawBondModel.newAtom);
                        drawBondModel.newAtom = null;
                    }
                    controller.getRenderer().getRendererModel().getHighlightedAtoms().add(mouseAtom);

                    Bond bond1 = controller.getMolecule().getBond(drawBondModel.startAtom, mouseAtom);
                    Bond bond2 = controller.getMolecule().getBond(mouseAtom, drawBondModel.startAtom);

                    Bond oldBond = bond1 == null ? (bond2 == null ? null : bond2) : bond1;

                    if (oldBond != null) {
                        oldBond.setType(oldBond.getType() + 1);
                        controller.refreshView();
                        if (isPreview) oldBond.setType(oldBond.getType() - 1);

                    } else {
                        Bond bond = controller.getMolecule().connect(drawBondModel.startAtom, mouseAtom, drawBondModel.bondType);
                        controller.refreshView();
                        controller.getRenderer().getRendererModel().getHighlightedAtoms().remove(mouseAtom);
                        if (isPreview) controller.getMolecule().removeBond(bond);
                    }
                } else {
                    Point2D worldCord = controller.graphCordToRealCord(new Point2D(x, y));
                    Vector2D bondVector = new Vector2D(worldCord.x - drawBondModel.startAtom.getX(), worldCord.y - drawBondModel.startAtom.getY());
                    bondVector.setLength(controller.getRenderer().getRendererModel().getDefaultBondLength());
                    drawBondModel.newAtom.move(drawBondModel.startAtom.getX() + bondVector.x, drawBondModel.startAtom.getY() + bondVector.y, 0);
                    controller.refreshView();
                }
            } else {
                if (drawBondModel.newAtom == null) {
                    drawBondModel.newAtom = controller.getMolecule().addAtom(controller.currentElement);
                    controller.getMolecule().connect(drawBondModel.startAtom, drawBondModel.newAtom, drawBondModel.bondType);
                }
                Point2D worldCord = controller.graphCordToRealCord(new Point2D(x, y));
                Vector2D bondVector = new Vector2D(worldCord.x - drawBondModel.startAtom.getX(), worldCord.y - drawBondModel.startAtom.getY());
                bondVector.setLength(controller.getRenderer().getRendererModel().getDefaultBondLength());
                drawBondModel.newAtom.move(drawBondModel.startAtom.getX() + bondVector.x, drawBondModel.startAtom.getY() + bondVector.y, 0);
                controller.refreshView();

            }
        }
    }


    public void onMouseWheel(Widget sender, MouseWheelVelocity velocity) {
//        CordTransformer cordTransformer = controller.getRenderer().getTransformer();
//        cordTransformer.scale(cordTransformer.getScaleX()*(1 + velocity.getDeltaY() / 1000d), cordTransformer.getScaleY()*(1 + velocity.getDeltaY() / 1000d));
//        controller.refreshView();
        controller.getRenderer().getRendererModel().setZoomFactor(controller.getRenderer().getRendererModel().getZoomFactor() * (1 + velocity.getDeltaY() / 1000d));
        controller.refreshView();

//        System.out.println(cordTransformer.getScaleX()*(1 + velocity.getDeltaY() / 1000d));
    }

}
