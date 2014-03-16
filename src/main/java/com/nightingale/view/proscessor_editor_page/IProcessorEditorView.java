package com.nightingale.view.proscessor_editor_page;

import com.nightingale.view.ViewablePage;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
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
    void showProcessorInfoPane(ProcessorModel processorModel);
    void showLinkInfoPane(ProcessorLinkModel linkVO);
    void showMppInfoPane(boolean isMppOk);
    void hideInfoPane();

}
