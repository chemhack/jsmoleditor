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

import com.chemhack.jsMolEditor.client.calc.MassCalculator;
import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.io.Molecules;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author Richard L. Apodaca
 *         Duan Lian
 */
public class MassCalculatorTest extends GWTTestCase {
    private MassCalculator calculator = new MassCalculator();

    public String getModuleName() {
        return "com.chemhack.jsMolEditor.Editor";
    }


    public void testItShouldFindTheAveragedMolecularMassOfBenzene() {
        Molecule benzene = Molecules.createBenzene();

        assertEquals(78.11184, calculator.findAveragedMass(benzene));
    }

    public void testItShouldFindTheAverageMolecularMassOfPhenol() {
        Molecule phenol = Molecules.createPhenol();
        assertEquals(94.11124, calculator.findAveragedMass(phenol), 0.00001);
    }
}
