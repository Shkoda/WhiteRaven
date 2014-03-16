package com.nightingale.service;

import com.nightingale.model.DataManager;
import com.nightingale.model.common.Graph;
import com.nightingale.model.mpp.IMppModel;
import com.nightingale.model.mpp.MppModel;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import com.nightingale.model.tasks.ITaskGraphModel;
import com.nightingale.model.tasks.TaskGraphModel;
import com.nightingale.model.tasks.elements.TaskLinkModel;
import com.nightingale.model.tasks.elements.TaskModel;
import org.apache.commons.lang3.SerializationUtils;


import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Nightingale on 09.03.14.
 */
public class DataService implements IDataService {
    @Override
    public DataManager.DataObject createNewDataModel() {
        Graph<ProcessorModel, ProcessorLinkModel> mpp = new Graph<>(ProcessorModel.class, ProcessorLinkModel.class);
        Graph<TaskModel, TaskLinkModel> graphModel = new Graph<>(TaskModel.class, TaskLinkModel.class);
        return new DataManager.DataObject(graphModel, mpp);
    }

    @Override
    public Graph<ProcessorModel, ProcessorLinkModel> createNewMppModel() {
        return new Graph<>(ProcessorModel.class, ProcessorLinkModel.class);
    }

    @Override
    public Graph<TaskModel, TaskLinkModel> createNewTaskGraph() {
        return new Graph<>(TaskModel.class, TaskLinkModel.class);
    }

    @Override
    public DataManager.DataObject readDataModel(Path path) throws IOException {
        return (DataManager.DataObject) SerializationUtils.deserialize(Files.readAllBytes(path));
    }

    @Override
    public Graph<ProcessorModel, ProcessorLinkModel> readMppModel(Path path) throws IOException {
        return (Graph<ProcessorModel, ProcessorLinkModel>) SerializationUtils.deserialize(Files.readAllBytes(path));
    }

    @Override
    public Graph<TaskModel, TaskLinkModel> readTaskGraph(Path path) throws IOException {
        return (Graph<TaskModel, TaskLinkModel>) SerializationUtils.deserialize(Files.readAllBytes(path));
    }

    @Override
    public void save(Serializable object, Path path) throws IOException {
        Files.write(path, SerializationUtils.serialize(object));
    }
}
