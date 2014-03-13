package com.nightingale.view.proscessor_editor_page;

import com.nightingale.view.ViewablePage;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IProcessorEditorView  extends ViewablePage {
    ToggleButton getCursorButton();
    ToggleButton getAddProcessorButton();
    ToggleButton getLinkButton();
    Button getCheckButton();

}
