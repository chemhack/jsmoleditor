package com.chemhack.jsMolEditor.client.widget;

import com.google.gwt.user.client.ui.*;
import com.chemhack.jsMolEditor.client.controller.EditorController;
import com.chemhack.jsMolEditor.client.listener.ToggleButtonListener;

import java.util.HashMap;


public class ElementToolBox extends VerticalPanel {
    ToggleButtonListener toggleButtonListener;
    HashMap<String, ToggleButton> elements = new HashMap<String, ToggleButton>();
    EditorController controller;
    public ElementToolBox(final EditorController controller, ToggleButtonListener toggleButtonListener) {
        super();
        this.controller=controller;
        this.toggleButtonListener = toggleButtonListener;

        this.setStyleName("jsmoleditor-elementToolBox");
        String[] elements = {"C", "N", "O", "S", "F", "Cl", "Br", "I", "P"};
        for (String element : elements) {
            this.add(createToggleButton(element));
        }

    }

    private ToggleButton createToggleButton(final String element) {
        ToggleButton tb = new ToggleButton();
        tb.setStylePrimaryName("jsmoleditor-toolButton");
        tb.addStyleName("jsmoleditor-elementToolBox-element-" + element);
        tb.setHTML(element);
        toggleButtonListener.addToggleButton(tb);
        tb.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
               controller.selectElement(element);
            }
        });
//        tb.addClickListener(toggleButtonListener);
        elements.put(element, tb);
        return tb;
    }

    public void setSelectedElement(String element) {
        ToggleButton tb = elements.get(element);
        toggleButtonListener.onClick(tb);
        tb.setDown(true);
    }
}