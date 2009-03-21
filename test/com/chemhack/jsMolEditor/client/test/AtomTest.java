/*
 * MX Cheminformatics Tools for Java
 * 
 * Copyright (c) 2007, 2008 Metamolecular, LLC
 * 
 * http://metamolecular.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.chemhack.jsMolEditor.client.test;

import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.DefaultMolecule;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author Richard L. Apodaca
 *         Duan Lian
 */
public class AtomTest extends GWTTestCase {
    public String getModuleName() {
        return "com.chemhack.jsMolEditor.Editor";
    }

    public void testItShouldThrowWhenIllegalRadicalIsSet() {
        Molecule molecule = new DefaultMolecule();
        Atom atom = molecule.addAtom("O");

        try {
            atom.setRadical(-1);
            fail();
        }
        catch (IllegalStateException ignore) {
        }

        try {
            atom.setRadical(4);
            fail();
        }
        catch (IllegalStateException ignore) {
        }
    }

    public void testItShouldChangeItsSymbolWhenGivenAValidNewOne() {
        Molecule molecule = new DefaultMolecule();
        Atom atom = molecule.addAtom("C");

        atom.setSymbol("O");

        assertEquals("O", atom.getSymbol());
    }

    public void testItShouldUpdateItsCoordinatesWhenMoved() {
        Molecule molecule = new DefaultMolecule();
        Atom atom = molecule.addAtom("C");

        atom.move(100, 100, 0);

        assertEquals(100d, atom.getX());
        assertEquals(100d, atom.getY());
        assertEquals(0d, atom.getZ());
    }


    public void testItShouldThrowWhenSettingAnIllegalAtomLabel() {
        Molecule molecule = new DefaultMolecule();
        Atom atom = molecule.addAtom("C");

        try {
            atom.setSymbol("bailout");
            fail();
        }
        catch (IllegalStateException e) {
        }
    }


    public void testItShouldReportValenceTwoForAPrimaryDoubleBondTerminal() {
        Molecule molecule = new DefaultMolecule();
        Atom c1 = molecule.addAtom("C");
        Atom c2 = molecule.addAtom("C");

        molecule.connect(c1, c2, 2);

        assertEquals(2, c1.getValence());
    }

    public void testItShouldReportValenceFourForAQuaternaryCarbon() {
        Molecule molecule = new DefaultMolecule();
        Atom c1 = molecule.addAtom("C");
        Atom c2 = molecule.addAtom("C");
        Atom c3 = molecule.addAtom("C");
        Atom c4 = molecule.addAtom("C");
        Atom c5 = molecule.addAtom("C");

        molecule.connect(c1, c2, 1);
        molecule.connect(c1, c3, 1);
        molecule.connect(c1, c4, 1);
        molecule.connect(c1, c5, 1);

        assertEquals(4, c1.getValence());
    }
}
