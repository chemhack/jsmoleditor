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

import com.chemhack.jsMolEditor.client.io.daylight.SMILESReader;
import com.chemhack.jsMolEditor.client.model.DefaultMolecule;
import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.Atom;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author Richard L. Apodaca
 *         Duan Lian
 */
public class SMILESReaderTest extends GWTTestCase {

    private SMILESReader reader = new SMILESReader();

    public String getModuleName() {
        return "com.chemhack.jsMolEditor.Editor";
    }


    public void testItShouldReadALinearChain() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "CCCCCC");

        assertEquals(6, input.countAtoms());
        assertEquals(5, input.countBonds());
    }

    public void testItShouldReadACycle() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "C1CCCCC1");

        assertEquals(6, input.countAtoms());
        assertEquals(6, input.countBonds());
    }

    public void testItShouldReadATertiaryBranch() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "CC(C)C");

        assertEquals(4, input.countAtoms());
        assertEquals(3, input.countBonds());
        assertEquals(3, input.getAtom(1).countNeighbors());
    }

    public void testItShouldReadAQuaternaryBranch() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "CC(C)(C)C");

        assertEquals(5, input.countAtoms());
        assertEquals(4, input.countBonds());
        assertEquals(4, input.getAtom(1).countNeighbors());
    }

    public void testItShouldReadANestedBranch() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "CC(C(C)C)C");

        assertEquals(6, input.countAtoms());
        assertEquals(5, input.countBonds());
        assertEquals(3, input.getAtom(1).countNeighbors());
        assertEquals(3, input.getAtom(2).countNeighbors());
    }

    public void testItShouldReadCubane() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "C12C3C4C1C5C4C3C25");

        assertEquals(8, input.countAtoms());
        assertEquals(12, input.countBonds());

        for (int i = 0; i < input.countAtoms(); i++) {
            Atom atom = input.getAtom(i);

            assertEquals(3, atom.countNeighbors());
        }
    }

    public void testItShouldReadADoubleBond() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "C=CC");

        assertEquals(3, input.countAtoms());
        assertEquals(2, input.getBond(0).getType());
    }

    public void testItShouldReadKekuleBenzene() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "C1=CC=CC=C1");

        assertEquals(6, input.countAtoms());

        for (int i = 0; i < input.countAtoms(); i++) {
            assertEquals(2, input.getAtom(i).countNeighbors());
            assertEquals(1, input.getAtom(i).countVirtualHydrogens());
        }
    }

    public void testItShouldReadATripleBond() {
        Molecule input = new DefaultMolecule();

        reader.read(input, "C#CC");

        assertEquals(3, input.countAtoms());
        assertEquals(3, input.getBond(0).getType());
    }

    public void testItShouldThrowWhenGivenIllegalAtomSymbol() {
        Molecule input = new DefaultMolecule();

        try {
            reader.read(input, "C!C");
            fail();
        } catch (IllegalArgumentException ignore) {
        }
    }

    public void testItShouldReadASMILESThroughAStaticMethod() {
        Molecule result = SMILESReader.read("C1=CC=CC=C1");

        assertEquals(6, result.countAtoms());
    }
}