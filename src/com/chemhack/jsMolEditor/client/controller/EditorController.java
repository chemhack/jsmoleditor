package com.chemhack.jsMolEditor.client.controller;

import com.chemhack.jsMolEditor.client.renderer.CanvasRenderer;
import com.chemhack.jsMolEditor.client.renderer.GeometryTools;
import com.chemhack.jsMolEditor.client.widget.*;
import com.chemhack.jsMolEditor.client.listener.EditorMouseListener;
import com.chemhack.jsMolEditor.client.listener.ToggleButtonListener;
import com.chemhack.jsMolEditor.client.listener.KeyboardShortCutEventPreview;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Point2D;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Dimension;
import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.DefaultMolecule;
import com.chemhack.jsMolEditor.client.model.Bond;
import com.chemhack.jsMolEditor.client.io.mdl.MolfileReader;
import com.chemhack.jsMolEditor.client.io.mdl.MolfileWriter;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

public class EditorController {
    CanvasRenderer renderer;
    Molecule molecule = new DefaultMolecule();
    private ElementToolBox elementToolBox;
    private TopToolBox topToolBox;

    public enum EditActions {
        drawSingleBond, drawDoubleBond, drawTrippleBond, drawAtom, moveAtom, enterElement, eraser, drawRing,drawBenzene
    }

    public EditActions currentAction = EditActions.drawSingleBond;
    public String currentElement = "C";
    public int currentRingSize =6;
    public EditorController(String divID, int width, int height) {


        VerticalPanel vp = new VerticalPanel();
        HorizontalPanel hp = new HorizontalPanel();

        SimplePanel topToolBoxWrapper = new SimplePanel();

        ToggleButtonListener toggleButtonListener = new ToggleButtonListener();

        topToolBox = new TopToolBox(this, toggleButtonListener);
        topToolBox.setStyleName("jsmoleditor-topToolBox");

        topToolBoxWrapper.setWidget(topToolBox);
        topToolBoxWrapper.setStyleName("jsmoleditor-topTooBoxWrapper");
        topToolBoxWrapper.setWidth(width + "px");

        SimplePanel sideToolBoxWrapper = new SimplePanel();
        elementToolBox = new ElementToolBox(this, toggleButtonListener);

        sideToolBoxWrapper.setWidget(elementToolBox);
        sideToolBoxWrapper.setStyleName("jsmoleditor-elementToolBoxWrapper");
        sideToolBoxWrapper.setHeight((height - 24) + "px");

        VerticalPanel vp2 = new VerticalPanel();


        ExtendedCanvas canvas = new ExtendedCanvas(width - 24, height - 48);
        StatusBar statusBar = new StatusBar(width - 24, 24);
        statusBar.setHTML("Copyright chemhack.com");
        renderer = new CanvasRenderer(this, canvas);

        vp2.add(canvas);
        vp2.add(statusBar);

        hp.add(sideToolBoxWrapper);
        hp.add(vp2);

        vp.add(topToolBoxWrapper);
        vp.add(hp);

        RootPanel rootPanel = RootPanel.get(divID);
        rootPanel.setPixelSize(width, height);
        rootPanel.add(vp);

        KeyboardShortCutEventPreview preview = new KeyboardShortCutEventPreview(this); //TODO deal with multiple editor instance?

        DOM.addEventPreview(preview);

        EditorMouseListener listener = new EditorMouseListener(this);
        canvas.addMouseListener(listener);
        canvas.addMouseWheelListener(listener);
    }

    public Atom getClosestAtom(Point2D graphCord) {
        Atom closestAtom = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < molecule.countAtoms(); i++) {
            Atom currentAtom = molecule.getAtom(i);
            Point2D currentPointGraph = realCordToGraphCord(new Point2D(currentAtom.getX(), currentAtom.getY()));
            double distance = GeometryTools.calcDistance(graphCord, currentPointGraph);

            if (distance <= renderer.getRendererModel().getAtomRadius() &&
                    distance < closestDistance) {
                closestAtom = currentAtom;
                closestDistance = distance;
            }
        }
        return closestAtom;
    }


    public Bond getClosestBond(Point2D graphCord) {
        Bond closestBond = null;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < molecule.countBonds(); i++) {
            Bond currentBond = molecule.getBond(i);
            Point2D currentCenterGraph = realCordToGraphCord(new Point2D((currentBond.getSource().getX() + currentBond.getTarget().getX()) / 2, (currentBond.getSource().getY() + currentBond.getTarget().getY()) / 2));

            double distance = GeometryTools.calcDistance(graphCord, currentCenterGraph);

            if (distance <= renderer.getRendererModel().getAtomRadius() &&
                    distance < closestDistance) {
                closestBond = currentBond;
                closestDistance = distance;
            }

        }

        return closestBond;

    }

    public Molecule getMolecule() {
        return molecule;
    }

    public void setMolecule(Molecule molecule) {
        GeometryTools.translateAllPositive(molecule);
        GeometryTools.center(molecule, new Dimension(renderer.getCanvas().getWidth(), renderer.getCanvas().getHeight()));
        this.molecule = molecule;
        calcAverageBondLength();
        renderer.paintNewMolecule(molecule);
//        renderer.getTransformer().dumpMatrix();
    }

    public void importMolFile(String fileContent) {
        MolfileReader reader = new MolfileReader();
        Molecule molecule = new DefaultMolecule();
        try {
            reader.read(molecule, fileContent);
            setMolecule(molecule);
        } catch (Exception e) {
            Window.alert("Error occured: " + e.getMessage());
        }
    }

    public String exportMolFile() {
        MolfileWriter writer = new MolfileWriter();
        try {
            return writer.write(molecule);
        } catch (Exception e) {
            Window.alert("Error occured: " + e.getMessage());
        }
        return null;
    }


    public void refreshView() {
        renderer.paintMolecule(molecule);
    }

    public void centerMol() {

    }

    public void zoomInOut(double factor) {
        renderer.getRendererModel().setZoomFactor(renderer.getRendererModel().getZoomFactor() * factor);
        renderer.paintNewMolecule(molecule);
    }

    public CanvasRenderer getRenderer() {
        return renderer;
    }

    public void calcAverageBondLength() {
        double total = 0;
        for (int i = 0; i < molecule.countBonds(); i++) {
            Bond bond = molecule.getBond(i);
            total += GeometryTools.calcDistance(new Point2D(bond.getSource().getX(), bond.getSource().getY()), new Point2D(bond.getTarget().getX(), bond.getTarget().getY()));
        }
        renderer.getRendererModel().setDefaultBondLength(total / molecule.countBonds());
    }

    public Point2D realCordToGraphCord(Point2D realCord) {
        return renderer.getTransformer().transform(realCord, null);
    }

    public Point2D graphCordToRealCord(Point2D graphCord) {
        return renderer.getTransformer().inverseTransform(graphCord, null);
    }


    public void selectElement(String element) {
        this.currentElement = element;
        this.currentAction=EditActions.drawAtom;
        elementToolBox.setSelectedElement(element);
    }

    public void selectEditAction(EditActions action) {
        this.currentAction = action;
        topToolBox.setSelectedAction(action);
    }

    public void selectRingAction(int ringSize,boolean isBenzene) {
        this.currentAction = isBenzene?EditActions.drawBenzene:EditActions.drawRing;
        this.currentRingSize=ringSize;
        topToolBox.setSelectedAction(isBenzene?EditActions.drawBenzene:EditActions.drawRing,ringSize);
    }



}
