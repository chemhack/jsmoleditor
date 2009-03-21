package com.chemhack.jsMolEditor.client.resources;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface MyImageBundle extends ImageBundle {


    @Resource("move.png")
    public AbstractImagePrototype moveSmall();

    @Resource("new.png")
    public AbstractImagePrototype newSmall();

    @Resource("open.png")
    public AbstractImagePrototype openSmall();

    @Resource("save.png")
    public AbstractImagePrototype saveSmall();

    @Resource("eraser.png")
    public AbstractImagePrototype eraserSmall();

    @Resource("singleBond.png")
    public AbstractImagePrototype singleBondSmall();

    @Resource("doubleBond.png")
    public AbstractImagePrototype doubleBondSmall();

    @Resource("tripleBond.png")
    public AbstractImagePrototype tripleBondSmall();

    @Resource("options.png")
    public AbstractImagePrototype optionsSmall();

    @Resource("zoomIn.png")
    public AbstractImagePrototype zoomInSmall();

    @Resource("zoomOut.png")
    public AbstractImagePrototype zoomOutSmall();

    @Resource("element_C.png")
    public AbstractImagePrototype elementCSmall();
    
    @Resource("element_N.png")
    public AbstractImagePrototype elementNSmall();

    @Resource("element_O.png")
    public AbstractImagePrototype elementOSmall();

    @Resource("dropdown.png")
    public AbstractImagePrototype dropDownSmall();

    @Resource("benzeneSmall.png")
    public AbstractImagePrototype benzeneSmall();

}
