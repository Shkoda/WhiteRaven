package com.nightingale.view.utils;

import javafx.beans.property.SimpleDoubleProperty;

import java.io.Serializable;

/**
 * Created by Nightingale on 13.03.14.
 */
public class SerializableDoubleProperty extends SimpleDoubleProperty implements Serializable {

    @Override
    public String toString() {
        return "SerializableDoubleProperty{" +
                "value=" + get() +
                '}';
    }
}
