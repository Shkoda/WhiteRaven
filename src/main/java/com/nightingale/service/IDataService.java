package com.nightingale.service;

import com.nightingale.model.DataManager;
import com.nightingale.model.entities.Graph;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IDataService {

    DataManager.DataObject createNewDataModel();

    Graph<ProcessorModel, ProcessorLinkModel> createNewMppModel();

    Graph<TaskModel, TaskLinkModel> createNewTaskGraph();

    DataManager.DataObject readDataModel(Path path) throws IOException;

    Graph<ProcessorModel, ProcessorLinkModel> readMppModel(Path path) throws IOException;

    Graph<TaskModel, TaskLinkModel> readTaskGraph(Path path) throws IOException;

    void save(Serializable object, Path path) throws IOException;

}
