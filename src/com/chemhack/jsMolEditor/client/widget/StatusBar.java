package com.chemhack.jsMolEditor.client.widget;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class StatusBar extends Composite {
    HorizontalPanel hp = new HorizontalPanel();
    HTML message = new HTML();

    public StatusBar(int width,int height) {
        message.setStyleName("jsmoleditor-statusBar-message");
        hp.add(message);
        hp.setStyleName("jsmoleditor-statusBar");
        hp.setWidth(width+"px");
        hp.setHeight(height+"px");
        this.initWidget(hp);
    }

    public String getHTML() {
        return message.getHTML();
    }

    public void setHTML(String html) {
        message.setHTML(html);
    }
}
