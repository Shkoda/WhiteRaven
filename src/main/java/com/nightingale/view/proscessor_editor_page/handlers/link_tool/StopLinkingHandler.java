package com.nightingale.view.proscessor_editor_page.handlers.link_tool;

import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.CreateLinkCommand;
import com.nightingale.view.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.vo.ProcessorLinkVO;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class StopLinkingHandler implements EventHandler<MouseEvent> {

    private ToggleButton linkButton;
    private IProcessorEditorMediator processorEditorMediator;


    public StopLinkingHandler( ToggleButton linkButton, IProcessorEditorMediator processorEditorMediator) {
        this.linkButton = linkButton;
        this.processorEditorMediator = processorEditorMediator;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (linkButton.isSelected() && mouseEvent.isSecondaryButtonDown()) {
            processorEditorMediator.turnOffAllSelection();
        }
    }
}
