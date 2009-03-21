package com.chemhack.jsMolEditor.client.dialog;

import com.google.gwt.user.client.ui.*;
import com.chemhack.jsMolEditor.client.controller.EditorController;
import com.chemhack.jsMolEditor.client.io.mdl.MolfileWriter;

public class ExportMolFileDialog extends DialogBox {
    public ExportMolFileDialog(final EditorController controller) {
        super();

        this.setText("Export Mol File");
        VerticalPanel dialogContent = new VerticalPanel();
        dialogContent.setSpacing(4);
        HTML promote = new HTML("Mol File is generated below");
        final TextArea molfileContent = new TextArea();
        molfileContent.setPixelSize(400, 300);
        MolfileWriter molWriter=new MolfileWriter();
        molfileContent.setText(molWriter.write(controller.getMolecule()));
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setSpacing(5);

        Button btClose = new Button("Close");
        btClose.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                hide();
            }
        });

        buttonPanel.add(btClose);
        dialogContent.add(promote);
        dialogContent.add(molfileContent);
        dialogContent.add(buttonPanel);
        dialogContent.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);

        this.setWidget(dialogContent);
    }
}