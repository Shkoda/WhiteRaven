package com.nightingale.model;

import com.nightingale.model.mpp.IMppModel;
import com.nightingale.model.mpp.MppModel;
import com.nightingale.model.tasks.ITaskGraphModel;
import com.nightingale.model.tasks.TaskGraphModel;
import com.nightingale.utils.Loggers;

import java.io.Serializable;

/**
 * Created by Nightingale on 09.03.14.
 */

public class DataManager {
    private static ITaskGraphModel taskGraphModel = new TaskGraphModel();
    private static IMppModel mppModel = new MppModel();

    public static ITaskGraphModel getTaskGraphModel() {
        return taskGraphModel;
    }

    public static void resetTaskGraphModel(ITaskGraphModel taskGraphModel) {
        DataManager.taskGraphModel.reset(taskGraphModel);
    }

    public static IMppModel getMppModel() {
        return mppModel;
    }

    public static void resetMppModel(IMppModel mppModel) {
        DataManager.mppModel = mppModel;
        Loggers.debugLogger.debug(mppModel);
    }


    public static void reset(DataObject dataObject) {
        DataManager.taskGraphModel = dataObject.taskGraphModel;
        DataManager.mppModel = dataObject.mppModel;
    }

    public static class DataObject implements Serializable{
        public final IMppModel mppModel ;

        public final ITaskGraphModel taskGraphModel;

        public DataObject(IMppModel mppModel, ITaskGraphModel taskGraphModel) {
            this.taskGraphModel = taskGraphModel;
            this.mppModel = mppModel;
        }

        @Override
        public String toString() {
            return "DataObject{" +
                    "mppModel=" + mppModel +
                    ", taskGraphModel=" + taskGraphModel +
                    '}';
        }
    }

    public static String toStringValue() {
        return "DataManager{" +
                "taskGraphModel=" + taskGraphModel +
                ", mppModel=" + mppModel +
                '}';
    }
}
