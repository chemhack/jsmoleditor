package com.chemhack.jsMolEditor.client.listener;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ToggleButton;

import java.util.ArrayList;

public class ToggleButtonListener implements ClickListener {
    ArrayList<ToggleButton> singleSelectionButtons = new ArrayList<ToggleButton>();

    public void addToggleButton(ToggleButton toggleButton) {
        singleSelectionButtons.add(toggleButton);
    }

    public void onClick(Widget sender) {
        for (ToggleButton button : singleSelectionButtons) {
            button.setDown(false);
        }
        ((ToggleButton) sender).setDown(true);
    }
}
