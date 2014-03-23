package com.nightingale.view.view_components.editor.task_editor;

import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorDuplexPropertyListener;
import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorIOPropertyChangeListener;
import com.nightingale.view.editor.common.listeners.VertexWeightChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;


/**
 * Created by Nightingale on 16.03.14.
 */

public class TaskInfoPane {

    private ToolBar toolBar;
    private TextField weightTextField, nameField;

    private VertexWeightChangeListener vertexWeightChangeListener;


    public TaskInfoPane() {
        nameField = new TextField();
        nameField.setPrefWidth(50);
        nameField.setEditable(false);

        weightTextField = new TextField();
        weightTextField.setPrefWidth(50);
        weightTextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            try {
                Double.valueOf(weightTextField.getText() + ((KeyEvent) keyEvent).getCharacter());
            } catch (Exception e) {
                keyEvent.consume();
            }
        });


        toolBar = new ToolBar();
        toolBar.getItems().addAll(new Text("  "), new Text("Name: "), nameField, new Text("  "),
                new Text("Processor performance: "), weightTextField);
        toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");

    }


    public void setParams(TaskModel taskModel) {
        nameField.setText(taskModel.getName());
        weightTextField.setText(String.valueOf(taskModel.getWeight()));
    }

    public void bindParams(final TaskModel taskModel) {
        vertexWeightChangeListener = new VertexWeightChangeListener(taskModel);

        weightTextField.textProperty().addListener(vertexWeightChangeListener);

    }

    public void unbindParams() {
        if (vertexWeightChangeListener == null ) {
            return;
        }

        weightTextField.textProperty().removeListener(vertexWeightChangeListener);

    }



    public ToolBar getToolBar() {
        return toolBar;
    }
}
