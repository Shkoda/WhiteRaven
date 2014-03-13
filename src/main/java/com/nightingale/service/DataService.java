package com.nightingale.service;

import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.IMppModel;
import com.nightingale.model.mpp.MppModel;
import com.nightingale.model.tasks.ITaskGraphModel;
import com.nightingale.model.tasks.TaskGraphModel;
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
        IMppModel mpp = new MppModel();
        ITaskGraphModel graphModel = new TaskGraphModel();
        return new DataManager.DataObject(mpp, graphModel);
    }

    @Override
    public IMppModel createNewMppModel() {
        return new MppModel();
    }

    @Override
    public ITaskGraphModel createNewTaskGraph() {
        return new TaskGraphModel();
    }

    @Override
    public DataManager.DataObject readDataModel(Path path) throws IOException {
        return (DataManager.DataObject) SerializationUtils.deserialize(Files.readAllBytes(path));
    }

    @Override
    public IMppModel readMppModel(Path path) throws IOException {
        return (IMppModel) SerializationUtils.deserialize(Files.readAllBytes(path));
    }

    @Override
    public ITaskGraphModel readTaskGraph(Path path) throws IOException {
        return (ITaskGraphModel) SerializationUtils.deserialize(Files.readAllBytes(path));
    }

    @Override
    public void save(Serializable object, Path path) throws IOException {
        Files.write(path, SerializationUtils.serialize(object));
    }
}
