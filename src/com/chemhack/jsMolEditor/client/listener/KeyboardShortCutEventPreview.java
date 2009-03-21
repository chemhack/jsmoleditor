package com.chemhack.jsMolEditor.client.listener;

import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.Event;
import com.chemhack.jsMolEditor.client.controller.EditorController;

public class KeyboardShortCutEventPreview implements EventPreview {

    EditorController controller;

    public KeyboardShortCutEventPreview(EditorController controller) {
        this.controller = controller;
    }

    public boolean onEventPreview(Event event) {
        int type = DOM.eventGetType(event);
        switch (type) {
            case Event.ONKEYDOWN:
                return onKeyDownPreview((char) DOM.eventGetKeyCode(event),
                        KeyboardListenerCollection.getKeyboardModifiers(event));
            default:
                return true;
        }
    }

    private boolean onKeyDownPreview(char keyCode, int modifiers) {
//        System.out.println("key:" + (int)keyCode + " mod:" + modifiers);
        switch (modifiers) {
            case 0:
                switch (keyCode) {
                    case 'C':
                    case 'N':
                    case 'O':
                    case 'S':
                    case 'F':
                    case 'I':
                    case 'P':
                        controller.selectElement(String.valueOf(keyCode));
                        break;
                    case 'B':
                        controller.selectElement("Br");
                        break;
                    case 189: //Key -
                        controller.selectEditAction(EditorController.EditActions.drawSingleBond);
                        break;
                    case 187: //Key =
                        controller.selectEditAction(EditorController.EditActions.drawDoubleBond);
                        break;


                }
                return false;
            default:
                return true;
        }
    }
}
