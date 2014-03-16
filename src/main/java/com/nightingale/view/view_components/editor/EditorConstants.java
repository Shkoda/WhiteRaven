package com.nightingale.view.view_components.editor;

import com.nightingale.view.utils.GridPosition;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;


/**
 * Created by Nightingale on 09.03.14.
 */
public class EditorConstants {

    public static final GridPosition TOOLBAR_POSITION = new GridPosition(0, 2);
    public static final GridPosition CANVAS_POSITION = new GridPosition(0, 1);
    public static final GridPosition INFO_BAR_POSITION = new GridPosition(0, 0);
    public static final DropShadow SELECTION_EFFECT;
    public static final DropShadow START_LINK_EFFECT;

    static {
        SELECTION_EFFECT = new DropShadow();
        SELECTION_EFFECT.setRadius(5);
        SELECTION_EFFECT.setColor(Color.GREEN);

        START_LINK_EFFECT = new DropShadow();
        START_LINK_EFFECT.setRadius(5);
        START_LINK_EFFECT.setColor(Color.ORANGE);
    }

}
