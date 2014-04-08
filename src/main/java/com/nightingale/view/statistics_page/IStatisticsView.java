package com.nightingale.view.statistics_page;

import com.nightingale.model.entities.statistics.ExperimentResult;
import com.nightingale.view.ViewablePage;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IStatisticsView extends ViewablePage {
    TextField getMinTaskNumberField();

    TextField getMaxTaskNumberField();

    TextField getVertexNumberGrowStep();

    TextField getMinTaskConnectivityField();

    TextField getMaxTaskConnectivityField();

    TextField getConnectivityGrowStep();

    TextField getMinTaskWeightField();

    TextField getMaxTaskWeightField();

    TextField getGraphNumber();

    Button getRefreshButton();

    void setStatistics(ObservableList<ExperimentResult> statistics);
}
