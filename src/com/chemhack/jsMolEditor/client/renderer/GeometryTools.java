package com.chemhack.jsMolEditor.client.renderer;

import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Dimension;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Point2D;


public class GeometryTools {

    public static void translateAllPositive(Molecule molecule) {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        for (int i = 0; i < molecule.countAtoms(); i++) {
            Atom atom = molecule.getAtom(i);
//            if (atom.getX()  && atom.getY() ) {
            if (atom.getX() < minX) {
                minX = atom.getX();
            }
            if (atom.getX() < minY) {
                minY = atom.getY();
            }
//            }
        }
        translate2D(molecule, minX * -1, minY * -1);

    }

    public static void translate2D(Molecule molecule, double transX, double transY) {
        for (int i = 0; i < molecule.countAtoms(); i++) {
            Atom atom = molecule.getAtom(i);
//            if (atom.getX()  && atom.getY() ) {
            atom.move(atom.getX()+transX,atom.getY()+transY,atom.getZ());
            
//            }
        }
    }
    public static void center(Molecule molecule, Dimension areaDim) {
        Dimension molDim = get2DDimension(molecule);
        int transX = (areaDim.width - molDim.width) / 2;
        int transY = (areaDim.height - molDim.height) / 2;
        translateAllPositive(molecule);
        translate2D(molecule, transX, transY);
    }
    public static Dimension get2DDimension(Molecule molecule) {
        double[] minmax = getMinMax(molecule);
        double maxX = minmax[2];
        double maxY = minmax[3];
        double minX = minmax[0];
        double minY = minmax[1];
        return new Dimension((int) (maxX - minX + 1), (int) (maxY - minY + 1));
    }
    public static double[] getMinMax(Molecule molecule) {
        double maxX = -Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        for (int i = 0; i < molecule.countAtoms(); i++) {
            Atom atom = molecule.getAtom(i);
//            if (atom.getPoint2d() != null) {
                if (atom.getX() > maxX) {
                    maxX = atom.getX();
                }
                if (atom.getX() < minX) {
                    minX = atom.getX();
                }
                if (atom.getY() > maxY) {
                    maxY = atom.getY();
                }
                if (atom.getY() < minY) {
                    minY = atom.getY();
                }
//            }
        }
        double[] minmax = new double[4];
        minmax[0] = minX;
        minmax[1] = minY;
        minmax[2] = maxX;
        minmax[3] = maxY;
        return minmax;
    }
    public static double calcDistance(Point2D point1, Point2D point2) {
        return Math.pow(Math.pow(point1.getX()- point2.getX(),2)+Math.pow(point1.getY()- point2.getY(),2),0.5);
    }

    public static Point2D getGeometryCenter(Molecule molecule) {
        double totalX=0;
        double totalY=0;
        for (int i = 0; i < molecule.countAtoms(); i++) {
            Atom atom = molecule.getAtom(i);
            totalX+=atom.getX();
            totalY+=atom.getY();
        }
        return new Point2D(totalX/molecule.countAtoms(),totalY/molecule.countAtoms());
    }



}


