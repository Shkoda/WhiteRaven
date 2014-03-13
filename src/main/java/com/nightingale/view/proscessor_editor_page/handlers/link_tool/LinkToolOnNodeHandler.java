package com.nightingale.view.proscessor_editor_page.handlers.link_tool;

import com.google.inject.Inject;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.CreateLinkCommand;
import com.nightingale.view.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.vo.ProcessorLinkVO;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * Created by Nightingale on 13.03.14.
 */
public class LinkToolOnNodeHandler implements EventHandler<MouseEvent> {
 private ICommandProvider commandProvider;

    private ToggleButton linkButton;
    private IProcessorEditorMediator processorEditorMediator;
    private Node owner;

    public LinkToolOnNodeHandler(ICommandProvider commandProvider, ToggleButton linkButton, IProcessorEditorMediator processorEditorMediator, Node owner) {
        this.commandProvider = commandProvider;
        this.linkButton = linkButton;
        this.processorEditorMediator = processorEditorMediator;
        this.owner = owner;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (linkButton.isSelected() && mouseEvent.isPrimaryButtonDown()) {
            Node linkStart = processorEditorMediator.getLinkStart();
            if (linkStart == null) {
                processorEditorMediator.setLinkStart(owner);
            } else {
                processorEditorMediator.createLink(linkStart, owner);

            }
        }
    }
}
