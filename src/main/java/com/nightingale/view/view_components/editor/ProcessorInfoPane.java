package com.nightingale.view.view_components.editor;

import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorDuplexPropertyListener;
import com.nightingale.view.editor.proscessor_editor_page.listeners.ProcessorIOPropertyChangeListener;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.view.view_components.editor.InfoPane;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;


/**
 * Created by Nightingale on 16.03.14.
 */

public class ProcessorInfoPane extends InfoPane<ProcessorModel> {

    private CheckBox isIOProcessor, fullDuplexEnabled;

    private ProcessorIOPropertyChangeListener ioPropertyChangeListener;
    private ProcessorDuplexPropertyListener duplexPropertyListener;

    public ProcessorInfoPane() {
        super();

        isIOProcessor = new CheckBox("has IO");
        fullDuplexEnabled = new CheckBox("Fullduplex enabled");

        toolBar.getItems().addAll(new Text("  "), isIOProcessor, new Text("  "), fullDuplexEnabled);
    }

    @Override
    public void setParams(ProcessorModel processorModel) {
        super.setParams(processorModel);
        isIOProcessor.setSelected(processorModel.isHasIO());
        fullDuplexEnabled.setSelected(processorModel.isFullDuplexEnabled());
    }

    @Override
    public void bindParams(final ProcessorModel processorModel) {
        super.bindParams(processorModel);
        ioPropertyChangeListener = new ProcessorIOPropertyChangeListener(processorModel);
        duplexPropertyListener = new ProcessorDuplexPropertyListener(processorModel);

        isIOProcessor.selectedProperty().addListener(ioPropertyChangeListener);
        fullDuplexEnabled.selectedProperty().addListener(duplexPropertyListener);
    }

    @Override
    protected boolean listenersNotNull() {
        return super.listenersNotNull() && ioPropertyChangeListener!= null && fullDuplexEnabled != null;
    }

    @Override
    public void unbindParams() {
        if (!listenersNotNull())
            return;
        weightTextField.textProperty().removeListener(weightChangeListener);
        isIOProcessor.selectedProperty().removeListener(ioPropertyChangeListener);
        fullDuplexEnabled.selectedProperty().removeListener(duplexPropertyListener);
    }

}
