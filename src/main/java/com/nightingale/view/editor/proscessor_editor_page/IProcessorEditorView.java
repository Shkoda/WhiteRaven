package com.nightingale.view.editor.proscessor_editor_page;

import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.view.editor.common.IEditorView;
import javafx.scene.control.Button;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IProcessorEditorView  extends IEditorView<ProcessorModel, ProcessorLinkModel> {

    Button getCheckButton();

    void showMppInfoPane(boolean isMppOk);

}
