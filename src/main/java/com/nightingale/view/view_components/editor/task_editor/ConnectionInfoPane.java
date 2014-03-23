package com.nightingale.view.view_components.editor.task_editor;

import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.view.editor.common.listeners.ConnectionWeightListener;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ConnectionInfoPane {
    private ToolBar toolBar;
    private TextField weightField, nameField;

    private ConnectionWeightListener weightListener;


    public ConnectionInfoPane() {
        nameField = new TextField();
        nameField.setPrefWidth(50);
        nameField.setEditable(false);

        weightField = new TextField();
        weightField.setPrefWidth(50);
        weightField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            try {
                Integer.valueOf(weightField.getText() + ((KeyEvent) keyEvent).getCharacter());
            } catch (Exception e) {
                keyEvent.consume();
            }
        });


        toolBar = new ToolBar();
        toolBar.getItems().addAll(new Text("  "), new Text("Name: "), nameField, new Text("  "),
                new Text("Link weight: "), weightField);
        toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");

    }


    public void setParams(TaskLinkModel taskLinkModel) {
        nameField.setText(taskLinkModel.getName());
        weightField.setText(String.valueOf(taskLinkModel.getWeight()));
    }

    public void bindParams(final TaskLinkModel taskLinkModel) {
        weightListener = new ConnectionWeightListener(taskLinkModel);
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
