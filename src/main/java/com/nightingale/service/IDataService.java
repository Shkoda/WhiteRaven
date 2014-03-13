package com.nightingale.service;

import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.IMppModel;
import com.nightingale.model.tasks.ITaskGraphModel;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IDataService {

    DataManager.DataObject createNewDataModel();

    IMppModel createNewMppModel();

    ITaskGraphModel createNewTaskGraph();

    DataManager.DataObject readDataModel(Path path) throws IOException;

    IMppModel readMppModel(Path path) throws IOException;

    ITaskGraphModel readTaskGraph(Path path) throws IOException;

    void save(Serializable object, Path path) throws IOException;

}
