package com.nightingale.view.proscessor_editor_page.listeners;

import com.nightingale.vo.ProcessorVO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javax.annotation.processing.Processor;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ProcessorPerformanceChangeListener implements ChangeListener<String>{

    private final ProcessorVO processorVO;

    public ProcessorPerformanceChangeListener(ProcessorVO processorVO) {
        this.processorVO = processorVO;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        processorVO.setPerformance(Double.valueOf(newValue));
    }
}
