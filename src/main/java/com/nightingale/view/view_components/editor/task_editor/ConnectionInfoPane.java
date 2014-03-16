package com.nightingale.view.view_components.editor.task_editor;

import com.nightingale.model.tasks.elements.TaskModel;
import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorLinkWeightListener;
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

    private ProcessorLinkWeightListener weightListener;


    public ConnectionInfoPane() {
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


        toolBar = new ToolBar();
        toolBar.getItems().addAll(new Text("  "), new Text("Name: "), nameField, new Text("  "),
                new Text("Link weight: "), weightField);
        toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");

    }


    public void setParams(TaskModel taskModel) {
        nameField.setText(taskModel.getName());
        weightField.setText(String.valueOf(taskModel.getWeight()));
    }

    public void bindParams(final TaskModel taskModel) {
//        weightListener = new ProcessorLinkWeightListener(taskModel);
//        weightField.textProperty().addListener(weightListener);
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
