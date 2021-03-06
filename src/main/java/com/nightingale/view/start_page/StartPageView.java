package com.nightingale.view.start_page;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.view_components.startpage.StartPageGridBuilder;
import com.nightingale.view.view_components.common.ButtonBuilder;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import javax.naming.ldap.Control;

import static com.nightingale.view.config.Config.*;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class StartPageView implements IStartPageView {
    @Inject
    public IStartPageMediator mediator;

    private BorderPane root;
    private GridPane gridForLinks;
    private Button processorEditorLink, taskEditorLink, modellerLink, statisticsLink;


    @Override
    public Pane getView() {
        if (root == null) {
            initView();
            mediator.init();
        }
        return root;
    }

    private void initView() {
        root = new BorderPane();

        AnchorPane.setBottomAnchor(root, ANCHOR_OFFSET_WORK_AREA);

        gridForLinks = StartPageGridBuilder.build();

        processorEditorLink = ButtonBuilder.createButton("EditProcessorLinkButton", HPos.RIGHT, VPos.BOTTOM);
        taskEditorLink = ButtonBuilder.createButton("EditTasksLinkButton", HPos.LEFT, VPos.BOTTOM);
        modellerLink = ButtonBuilder.createButton("ModellingLinkButton", HPos.RIGHT, VPos.TOP);
        statisticsLink = ButtonBuilder.createButton("StatisticsLinkButton", HPos.LEFT, VPos.TOP);

        gridForLinks.add(processorEditorLink, 0, 0);
        gridForLinks.add(taskEditorLink, 1, 0);
        gridForLinks.add(modellerLink, 0, 1);
        gridForLinks.add(statisticsLink, 1, 1);

        root.setCenter(gridForLinks);
    }


    @Override
    public String getName() {
        return Config.START_PAGE_NAME;
    }

    @Override
    public boolean isNavigationVisible() {
        return false;
    }

    @Override
    public ViewablePage getPreviousPage() {
        return null;
    }

    @Override
    public ViewablePage getNextPage() {
        return null;
    }

    @Override
    public Button getProcessorEditorLink() {
        return processorEditorLink;
    }

    @Override
    public Button getTaskEditorLink() {
        return taskEditorLink;
    }

    @Override
    public Button getModellerLink() {
        return modellerLink;
    }

    @Override
    public Button getStatisticsLink() {
        return statisticsLink;
    }
}
