package com.nightingale.view.view_components.editor;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


/**
 * Created by Nightingale on 16.03.14.
 */
public class CheckInfoBuilder {
    private static final Text CORRECT_MPP_TEXT, BAD_MPP_TEXT;
    public static final DropShadow GREEN_EFFECT;
    public static final DropShadow RED_EFFECT;

    static {
        GREEN_EFFECT = new DropShadow();
        GREEN_EFFECT.setRadius(5);
        GREEN_EFFECT.setColor(Color.GREEN);

        RED_EFFECT = new DropShadow();
        RED_EFFECT.setRadius(5);
        RED_EFFECT.setColor(Color.RED);
    }
    static {
        CORRECT_MPP_TEXT = new Text(" Mpp is correct");
        CORRECT_MPP_TEXT.setStyle(" -fx-font-size: 20; -fx-font-family: \"Segoe UI Light\";");
        CORRECT_MPP_TEXT.setEffect(GREEN_EFFECT);

        BAD_MPP_TEXT = new Text(" MPP is not connected graph");
        BAD_MPP_TEXT.setStyle("-fx-font-size: 20; -fx-font-family: \"Segoe UI Light\";");
        BAD_MPP_TEXT.setEffect(RED_EFFECT);
    }

    public static Text build(boolean mppIsOk) {
        return mppIsOk ? CORRECT_MPP_TEXT : BAD_MPP_TEXT;
    }
}
