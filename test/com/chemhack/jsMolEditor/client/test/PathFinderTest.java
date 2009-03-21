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

import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.Atom;
import com.chemhack.jsMolEditor.client.io.Molecules;
import com.chemhack.jsMolEditor.client.path.PathFinder;
import com.google.gwt.junit.client.GWTTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard L. Apodaca
 *         Duan Lian
 */
public class PathFinderTest extends GWTTestCase {
    private Molecule hexane;
    private Molecule acetone;
    private Molecule cyclohexane;
    private Molecule neopentane;
    private Molecule phenol;

    @Override
    protected void gwtSetUp() throws Exception
    {
      hexane = Molecules.createHexane();
      acetone = Molecules.createAcetone();
      cyclohexane = Molecules.createCyclohexane();
      neopentane = Molecules.createNeopentane();
      phenol = Molecules.createPhenol();
    }

    public String getModuleName() {
        return "com.chemhack.jsMolEditor.Editor";
    }
    
    public void testItShouldFindOneHexanePathsStartingFromPrimaryCarbon() {
        PathFinder finder = new PathFinder();

        List<List<Atom>> paths = finder.findAllPaths(hexane.getAtom(0));

        assertEquals(1, paths.size());
        assertEquals(hexane.countAtoms(), paths.get(0).size());

        List<Atom> path = paths.get(0);

        for (int i = 0; i < hexane.countAtoms(); i++) {
            assertEquals(hexane.getAtom(i).getIndex(), path.get(i).getIndex());
        }
    }

    public void testItShouldFindBothHexanePathsStartingFromSecondaryCarbon() {
        PathFinder finder = new PathFinder();

        List<List<Atom>> paths = finder.findAllPaths(hexane.getAtom(1));

        assertEquals(2, paths.size());

        List<Atom> test = new ArrayList<Atom>();

        test.add(hexane.getAtom(1));
        test.add(hexane.getAtom(0));

        assertTrue(paths.contains(test));

        test.clear();

        test.add(hexane.getAtom(1));
        test.add(hexane.getAtom(2));
        test.add(hexane.getAtom(3));
        test.add(hexane.getAtom(4));
        test.add(hexane.getAtom(5));

        assertTrue(paths.contains(test));
    }

    public void testItShouldFindAllThreeAcetonePathsStartingFromCarbonylCarbon() {
        PathFinder finder = new PathFinder();
        List<List<Atom>> paths = finder.findAllPaths(acetone.getAtom(1));

        assertEquals(3, paths.size());

        List<Atom> test = new ArrayList<Atom>();

        test.add(acetone.getAtom(1));
        test.add(acetone.getAtom(0));

        assertTrue(paths.contains(test));

        test.clear();
        test.add(acetone.getAtom(1));
        test.add(acetone.getAtom(2));

        assertTrue(paths.contains(test));

        test.clear();

        test.add(acetone.getAtom(1));
        test.add(acetone.getAtom(3));

        assertTrue(paths.contains(test));
    }

    public void testItShouldFindTwoCyclohexanePaths() {
        PathFinder finder = new PathFinder();
        List<List<Atom>> paths = finder.findAllPaths(cyclohexane.getAtom(0));

        assertEquals(2, paths.size());

        List<Atom> test = new ArrayList<Atom>();

        test.add(cyclohexane.getAtom(0));
        test.add(cyclohexane.getAtom(1));
        test.add(cyclohexane.getAtom(2));
        test.add(cyclohexane.getAtom(3));
        test.add(cyclohexane.getAtom(4));
        test.add(cyclohexane.getAtom(5));

        assertTrue(paths.contains(test));

        test.clear();

        test.add(cyclohexane.getAtom(0));
        test.add(cyclohexane.getAtom(5));
        test.add(cyclohexane.getAtom(4));
        test.add(cyclohexane.getAtom(3));
        test.add(cyclohexane.getAtom(2));
        test.add(cyclohexane.getAtom(1));

        assertTrue(paths.contains(test));
    }

    public void testItShouldFindFourNeopentanePaths() {
        PathFinder finder = new PathFinder();
        List<List<Atom>> paths = finder.findAllPaths(neopentane.getAtom(0));

        assertEquals(4, paths.size());
    }

    public void testItShouldFindBothPathsStartingFromPhenolOxygen() {
        PathFinder finder = new PathFinder();
        List<List<Atom>> paths = finder.findAllPaths(phenol.getAtom(6));

        assertEquals(2, paths.size());

        List<Atom> test = new ArrayList<Atom>();

        test.add(phenol.getAtom(6));
        test.add(phenol.getAtom(0));
        test.add(phenol.getAtom(1));
        test.add(phenol.getAtom(2));
        test.add(phenol.getAtom(3));
        test.add(phenol.getAtom(4));
        test.add(phenol.getAtom(5));

        assertTrue(paths.contains(test));

        test.clear();

        test.add(phenol.getAtom(6));
        test.add(phenol.getAtom(0));
        test.add(phenol.getAtom(5));
        test.add(phenol.getAtom(4));
        test.add(phenol.getAtom(3));
        test.add(phenol.getAtom(2));
        test.add(phenol.getAtom(1));

        assertTrue(paths.contains(test));
    }
}
