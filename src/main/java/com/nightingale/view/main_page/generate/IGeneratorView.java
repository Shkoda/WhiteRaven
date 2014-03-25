package com.nightingale.view.main_page.generate;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Created by Nightingale on 24.03.2014.
 */
public interface IGeneratorView {
    Stage getView();
    TextField getMinVertexWeightField();
    TextField getMaxVertexWeightField();
    TextField getVertexNumberField();
    TextField getConnectivityField();
    Button getSubmitButton();
}
