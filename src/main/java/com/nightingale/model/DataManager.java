package com.nightingale.model;

import com.nightingale.model.entities.Graph;

import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;

import java.io.Serializable;

/**
 * Created by Nightingale on 09.03.14.
 */

public class DataManager {
    private static Graph<ProcessorModel, ProcessorLinkModel> mpp = new Graph<>(ProcessorModel.class, ProcessorLinkModel.class, false);
    private static Graph<TaskModel, TaskLinkModel> taskGraph = new Graph<>(TaskModel.class, TaskLinkModel.class, true);


    public static Graph<TaskModel, TaskLinkModel> getTaskGraphModel() {
        return taskGraph;
    }

    public static void resetTaskGraphModel(Graph<TaskModel, TaskLinkModel> taskGraph) {
        DataManager.taskGraph = taskGraph;
        Loggers.debugLogger.debug(DataManager.taskGraph);
    }

    public static Graph<ProcessorModel, ProcessorLinkModel> getMppModel() {
        return mpp;
    }

    public static void resetMppModel(Graph<ProcessorModel, ProcessorLinkModel> mpp) {
        DataManager.mpp = mpp;
        Loggers.debugLogger.debug(DataManager.mpp);
    }


    public static void reset(DataObject dataObject) {
        DataManager.taskGraph = dataObject.taskGraph;
        DataManager.mpp = dataObject.mpp;
    }

    public static class DataObject implements Serializable {
        public final Graph<ProcessorModel, ProcessorLinkModel> mpp;

        public final Graph<TaskModel, TaskLinkModel> taskGraph;

        public DataObject(Graph<TaskModel, TaskLinkModel> taskGraph, Graph<ProcessorModel, ProcessorLinkModel> mpp) {
            this.taskGraph = taskGraph;
            this.mpp = mpp;
        }

        @Override
        public String toString() {
            return "DataObject{" +
                    "mpp=" + mpp +
                    ", taskGraph=" + taskGraph +
                    '}';
        }
    }

    public static String toStringValue() {
        return "DataManager{" +
                "taskGraph=" + taskGraph +
                ", mpp=" + mpp +
                '}';
    }
}
