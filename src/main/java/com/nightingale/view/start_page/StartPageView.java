package com.nightingale.view.start_page;


import com.google.inject.Inject;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.view_components.startpage.StartPageGridBuilder;
import com.nightingale.view.view_components.common.ButtonBuilder;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static com.nightingale.view.config.Config.*;

/**
 * Created by Nightingale on 09.03.14.
 */
public class StartPageView implements IStartPageView {
    @Inject
    public IStartPageMediator mediator;

    private AnchorPane root;
    private GridPane gridForLinks;
    private Button processorEditorLink, taskEditorLink, modellerLink, statisticsLink;


    @Override
    public Pane getView() {
        if (root == null){
            initView();
            mediator.init();
        }
        return root;
    }

    private void initView(){
        root = new AnchorPane();
        AnchorPane.setBottomAnchor(root, ANCHOR_OFFSET_WORK_AREA);

        gridForLinks = StartPageGridBuilder.build();
        gridForLinks.prefHeightProperty().bind(root.heightProperty());
        gridForLinks.prefWidthProperty().bind(root.widthProperty());


        processorEditorLink = ButtonBuilder.createButton("EditProcessorLinkButton", HPos.RIGHT, VPos.BOTTOM);
        taskEditorLink = ButtonBuilder.createButton("EditTasksLinkButton", HPos.LEFT, VPos.BOTTOM);
        modellerLink = ButtonBuilder.createButton("ModellingLinkButton", HPos.RIGHT, VPos.TOP);
        statisticsLink = ButtonBuilder.createButton("StatisticsLinkButton", HPos.LEFT, VPos.TOP);

        gridForLinks.add(processorEditorLink, 0, 0);
        gridForLinks.add(taskEditorLink, 1, 0);
        gridForLinks.add(modellerLink, 0, 1);
        gridForLinks.add(statisticsLink, 1, 1);
        root.getChildren().setAll(gridForLinks);
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

    protected Button getProcessorEditorLink() {
        return processorEditorLink;
    }

    protected Button getTaskEditorLink() {
        return taskEditorLink;
    }

    protected Button getModellerLink() {
        return modellerLink;
    }

    protected Button getStatisticsLink() {
        return statisticsLink;
    }
}
