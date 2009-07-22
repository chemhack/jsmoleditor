package com.chemhack.jsMolEditor.client.controller;

import com.chemhack.jsMolEditor.client.renderer.CanvasRenderer;
import com.chemhack.jsMolEditor.client.renderer.GeometryTools;
import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.DefaultMolecule;
import com.chemhack.jsMolEditor.client.model.Bond;
import com.chemhack.jsMolEditor.client.widget.ExtendedCanvas;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Dimension;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Point2D;
import com.chemhack.jsMolEditor.client.io.mdl.MolfileReader;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.Window;

public class ViewerController {
    CanvasRenderer renderer;
    Molecule molecule = new DefaultMolecule();
    public ViewerController(String divID, int width, int height) {
        ExtendedCanvas canvas = new ExtendedCanvas(width, height);
        renderer = new CanvasRenderer(canvas);
        RootPanel rootPanel = RootPanel.get(divID);
        rootPanel.setPixelSize(width, height);
        rootPanel.add(canvas);
    }
    public void setMolecule(Molecule molecule) {
        GeometryTools.translateAllPositive(molecule);
        GeometryTools.center(molecule, new Dimension(renderer.getCanvas().getWidth(), renderer.getCanvas().getHeight()));
        this.molecule = molecule;
        calcAverageBondLength();
        renderer.paintNewMolecule(molecule);
//        renderer.getTransformer().dumpMatrix();
    }
    public void calcAverageBondLength() {
        double total = 0;
        for (int i = 0; i < molecule.countBonds(); i++) {
            Bond bond = molecule.getBond(i);
            total += GeometryTools.calcDistance(new Point2D(bond.getSource().getX(), bond.getSource().getY()), new Point2D(bond.getTarget().getX(), bond.getTarget().getY()));
        }
        renderer.getRendererModel().setDefaultBondLength(total / molecule.countBonds());
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
    
    
}
