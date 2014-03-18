package com.nightingale.view.statistics_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.modeller_page.ModellerView;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class StatisticsView implements IStatisticsView {

    @Override
    public Pane getView() {
        return new Pane();
    }

    @Inject
    public ModellerView modellerView;


    @Override
    public String getName() {
        return Config.STATISTICS_PAGE_NAME;
    }
    @Override
    public boolean isNavigationVisible() {
        return true;
    }

    @Override
    public ViewablePage getPreviousPage() {
        return modellerView;
    }

    @Override
    public ViewablePage getNextPage() {
        return null;
    }
}
