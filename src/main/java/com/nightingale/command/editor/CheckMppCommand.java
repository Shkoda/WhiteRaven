package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import com.nightingale.view.utils.WeightedQuickUnionUF;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Nightingale on 16.03.14.
 */
public class CheckMppCommand extends Service<Boolean> {
    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
           List<ProcessorModel> processors = new ArrayList<>(DataManager.getMppModel().getProcessors());
                if (processors.size() == 0)
                    return false;
                if (processors.size() == 1)
                    return true;

                int processorModelId = processors.get(0).getId();

                int maxProcessorId = DataManager.getMppModel().getMaxProcessorId();
                WeightedQuickUnionUF unionUF = new WeightedQuickUnionUF(maxProcessorId+1);

                Collection<ProcessorLinkModel> links = DataManager.getMppModel().getLinks();
                for (ProcessorLinkModel linkModel : links)
                    unionUF.union(linkModel.getFirstProcessorId(), linkModel.getSecondProcessorId());

                for (int i = processors.size() - 1; i > 0; i--)
                    if (!unionUF.connected(processorModelId, processors.get(i).getId()))
                        return false;

                return true;
            }
        };
    }

}