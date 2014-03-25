package com.nightingale.view.view_components.common;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Nightingale on 24.03.2014.
 */
public class NumberField {
    public static TextField buildIntField(int defaultValue, double width, double height) {
        return buildField(NumberFilter.toIntegerFunction, defaultValue, width, height);
    }

    public static TextField buildIntField(int defaultValue, double width) {
        TextField textField = buildField(NumberFilter.toIntegerFunction, defaultValue);
        setConstantWidth(textField, width);
        return textField;
    }

    public static TextField buildIntField(int defaultValue) {
        return buildField(NumberFilter.toIntegerFunction, defaultValue);
    }

    public static TextField buildIntField() {
        return buildField(NumberFilter.toIntegerFunction);
    }

    public static TextField buildDoubleField(double defaultValue, double width, double height) {
        return buildField(NumberFilter.toDoubleFunction, defaultValue, width, height);
    }

    public static TextField buildDoubleField(double defaultValue, double width) {
        TextField textField = buildField(NumberFilter.toDoubleFunction, defaultValue);
        setConstantWidth(textField, width);
        return textField;
    }

    public static TextField buildDoubleField(double defaultValue) {
        return buildField(NumberFilter.toDoubleFunction, defaultValue);
    }

    public static TextField buildDoubleField() {
        return buildField(NumberFilter.toDoubleFunction);
    }


    //---------------------------private---------------------------

    private static TextField buildField(BiFunction<String, KeyEvent, ? extends Number> filterFunction, Number defaultValue) {
        TextField textField = buildField(filterFunction);
        textField.setText(String.valueOf(defaultValue));
        return textField;
    }

    private static TextField buildField(BiFunction<String, KeyEvent, ? extends Number> filterFunction, Number defaultValue, double width, double height) {
        TextField textField = buildField(filterFunction, defaultValue);
        setConstantWidth(textField, width);
        setConstantHeight(textField, height);
        return textField;
    }

    private static TextField setConstantWidth(TextField textField, double width) {
        textField.setMinWidth(width);
        textField.setPrefWidth(width);
        textField.setMaxWidth(width);
        return textField;
    }

    private static TextField setConstantHeight(TextField textField, double height) {
        textField.setMinHeight(height);
        textField.setPrefHeight(height);
        textField.setMaxHeight(height);
        return textField;
    }

    private static TextField buildField(BiFunction<String, KeyEvent, ? extends Number> filterFunction) {
        TextField textField = new TextField();
        textField.setStyle("-fx-font-size: 9");
        textField.setEditable(true);
        textField.addEventFilter(KeyEvent.KEY_TYPED, new NumberFilter(textField, filterFunction));
        return textField;
    }

    public static class NumberFilter implements EventHandler<KeyEvent> {
        public static final BiFunction<String, KeyEvent, Integer> toIntegerFunction = (s, keyEvent) -> {
            int value = 0;
            try {
                value = Integer.valueOf(s + keyEvent.getCharacter());
            } catch (Exception e) {
                keyEvent.consume();
            }
            return value;
        };

        public static final BiFunction<String, KeyEvent, Double> toDoubleFunction = (s, keyEvent) -> {
            double value = 0;
            try {
                value = Double.valueOf(s + keyEvent.getCharacter());
            } catch (Exception e) {
                keyEvent.consume();
            }
            return value;
        };


        private TextField textField;
        private BiFunction<String, KeyEvent, ? extends Number> filterFunction;


        protected NumberFilter(TextField textField, BiFunction<String, KeyEvent, ? extends Number> filterFunction) {
            this.textField = textField;
            this.filterFunction = filterFunction;
        }

        @Override
        public void handle(KeyEvent event) {
            filterFunction.apply(textField.getText(), event);
        }
    }


}
