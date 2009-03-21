package com.chemhack.jsMolEditor.client.test;

import com.google.gwt.junit.client.GWTTestCase;
import com.chemhack.jsMolEditor.client.io.mdl.MolfileReader;
import com.chemhack.jsMolEditor.client.io.mdl.MolfileWriter;
import com.chemhack.jsMolEditor.client.model.DefaultMolecule;
import com.chemhack.jsMolEditor.client.model.Molecule;
import com.chemhack.jsMolEditor.client.model.Atom;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class TestMofileReader extends GWTTestCase {

    /**
     * Must refer to a valid module that sources this class.
     */
    public String getModuleName() {
        return "com.chemhack.jsMolEditor.Editor";
    }

    private String TEMPO = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n 11 11  0  0  0  0  0  0  0  0  0 V2000\n    7.3600   -6.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.2260   -7.1800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.0921   -6.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.0921   -5.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.2260   -5.1800    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n    7.3600   -5.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    9.5921   -4.8140    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n   10.0921   -5.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.8600   -4.8140    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    6.3600   -5.6800    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n    8.2260   -4.1800    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n  1  2  1  0  0  0  0\n  2  3  1  0  0  0  0\n  3  4  1  0  0  0  0\n  4  5  1  0  0  0  0\n  5  6  1  0  0  0  0\n  6  1  1  0  0  0  0\n  4  7  1  0  0  0  0\n  4  8  1  0  0  0  0\n  6  9  1  0  0  0  0\n  6 10  1  0  0  0  0\n  5 11  1  0  0 \nM  RAD  1  11   1\nM  END";

    private String methylRadical = "[NO NAME]\n  CHEMWRIT          2D\nCreated with ChemWriter - http://metamolecular.com/chemwriter\n  1  0  0  0  0  0  0  0  0  0  0 V2000\n    6.4000   -6.2000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\nM  RAD  1   1   1\nM  END";

    private MolfileReader reader = new MolfileReader();


    public void testItShouldReadSingletMethylRadical() {
        Molecule mr = new DefaultMolecule();

        reader.read(mr, methylRadical);

        assertEquals(1, mr.countAtoms());
        assertEquals(1, mr.getAtom(0).getRadical());
    }

    public void testItShouldReadTheOxygenCenteredRadicalInNMO() {
        Molecule tempo = new DefaultMolecule();

        reader.read(tempo, TEMPO);

        Atom o = null;

        for (int i = 0; i < tempo.countAtoms(); i++) {
            Atom test = tempo.getAtom(i);

            if (test.getSymbol().equals("O")) {
                o = test;

                break;
            }
        }

        assertNotNull(o);
        assertEquals(1, o.getRadical());
    }

    public void testModification() {
        Molecule tempo = new DefaultMolecule();

        reader.read(tempo, TEMPO);

        Atom o = null;

        for (int i = 0; i < tempo.countAtoms(); i++) {
            Atom test = tempo.getAtom(i);

            if (test.getSymbol().equals("O")) {
                o = test;
                o.setSymbol("P");
                break;
            }
        }

        assertNotNull(o);
        assertEquals(1, o.getRadical());

        MolfileWriter writer = new MolfileWriter();
        System.out.print(writer.write(tempo));
    }

}
