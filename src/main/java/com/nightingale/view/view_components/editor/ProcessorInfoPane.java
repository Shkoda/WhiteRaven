package com.nightingale.view.view_components.editor;

import com.nightingale.view.proscessor_editor_page.listeners.ProcessorDuplexPropertyListener;
import com.nightingale.view.proscessor_editor_page.listeners.ProcessorIOPropertyChangeListener;
import com.nightingale.view.proscessor_editor_page.listeners.ProcessorPerformanceChangeListener;
import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.regex.Pattern;

/**
 * Created by Nightingale on 16.03.14.
 */

public class ProcessorInfoPane {
 
    private ToolBar toolBar;
    private TextField performanceTextField, nameField;
    private CheckBox isIOProcessor, fullDuplexEnabled;

    private ProcessorPerformanceChangeListener performanceChangeListener;
    private ProcessorIOPropertyChangeListener ioPropertyChangeListener;
    private ProcessorDuplexPropertyListener duplexPropertyListener;

    public ProcessorInfoPane() {
        nameField = new TextField();
        nameField.setPrefWidth(50);
        nameField.setEditable(false);

        performanceTextField = new TextField();
        performanceTextField.setPrefWidth(50);
        performanceTextField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    Double.valueOf(performanceTextField.getText() + keyEvent.getCharacter());
                } catch (Exception e) {
                    keyEvent.consume();
                }
            }
        });

        isIOProcessor = new CheckBox("has IO");
        fullDuplexEnabled = new CheckBox("Fullduplex enabled");

        toolBar = new ToolBar();
        toolBar.getItems().addAll(new Text("  "), new Text("Name: "), nameField, new Text("  "),
                new Text("Processor performance: "), performanceTextField,
                new Text("  "), isIOProcessor, new Text("  "), fullDuplexEnabled);
        toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");

    }


    public void setParams(ProcessorModel processorModel) {
        nameField.setText(processorModel.getName());
        performanceTextField.setText(String.valueOf(processorModel.getPerformance()));
        isIOProcessor.setSelected(processorModel.isHasIO());
        fullDuplexEnabled.setSelected(processorModel.isFullDuplexEnabled());
    }

    public void bindParams(final ProcessorModel processorModel) {
        performanceChangeListener = new ProcessorPerformanceChangeListener(processorModel);
        ioPropertyChangeListener = new ProcessorIOPropertyChangeListener(processorModel);
        duplexPropertyListener = new ProcessorDuplexPropertyListener(processorModel);

        performanceTextField.textProperty().addListener(performanceChangeListener);
        isIOProcessor.selectedProperty().addListener(ioPropertyChangeListener);
        fullDuplexEnabled.selectedProperty().addListener(duplexPropertyListener);
    }

    public void unbindParams() {
        if (performanceChangeListener == null || ioPropertyChangeListener == null || fullDuplexEnabled == null) {
            return;
        }

        performanceTextField.textProperty().removeListener(performanceChangeListener);
        isIOProcessor.selectedProperty().removeListener(ioPropertyChangeListener);
        fullDuplexEnabled.selectedProperty().removeListener(duplexPropertyListener);
    }

    public ToolBar getToolBar() {
        return toolBar;
    }
}
