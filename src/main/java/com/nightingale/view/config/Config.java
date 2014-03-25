package com.nightingale.view.config;

import javafx.stage.FileChooser;

/**
 * Created by Nightingale on 16.02.14.
 */
public class Config {
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_HEIGHT = 500;

    public static final int DIALOG_SCENE_WIDTH = 400;
    public static final int DIALOG_SCENE_HEIGHT = 250;

    public static final double ANCHOR_OFFSET_WORK_AREA = 10;
    public static final double DISTANCE_BETWEEN_LINKS = 0;
    public static final int SYSTEM_MENU_BUTTON_SIZE = 30;

    public static final int PREVIOUS_NEXT_BUTTON_SIZE = 20;

    public static final int STATUS_HEADER_HEIGHT = 30;
    public static final int TOOL_HEIGHT = 50;

    public static final double LINK_WIDTH = ((SCENE_WIDTH - ANCHOR_OFFSET_WORK_AREA * 2) - DISTANCE_BETWEEN_LINKS * 2) / 3;

    public static final int MPP_CANVAS_WIDTH = 675;
    public static final int MPP_CANVAS_HEIGHT = 300;

    public static final int TASK_CANVAS_WIDTH = 675;
    public static final int TASK_CANVAS_HEIGHT = 500;

    public static final int PROCESSOR_CONNECTION_POINTS_PER_SIDE = 5;
    public static final int TASK_CONNECTION_POINTS_NUMBER = 20;


    public static final int PROCESSOR_ELEMENT_WIDTH = 40;
    public static final int PROCESSOR_ELEMENT_HEIGHT = 40;

    public static final int TASK_ELEMENT_WIDTH = 40;
    public static final int TASK_ELEMENT_HEIGHT = 40;

    public static final String MPP_EXTENSION = "mpp";
    public static final String MPP_EXTENSION_DESCRIPTION = "project file (*." + MPP_EXTENSION + ")";
    public static final FileChooser.ExtensionFilter MPP_EXTENSION_FILTER = new FileChooser.ExtensionFilter(MPP_EXTENSION_DESCRIPTION, "*." + MPP_EXTENSION);

    public static final String TASK_GRAPH_EXTENSION = "graph";
    public static final String TASK_GRAPH_EXTENSION_DESCRIPTION = "project file (*." + TASK_GRAPH_EXTENSION + ")";
    public static final FileChooser.ExtensionFilter TASK_GRAPH_EXTENSION_FILTER = new FileChooser.ExtensionFilter(TASK_GRAPH_EXTENSION_DESCRIPTION, "*." + TASK_GRAPH_EXTENSION);

    public static final String PROJECT_EXTENSION = "project";
    public static final String PROJECT_EXTENSION_DESCRIPTION = "project file (*." + PROJECT_EXTENSION + ")";
    public static final FileChooser.ExtensionFilter PROJECT_FILE_EXTENSION_FILTER = new FileChooser.ExtensionFilter(PROJECT_EXTENSION_DESCRIPTION, "*." + PROJECT_EXTENSION);

    public static final String START_PAGE_NAME = "Start Page";
    public static final String PROCESSOR_EDITOR_PAGE_NAME = "MPP Editor";
    public static final String TASK_EDITOR_PAGE_NAME = "Task Editor";
    public static final String MODELLER_PAGE_NAME = "Modeller";
    public static final String STATISTICS_PAGE_NAME = "Statistics";

    public static final int EDITOR_BUTTON_SIZE = 40;


}
