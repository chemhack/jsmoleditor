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
package com.chemhack.jsMolEditor.client.calc;

import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.model.AtomicSystem;
import com.chemhack.jsMolEditor.client.model.Molecule;

/**
 * @author Richard L. Apodaca
 */
public class MassCalculator {
    /**
     * Throws away error determinations.
     *
     * @param molecule the molecule to be measured
     * @return the averaged moleclar mass of molecule
     */
    public double findAveragedMass(Molecule molecule) {
        double result = 0;

        for (int i = 0; i < molecule.countAtoms(); i++) {
            Atom atom = molecule.getAtom(i);

            if (atom.hasSingleIsotope()) {

            } else {
                result += AtomicSystem.getInstance().getAverageMass(atom.getSymbol()).getValue();
            }

            result += atom.countVirtualHydrogens() * AtomicSystem.getInstance().getAverageMass("H").getValue();
        }

        return result;
    }
}
