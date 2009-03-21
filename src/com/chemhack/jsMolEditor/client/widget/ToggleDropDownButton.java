package com.chemhack.jsMolEditor.client.widget;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.Command;
import com.google.gwt.core.client.GWT;
import com.chemhack.jsMolEditor.client.resources.MyImageBundle;

import java.util.ArrayList;
import java.util.List;

public class ToggleDropDownButton extends Composite {
    MyImageBundle myImageBundle = (MyImageBundle) GWT.create(MyImageBundle.class);
    List<OptionButton> options = new ArrayList<OptionButton>();
    ToggleButton dropDownButton = new ToggleButton(myImageBundle.dropDownSmall().createImage());
    SimplePanel selectedOptionWrapper = new SimplePanel();
    HorizontalPanel hp = new HorizontalPanel();
    VerticalPanel dropDownMenu = new VerticalPanel();
    PopupPanel dropDownWrapper = new PopupPanel();
    boolean isMenuShowed = false;
    Command upAllToggle;
    ClickListener defaultOptionClickListener = new ClickListener() {
        public void onClick(Widget sender) {
            OptionButton optionButton = (OptionButton) sender;
            if (optionButton != selectedOptionWrapper.getWidget()) {     //Selecting new option from dropdown menu
                selectOption(optionButton);
                toggleMenuShowed();
            }
            if (upAllToggle != null) upAllToggle.execute();
            ((OptionButton) selectedOptionWrapper.getWidget()).setDown(true);

        }
    };

    private void toggleMenuShowed() {
        if (isMenuShowed) {
            hideDropDownMenu();
        } else {
            showDropDownMenu();
        }
    }

    public ToggleDropDownButton() {
        super();
        hp.add(selectedOptionWrapper);
        hp.add(dropDownButton);
        dropDownMenu.setWidth(hp.getOffsetWidth() + "px");
        dropDownWrapper.add(dropDownMenu);
        dropDownWrapper.setStyleName(null);

        initWidget(hp);

        dropDownButton.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                toggleMenuShowed();
            }
        });


    }

    private void selectOption(OptionButton selectedOption) {
        selectedOption.removeFromParent();
        selectedOption.setDown(false);
        selectedOptionWrapper.clear();
        selectedOptionWrapper.add(selectedOption);
    }

    private void showDropDownMenu() {
        dropDownWrapper.setPopupPosition(hp.getAbsoluteLeft(), hp.getAbsoluteTop() + hp.getOffsetHeight());
        dropDownWrapper.show();
        dropDownMenu.clear();
        for (OptionButton option : options) {
            if (option != selectedOptionWrapper.getWidget()) {
                option.setDown(false);
                dropDownMenu.add(option);
            }
        }
        dropDownButton.setDown(true);
        isMenuShowed = true;
    }

    private void hideDropDownMenu() {
        dropDownWrapper.hide();
        dropDownButton.setDown(false);
        isMenuShowed = false;
    }

    public void addOption(Image image, String text, ClickListener clickListener) {
        OptionButton option = new OptionButton(image, text, clickListener);
        options.add(option);

    }

    public void setSelectedIndex(int index) {
        selectOption(options.get(index));
    }


    private class OptionButton extends ToggleButton {
        public Image image;
        public String text;

        private OptionButton(Image image, String text, ClickListener clickListener) {
            super(image);
            setTitle(text);
            addClickListener(clickListener);
            addClickListener(defaultOptionClickListener);
        }
    }

    public void setDown(boolean down) {
        ((OptionButton) selectedOptionWrapper.getWidget()).setDown(down);
    }

    public void setUpAllToggle(Command upAllToggle) {
        this.upAllToggle = upAllToggle;
    }
}
