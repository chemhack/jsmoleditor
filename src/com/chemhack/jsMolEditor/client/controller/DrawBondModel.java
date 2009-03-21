package com.chemhack.jsMolEditor.client.controller;

import com.chemhack.jsMolEditor.client.model.Atom;

public class DrawBondModel {
    public Atom startAtom;
    public Atom newAtom;
    public boolean activated = false;
    public boolean isClickBond = true;
    public int bondType = 1;
}
