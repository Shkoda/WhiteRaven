package com.nightingale.view.modeller_page;

import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.view.view_components.modeller.ModellerConstants;

import java.util.Map;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IModellerMediator {
    void initQueueComboBox();

    void initScheduleComboBox();

    void refreshView();

    void refreshQueues();

    Map<String, String> getQueueMap();

    Map<ModellerConstants.ScheduleType, SystemModel> getScheduleMap();
}
