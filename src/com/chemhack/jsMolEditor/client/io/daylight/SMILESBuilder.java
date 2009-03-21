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
package com.chemhack.jsMolEditor.client.io.daylight;

import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.model.Molecule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Richard L. Apodaca
 *         Duan Lian
 */
public class SMILESBuilder {

    private Atom head;
    private int bondType;
    private Molecule molecule;
    private List<Atom> branchPoints;
    private Map<String, Atom> ringClosures;

    public SMILESBuilder(Molecule molecule) {
        this.molecule = molecule;
        this.branchPoints = new ArrayList<Atom>();
        this.ringClosures = new HashMap<String, Atom>();
        head = null;

        resetBond();
    }

    public void addHead(String token) {
        Atom atom = molecule.addAtom(token);

        if (head != null) {
            molecule.connect(head, atom, bondType);
        }

        head = atom;

        resetBond();
    }

    public void addBond(int type) {
        this.bondType = type;
    }

    public void openBranch() {
        branchPoints.add(head);
    }

    public void closeBranch() {
        try {
            head = branchPoints.remove(branchPoints.size() - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("Attempting to close branch when no branches are open.", e);
        }
    }

    public void ring(String id) {
        Atom atom = ringClosures.get(id);

        if (atom == null) {
            ringClosures.put(id, head);
        } else {
            molecule.connect(head, atom, bondType);
            resetBond();
            ringClosures.remove(id);
        }
    }

    private void resetBond() {
        this.bondType = 1;
    }
}
