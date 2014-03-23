package com.nightingale.view.view_components.editor;

import com.nightingale.model.entities.Informative;
import com.nightingale.view.editor.common.listeners.WeightChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 23.03.2014.
 */
public class InfoPane<I extends Informative> {
    protected ToolBar toolBar;
    protected TextField weightTextField, nameField;

    protected WeightChangeListener weightChangeListener;

    public InfoPane() {
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
                new Text("Weight: "), weightTextField);
        toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");
    }


    public void setParams(I informative) {
        nameField.setText(informative.getName());
        weightTextField.setText(String.valueOf(informative.getWeight()));
    }

    public void bindParams(final I informative) {
        weightChangeListener = new WeightChangeListener(informative);
        weightTextField.textProperty().addListener(weightChangeListener);
    }

    protected boolean listenersNotNull() {
        return weightChangeListener != null;
    }

    public void unbindParams() {
        if (!listenersNotNull())
            return;
        weightTextField.textProperty().removeListener(weightChangeListener);
    }

    public ToolBar getToolBar() {
        return toolBar;
    }
}
