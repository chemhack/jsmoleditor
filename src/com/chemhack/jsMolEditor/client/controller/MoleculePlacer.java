package com.chemhack.jsMolEditor.client.controller;

import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.model.Bond;
import com.chemhack.jsMolEditor.client.math.Vector2D;
import com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom.Point2D;
import com.chemhack.jsMolEditor.client.renderer.GeometryTools;


public class MoleculePlacer {
    public static Point2D calcNewAtomPlace(Atom targetAtom, double bondLength) {
        Molecule molecule = targetAtom.getMolecule();
        int neighbors = targetAtom.countNeighbors();
        Point2D newPlace = new Point2D();
        switch (neighbors) {
            case 0:
                newPlace.setLocation(targetAtom.getX() + bondLength, targetAtom.getY());
                break;
            case 1:
                Atom onlyNeighbor = targetAtom.getNeighbors()[0];

                Vector2D onlyVector = new Vector2D(onlyNeighbor.getX() - targetAtom.getX(), onlyNeighbor.getY() - targetAtom.getY());
                Vector2D newVector1 = onlyVector.rotate(Math.toRadians(120))[0];
                Vector2D newVector2 = onlyVector.rotate(Math.toRadians(120))[1];
                Point2D moleculeCenter = GeometryTools.getGeometryCenter(molecule);
                Vector2D compareVector = new Vector2D(moleculeCenter.x - targetAtom.getX(), moleculeCenter.y - targetAtom.getY());

                double angle1 = compareVector.angle(newVector1);
                double angle2 = compareVector.angle(newVector2);
                Vector2D newVector;

                newVector = (angle1 > angle2) ? newVector1 : newVector2;
                newVector.setLength(bondLength);
                newPlace.setLocation(targetAtom.getX() + newVector.x, targetAtom.getY() + newVector.y);
                break;

            case 2:
                Atom neighbor1 = targetAtom.getNeighbors()[0];
                Atom neighbor2 = targetAtom.getNeighbors()[1];
                Vector2D vector1 = new Vector2D(neighbor1.getX() - targetAtom.getX(), neighbor1.getY() - targetAtom.getY());
                Vector2D vector2 = new Vector2D(neighbor2.getX() - targetAtom.getX(), neighbor2.getY() - targetAtom.getY());
                vector1.normalize();
                vector2.normalize();
                Vector2D vector3 = new Vector2D(vector1.x + vector2.x, vector1.y + vector2.y);
                vector3.setLength(bondLength);
                newPlace.setLocation(targetAtom.getX() - vector3.x, targetAtom.getY() - vector3.y);

                break;
            default:
                double maxAngel = 0;
                Vector2D targetVector = null;
                for (Atom neighborD1 : targetAtom.getNeighbors()) {
                    double currentMinAngel = Math.PI; //find out the nearest bond
                    Vector2D currentNeighboringVector = new Vector2D();
                    for (Atom neighborD2 : targetAtom.getNeighbors()) {
                        if (neighborD1 != neighborD2) {
                            Vector2D vectorD1 = new Vector2D(neighborD1.getX() - targetAtom.getX(), neighborD1.getY() - targetAtom.getY());
                            Vector2D vectorD2 = new Vector2D(neighborD2.getX() - targetAtom.getX(), neighborD2.getY() - targetAtom.getY());
                            double currentAngel = vectorD1.angle(vectorD2);
                            if (currentAngel < currentMinAngel) {
                                currentMinAngel = currentAngel;
                                currentNeighboringVector.x = vectorD1.x + vectorD2.x;
                                currentNeighboringVector.y = vectorD1.y + vectorD2.y;
                                currentNeighboringVector.setLength(bondLength);
                            }
                        }
                    }

                    if (currentMinAngel > maxAngel) {
                        targetVector = currentNeighboringVector;
                        maxAngel = currentMinAngel;
                    }

                }
                newPlace.setLocation(targetAtom.getX() + targetVector.x, targetAtom.getY() + targetVector.y);

                break;
        }

        return newPlace;
    }

    public static void placeRingOnCurrentBond(Bond currentBond, double bondLength, int ringSize, boolean isBenzene) {

    }

//    public static void placeRingOnWhiteSpace(double x,double y,Molecule molecule, double bondLength, int ringSize, boolean isBenzene) {
//        Vector2D vector = new Vector2D(0, bondLength);
//        Atom targetAtom=molecule.addAtom("C",x,y,0);
//        System.out.println(vector);
//        System.out.println(targetAtom.getX());
//        System.out.println(targetAtom.getY());
//        doRingPlace(bondLength, ringSize, isBenzene, molecule, vector, targetAtom);

    //    }

    public static void placeRingOnCurrentAtom(Atom targetAtom, double bondLength, int ringSize, boolean isBenzene) {
        Vector2D vector = new Vector2D(0, -1);
        doRingPlace(bondLength, ringSize, isBenzene, targetAtom.getMolecule(), vector, targetAtom);
    }


    public static void placeRingOnCurrentAtom(Atom targetAtom, double bondLength, int ringSize, boolean isBenzene, Atom lastAtom) {
        Vector2D vector = new Vector2D(targetAtom.getX() - lastAtom.getX(), targetAtom.getY() - lastAtom.getY());

        doRingPlace(bondLength, ringSize, isBenzene, targetAtom.getMolecule(), vector, targetAtom);
    }

    public static Atom placeNewRing(Atom targetAtom, double bondLength, int ringSize, boolean isBenzene) {
        Point2D firtAtomPlace = calcNewAtomPlace(targetAtom, bondLength);
        Point2D targetAtomPlace = new Point2D(targetAtom.getX(), targetAtom.getY());
        Molecule molecule = targetAtom.getMolecule();
        Vector2D vector = new Vector2D(firtAtomPlace.x - targetAtomPlace.x, firtAtomPlace.y - targetAtomPlace.y);
        Atom firstRingAtom = molecule.addAtom("C", firtAtomPlace.x, firtAtomPlace.y, 0);

        doRingPlace(bondLength, ringSize, isBenzene, molecule, vector, firstRingAtom);

        return firstRingAtom;
    }

    private static void doRingPlace(double bondLength, int ringSize, boolean isBenzene, Molecule molecule, Vector2D vector, Atom firstRingAtom) {
        double radical = (bondLength / 2) / (Math.sin(Math.toRadians(180 / ringSize)));
        vector.setLength(radical);
        Point2D ringCenter = new Point2D(firstRingAtom.getX() + vector.x, firstRingAtom.getY() + vector.y);
        Vector2D reverseVector = new Vector2D(-vector.x, -vector.y);
        int bondOrder = 1;
        Atom lastAtom = firstRingAtom;
        for (int i = 1; i < ringSize; i++) {
            double angle = i * ((double) 360) / ringSize;
            Vector2D turnedVector;
            if (angle < 180) {
                turnedVector = reverseVector.rotate(Math.toRadians(angle))[0];
            } else if (angle > 180) {
                turnedVector = reverseVector.rotate(Math.toRadians(360 - angle))[1];
            } else {
                turnedVector = vector;
            }
            
            turnedVector.setLength(radical);

            Atom newAtom = molecule.addAtom("C", ringCenter.x + turnedVector.x, ringCenter.y + turnedVector.y, 0);
            molecule.connect(lastAtom, newAtom, bondOrder);
            lastAtom = newAtom;
            if (isBenzene) {
                bondOrder = bondOrder == 1 ? 2 : 1;
            }
        }
        molecule.connect(lastAtom, firstRingAtom, bondOrder);
    }
}
