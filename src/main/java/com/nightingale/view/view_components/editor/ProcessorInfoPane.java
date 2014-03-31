package com.nightingale.view.view_components.editor;

import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorDuplexPropertyListener;
import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorIOPropertyChangeListener;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorLinkNumberListener;
import com.nightingale.view.view_components.editor.InfoPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;


/**
 * Created by Nightingale on 16.03.14.
 */

public class ProcessorInfoPane extends InfoPane<ProcessorModel> {

    private CheckBox isIOProcessor, fullDuplexEnabled;
    private TextField linkNumberField;

    private ProcessorIOPropertyChangeListener ioPropertyChangeListener;
    private ProcessorDuplexPropertyListener duplexPropertyListener;
    private ProcessorLinkNumberListener linkNumberListener;

    public ProcessorInfoPane() {
        super();

        isIOProcessor = new CheckBox("has IO");
        fullDuplexEnabled = new CheckBox("Fullduplex enabled");

        linkNumberField = new TextField();
        linkNumberField.setPrefWidth(50);
        linkNumberField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            try {
                Integer integer = Integer.valueOf(linkNumberField.getText() + ((KeyEvent) keyEvent).getCharacter());
                if (integer <= 0)
                    keyEvent.consume();
            } catch (Exception e) {
                keyEvent.consume();
            }
        });

        toolBar.getItems().addAll( new Text("  Link number: "), linkNumberField, new Text("  "), isIOProcessor, new Text("  "), fullDuplexEnabled);
    }

    @Override
    public void setParams(ProcessorModel processorModel) {
        super.setParams(processorModel);
        isIOProcessor.setSelected(processorModel.isHasIO());
        fullDuplexEnabled.setSelected(processorModel.isFullDuplexEnabled());
        linkNumberField.setText(String.valueOf(processorModel.getPhysicalLinkNumber()));
    }

    @Override
    public void bindParams(final ProcessorModel processorModel) {
        super.bindParams(processorModel);
        ioPropertyChangeListener = new ProcessorIOPropertyChangeListener(processorModel);
        duplexPropertyListener = new ProcessorDuplexPropertyListener(processorModel);
        linkNumberListener = new ProcessorLinkNumberListener(processorModel);

        isIOProcessor.selectedProperty().addListener(ioPropertyChangeListener);
        fullDuplexEnabled.selectedProperty().addListener(duplexPropertyListener);
        linkNumberField.textProperty().addListener(linkNumberListener);
    }

    @Override
    protected boolean listenersNotNull() {
        return super.listenersNotNull() && ioPropertyChangeListener != null
                && fullDuplexEnabled != null && linkNumberField != null;
    }

    @Override
    public void unbindParams() {
        if (!listenersNotNull())
            return;
        weightTextField.textProperty().removeListener(weightChangeListener);
        isIOProcessor.selectedProperty().removeListener(ioPropertyChangeListener);
        fullDuplexEnabled.selectedProperty().removeListener(duplexPropertyListener);
        linkNumberField.textProperty().removeListener(linkNumberListener);
    }

}
