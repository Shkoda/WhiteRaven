package com.nightingale.view.proscessor_editor_page;

import com.nightingale.view.ViewablePage;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import javax.annotation.processing.Processor;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IProcessorEditorView  extends ViewablePage {
    ToggleButton getCursorButton();
    ToggleButton getAddProcessorButton();
    ToggleButton getLinkButton();
    Button getCheckButton();
    void showProcessorInfoPane(ProcessorVO processorVO);
    void showLinkInfoPane(ProcessorLinkVO linkVO);
    void hideInfoPane();

}
