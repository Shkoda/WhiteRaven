package com.nightingale.view.view_components.editor.mpp_editor;

import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorLinkWeightListener;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ProcessorLinkInfoPane {
    private ToolBar toolBar;
    private TextField weightField, nameField;
    private CheckBox fullDuplex;

    private ProcessorLinkWeightListener weightListener;


    public ProcessorLinkInfoPane() {
        nameField = new TextField();
        nameField.setPrefWidth(50);
        nameField.setEditable(false);

        weightField = new TextField();
        weightField.setPrefWidth(50);
        weightField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    Integer.valueOf(weightField.getText() + keyEvent.getCharacter());
                } catch (Exception e) {
                    keyEvent.consume();
                }
            }
        });

        fullDuplex = new CheckBox("is full-duplex");
        fullDuplex.setDisable(true);

        toolBar = new ToolBar();
        toolBar.getItems().addAll(new Text("  "), new Text("Name: "), nameField, new Text("  "),
                new Text("Link weight: "), weightField,
                new Text("  "), fullDuplex);
        toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");

    }


    public void setParams(ProcessorLinkModel linkModel) {
        nameField.setText(linkModel.getName());
        weightField.setText(String.valueOf(linkModel.getWeight()));
        fullDuplex.setSelected(linkModel.isFullDuplexEnabled());
    }

    public void bindParams(final ProcessorLinkModel linkModel) {
        weightListener = new ProcessorLinkWeightListener(linkModel);
        weightField.textProperty().addListener(weightListener);
    }

    public void unbindParams() {
        if (weightListener == null) {
            return;
        }
        weightField.textProperty().removeListener(weightListener);

    }

    public ToolBar getToolBar() {
        return toolBar;
    }
}
