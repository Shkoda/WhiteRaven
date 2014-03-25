package com.nightingale.view.view_components.common;

import javafx.scene.control.TextField;

/**
 * Created by Nightingale on 24.03.2014.
 */
public class FilteredField extends TextField {
    public static final String POSITIVE_INTEGER_REGEXP = "[0-9]";
    public static final String DOUBLE_REGEXP = "[0-9]*\\.?[0-9]";

    private String regexp;

    public FilteredField(String regexp) {
        this.regexp = regexp;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        // If the replaced text would end up being invalid, then simply
        // ignore this call!
        if (text.matches("[0-9]")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (text.matches("[0-9]")) {
            super.replaceSelection(text);
        }
    }
}
