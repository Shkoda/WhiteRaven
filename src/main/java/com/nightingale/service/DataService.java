package com.nightingale.service;

import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
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
        Graph<ProcessorModel, ProcessorLinkModel> mpp = new Graph<>(ProcessorModel.class, ProcessorLinkModel.class, false);
        Graph<TaskModel, TaskLinkModel> graphModel = new Graph<>(TaskModel.class, TaskLinkModel.class, true);
        return new DataManager.DataObject(graphModel, mpp);
    }

    @Override
    public Graph<ProcessorModel, ProcessorLinkModel> createNewMppModel() {
        return new Graph<>(ProcessorModel.class, ProcessorLinkModel.class, false);
    }

    @Override
    public Graph<TaskModel, TaskLinkModel> createNewTaskGraph() {
        return new Graph<>(TaskModel.class, TaskLinkModel.class, true);
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
