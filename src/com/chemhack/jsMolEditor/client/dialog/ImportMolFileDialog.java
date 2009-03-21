package com.chemhack.jsMolEditor.client.dialog;

import com.google.gwt.user.client.ui.*;
import com.chemhack.jsMolEditor.client.controller.EditorController;

public class ImportMolFileDialog extends DialogBox {
    public ImportMolFileDialog(final EditorController controller) {
        super();

        this.setText("Import Mol File");
        VerticalPanel dialogContent = new VerticalPanel();
        dialogContent.setSpacing(4);
        HTML promote = new HTML("Paste Content below");
        final TextArea molfileContent = new TextArea();
        molfileContent.setPixelSize(400, 300);
//      molfileContent.setText();
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setSpacing(5);
        Button btConfirm = new Button("Insert");
        btConfirm.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
            controller.importMolFile(molfileContent.getText());
            hide();
            }
        });
        Button btCancel = new Button("Cancel");
        btCancel.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                hide();
            }
        });

        buttonPanel.add(btConfirm);
        buttonPanel.add(btCancel);
        dialogContent.add(promote);
        dialogContent.add(molfileContent);
        dialogContent.add(buttonPanel);
        dialogContent.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);

        this.setWidget(dialogContent);
    }
}
