package com.chemhack.jsMolEditor.client.dialog;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import com.chemhack.jsMolEditor.client.controller.EditorController;
import com.chemhack.jsMolEditor.client.renderer.RendererModel;

public class OptionDialog extends DialogBox {
    

    public OptionDialog(final EditorController controller) {
        super();
        this.setText("Options");

        final RendererModel rendererModel = controller.getRenderer().getRendererModel();

        VerticalPanel dialogContent = new VerticalPanel();
        dialogContent.setWidth("300px");
        dialogContent.setSpacing(4);
        dialogContent.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

//        CheckBox cbShowImplicitHydrogens = new CheckBox("Show Implicit Hydrogens");
//        cbShowImplicitHydrogens.setChecked(rendererModel.isShowImplicitHydrogens());
//
//        CheckBox cbShowExplicitHydrogens = new CheckBox("Show Explicit Hydrogens");
//        cbShowExplicitHydrogens.setChecked(rendererModel.isShowExplicitHydrogens());
//
//        CheckBox cbShowEndCarbon = new CheckBox("Show End Carbon");
//        cbShowEndCarbon.setChecked(rendererModel.isShowEndCarbons());

        HorizontalPanel hpBondWidth = new HorizontalPanel();
        hpBondWidth.setSpacing(4);
        Label lbBondWidth = new Label("Bond Width:");
        final TextBox tbBondWidth = new TextBox();
        tbBondWidth.setMaxLength(5);
        tbBondWidth.setWidth("50px");
        tbBondWidth.setText(String.valueOf(rendererModel.getBondWidth()));
        tbBondWidth.addFocusListener(new FocusListener() {
            public void onFocus(Widget sender) {
                //Do nothing
            }

            public void onLostFocus(Widget sender) {
                TextBox tb = (TextBox) sender;
                try {
                    Double.valueOf(tb.getText());
                } catch (NumberFormatException e) {
                    Window.alert("Bond Width must be a valid float number.");
                    tb.setFocus(true);
                }
            }
        });
        Label lbBondWidth2 = new Label("Pixels");
        hpBondWidth.add(lbBondWidth);
        hpBondWidth.add(tbBondWidth);
        hpBondWidth.add(lbBondWidth2);

        HorizontalPanel hpBackColor = new HorizontalPanel();
        hpBackColor.setSpacing(4);
        Label lbBackColor = new Label("Background Color:");
        final TextBox tbBackColor = new TextBox();
        tbBackColor.setMaxLength(5);
        tbBackColor.setWidth("50px");
        tbBackColor.setText(String.valueOf(rendererModel.getBackColor()));
        hpBackColor.add(lbBackColor);
        hpBackColor.add(tbBackColor);

        HorizontalPanel hpForeColor = new HorizontalPanel();
        hpForeColor.setSpacing(4);
        Label lbForeColor = new Label("Foreground Color:");
        final TextBox tbForeColor = new TextBox();
        tbForeColor.setMaxLength(5);
        tbForeColor.setWidth("50px");
        tbForeColor.setText(String.valueOf(rendererModel.getForeColor()));
        hpForeColor.add(lbForeColor);
        hpForeColor.add(tbForeColor);


        HorizontalPanel hpButtons = new HorizontalPanel();
        hpButtons.setSpacing(5);

        Button btSave = new Button("Save");
        btSave.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                rendererModel.setBackColor(tbBackColor.getText());
                rendererModel.setForeColor(tbForeColor.getText());
                rendererModel.setBondWidth(Double.valueOf(tbBondWidth.getText()));
                controller.refreshView();
                hide();
            }
        });
        Button btSavePerm = new Button("Save Permanently");
        btSavePerm.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {

                hide();
            }
        });

        Button btCancel = new Button("Cancel");
        btCancel.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                hide();
            }
        });


        hpButtons.add(btSave);
        hpButtons.add(btCancel);

//        dialogContent.add(cbShowImplicitHydrogens);
//        dialogContent.add(cbShowExplicitHydrogens);
//        dialogContent.add(cbShowEndCarbon);
        dialogContent.add(hpBondWidth);
        dialogContent.add(hpBackColor);
        dialogContent.add(hpForeColor);

        dialogContent.add(hpButtons);
        dialogContent.setCellHorizontalAlignment(hpButtons, HasHorizontalAlignment.ALIGN_RIGHT);

        this.setWidget(dialogContent);

    }
}
