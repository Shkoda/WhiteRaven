package com.nightingale.view.editor.tasks_editor_page;

import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import com.nightingale.view.ViewablePage;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface ITasksEditorView extends ViewablePage {
    ToggleButton getCursorButton();

    ToggleButton getAddTaskButton();

    ToggleButton getLinkButton();

    Button getCheckButton();

    void showTaskInfoPane(ProcessorModel processorModel);

    void showLinkInfoPane(ProcessorLinkModel linkVO);

    void showGraphInfoPane(boolean isMppOk);

    void hideInfoPane();

}
