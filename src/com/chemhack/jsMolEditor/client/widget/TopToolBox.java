package com.chemhack.jsMolEditor.client.widget;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.chemhack.jsMolEditor.client.resources.MyImageBundle;
import com.chemhack.jsMolEditor.client.controller.EditorController;
import com.chemhack.jsMolEditor.client.dialog.ImportMolFileDialog;
import com.chemhack.jsMolEditor.client.dialog.ExportMolFileDialog;
import com.chemhack.jsMolEditor.client.dialog.OptionDialog;
import com.chemhack.jsMolEditor.client.listener.ToggleButtonListener;


public class TopToolBox extends HorizontalPanel {
    ToggleDropDownButton tdbBond;
    ToggleDropDownButton tdbElement;

    ToggleButtonListener toggleButtonListener;
    private ToggleButton tbDoubleBond;
    private ToggleButton tbSingleBond;
    private ToggleButton tbTripleBond;
    private ToggleButton tbBenzene;
    private ToggleButton tbHexagon;
    private ToggleButton tbPentagon;
    private ToggleButton tbSquare;
    private ToggleButton tbTriangle;

    public TopToolBox(final EditorController controller, final ToggleButtonListener toggleButtonListener) {
        super();
        this.toggleButtonListener = toggleButtonListener;

        MyImageBundle myImageBundle = (MyImageBundle) GWT.create(MyImageBundle.class);

        PushButton pbNew = createPushButton(myImageBundle.newSmall(), "New Molecule");
        pbNew.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.getMolecule().clear();
                controller.refreshView();

            }
        });
        this.add(pbNew);

        PushButton pbOpen = createPushButton(myImageBundle.openSmall(), "Open Mol File");
        pbOpen.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                ImportMolFileDialog importMolFileDialog = new ImportMolFileDialog(controller);
                importMolFileDialog.center();
                importMolFileDialog.show();
            }
        });
        this.add(pbOpen);

        PushButton pbSave = createPushButton(myImageBundle.saveSmall(), "Save Mol File");
        pbSave.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                ExportMolFileDialog exportMolFileDialog = new ExportMolFileDialog(controller);
                exportMolFileDialog.center();
                exportMolFileDialog.show();
            }
        });
        this.add(pbSave);

        this.add(createSpace());

        tbSingleBond = createToggleButton(myImageBundle.singleBondSmall(), "Single Bond");
        tbSingleBond.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.selectElement("C");
                toggleButtonListener.onClick(sender);
                controller.currentAction = EditorController.EditActions.drawSingleBond;                
            }
        });
        tbSingleBond.setDown(true);
        this.add(tbSingleBond);

        tbDoubleBond = createToggleButton(myImageBundle.doubleBondSmall(), "Double Bond");
        tbDoubleBond.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.selectElement("C");
                toggleButtonListener.onClick(sender);
                controller.currentAction = EditorController.EditActions.drawDoubleBond;
            }
        });
        this.add(tbDoubleBond);

        tbTripleBond = createToggleButton(myImageBundle.tripleBondSmall(), "Triple Bond");
        tbTripleBond.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.selectElement("C");
                toggleButtonListener.onClick(sender);                
                controller.currentAction = EditorController.EditActions.drawTrippleBond;                
            }
        });
        this.add(tbTripleBond);


        this.add(createSpace());

        tbTriangle = createToggleButton(myImageBundle.triangleSmall(), "Triangle");
        tbTriangle.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.currentAction = EditorController.EditActions.drawRing;
                controller.currentRingSize = 3;
            }
        });
        this.add(tbTriangle);


        tbSquare = createToggleButton(myImageBundle.squareSmall(), "Square");
        tbSquare.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.currentAction = EditorController.EditActions.drawRing;
                controller.currentRingSize = 4;
            }
        });
        this.add(tbSquare);


        tbPentagon = createToggleButton(myImageBundle.pentagonSmall(), "Pentagon");
        tbPentagon.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.currentAction = EditorController.EditActions.drawRing;
                controller.currentRingSize = 5;
            }
        });
        this.add(tbPentagon);

        tbHexagon = createToggleButton(myImageBundle.hexagonSmall(), "Hexagon");
        tbHexagon.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.currentAction = EditorController.EditActions.drawRing;
                controller.currentRingSize = 6;
            }
        });
        this.add(tbHexagon);

        tbBenzene = createToggleButton(myImageBundle.benzeneSmall(), "Benzene");
        tbBenzene.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.currentAction = EditorController.EditActions.drawBenzene;
                controller.currentRingSize = 6;
            }
        });
        this.add(tbBenzene);


//        tdbBond = new ToggleDropDownButton();
//        tdbBond.addOption(myImageBundle.singleBondSmall().createImage(), "Singel Bond", new ClickListener() {
//            public void onClick(Widget sender) {
//                controller.currentAction = EditorController.EditActions.drawSingeBond;
//            }
//        });
//        tdbBond.addOption(myImageBundle.doubleBondSmall().createImage(), "Double Bond", new ClickListener() {
//            public void onClick(Widget sender) {
//                controller.currentAction = EditorController.EditActions.drawDoubleBond;
//            }
//        });
//        tdbBond.addOption(myImageBundle.tripleBondSmall().createImage(), "Triple Bond", new ClickListener() {
//            public void onClick(Widget sender) {
//                controller.currentAction = EditorController.EditActions.drawTrippleBond;
//            }
//        });
//        tdbBond.setSelectedIndex(0);
//        tdbBond.setUpAllToggle(upAllToggle);
//        tdbBond.setDown(true);
//        this.add(tdbBond);
//
//
//        this.add(createSpace(3));

//        tdbElement = new ToggleDropDownButton();
//        tdbElement.addOption(myImageBundle.elementCSmall().createImage(), "Element C", new ClickListener() {
//            public void onClick(Widget sender) {
//                controller.currentAction = EditorController.EditActions.drawAtom;
//                controller.currentElement = "C";
//            }
//        });
//        tdbElement.addOption(myImageBundle.elementNSmall().createImage(), "Element N", new ClickListener() {
//            public void onClick(Widget sender) {
//                controller.currentAction = EditorController.EditActions.drawAtom;
//                controller.currentElement = "N";
//            }
//        });
//        tdbElement.addOption(myImageBundle.elementOSmall().createImage(), "Element O", new ClickListener() {
//            public void onClick(Widget sender) {
//                controller.currentAction = EditorController.EditActions.drawAtom;
//                controller.currentElement = "O";
//            }
//        });
//        tdbElement.setSelectedIndex(0);
//        tdbElement.setUpAllToggle(upAllToggle);
//        this.add(tdbElement);

        this.add(createSpace());

        ToggleButton tbMove = createToggleButton(myImageBundle.moveSmall(), "Move Atom");
        tbMove.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.currentAction = EditorController.EditActions.moveAtom;
            }
        });
        this.add(tbMove);

        ToggleButton tbEraser = createToggleButton(myImageBundle.eraserSmall(), "Eraser");
        tbEraser.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.currentAction = EditorController.EditActions.eraser;
            }
        });
        this.add(tbEraser);


        this.add(createSpace());

        PushButton pbZoomIn = createPushButton(myImageBundle.zoomInSmall(), "Zoom In");
        pbZoomIn.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.zoomInOut(1.2);
            }
        });
        this.add(pbZoomIn);
        PushButton pbZoomOut = createPushButton(myImageBundle.zoomOutSmall(), "Zoom Out");
        pbZoomOut.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.zoomInOut(0.8);
            }
        });
        this.add(pbZoomOut);

        this.add(createSpace());

        PushButton tbOptions = createPushButton(myImageBundle.optionsSmall(), "Options");
        tbOptions.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                OptionDialog optionDialog = new OptionDialog(controller);
                optionDialog.center();
                optionDialog.show();
            }
        });
        this.add(tbOptions);


//        selectButton(tbSingleBond);
//        ToggleButton tbMove = new ToggleButton(myImageBundle.moveSmall().createImage());
//        tbTripleBond.addClickListener(new ClickListener(){
//            public void onClick(Widget sender) {
//
//            }
//        });
//
//        this.add(tbMove);

    }

    private ToggleButton createToggleButton(AbstractImagePrototype imagePrototype, String tittle) {
        ToggleButton toggleButton = new ToggleButton(imagePrototype.createImage());
        toggleButton.setTitle(tittle);
        toggleButton.setStylePrimaryName("jsmoleditor-toolButton");

        toggleButton.addClickListener(toggleButtonListener);
        toggleButtonListener.addToggleButton(toggleButton);

        return toggleButton;
    }

    private PushButton createPushButton(AbstractImagePrototype imagePrototype, String tittle) {
        PushButton pushButton = new PushButton(imagePrototype.createImage());
        pushButton.setTitle(tittle);
        pushButton.setStylePrimaryName("jsmoleditor-toolButton");
        return pushButton;
    }

    private SimplePanel createSpace() {
        SimplePanel space = new SimplePanel();
        space.setStyleName("jsmoleditor-toolBoxSeprator");
        return space;
    }

    public void setSelectedAction(EditorController.EditActions action) {
        switch (action) {
            case drawSingleBond:
                toggleButtonListener.onClick(tbSingleBond);
                break;
            case drawDoubleBond:
                toggleButtonListener.onClick(tbDoubleBond);
                break;
            case drawTrippleBond:
                toggleButtonListener.onClick(tbTripleBond);
                break;
        }

    }

    public void setSelectedAction(EditorController.EditActions action, int ringSize) {
        switch (action) {
            case drawRing:
                switch (ringSize) {
                    case 3:
                        toggleButtonListener.onClick(tbTriangle);
                        break;
                    case 4:
                        toggleButtonListener.onClick(tbSquare);
                        break;
                    case 5:
                        toggleButtonListener.onClick(tbPentagon);
                        break;
                    case 6:
                        toggleButtonListener.onClick(tbHexagon);
                        break;
                }
                break;
            case drawBenzene:
                toggleButtonListener.onClick(tbBenzene);
                break;
        }

    }


}
