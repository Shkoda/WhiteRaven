package com.nightingale.view.proscessor_editor_page.listeners;

import com.nightingale.vo.ProcessorVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ProcessorIOPropertyChangeListener implements ChangeListener<Boolean> {

    private final ProcessorVO processorVO;

    public ProcessorIOPropertyChangeListener(ProcessorVO processorVO) {
        this.processorVO = processorVO;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        processorVO.setHasIO(newValue);
    }
}
