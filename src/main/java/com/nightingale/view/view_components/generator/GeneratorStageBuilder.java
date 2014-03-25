package com.nightingale.view.view_components.generator;

import com.nightingale.view.config.Config;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Nightingale on 24.03.2014.
 */
public class GeneratorStageBuilder {
    public static Stage build(){
        Stage modalStage = new Stage();
        modalStage.setMinHeight(Config.DIALOG_SCENE_HEIGHT);
        modalStage.setMinWidth(Config.DIALOG_SCENE_WIDTH);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Task Graph Generator Dialog");
        return modalStage;
    }
}
