package com.nightingale.view.view_components.statistics;

import com.nightingale.view.view_components.common.NumberField;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 21.03.2014.
 */
public class StatisticsToolBar {
    private ToolBar toolBar;
    private TextField minTaskNumberField, maxTaskNumberField;
    private TextField vertexNumberGrowStep;
    private TextField minTaskConnectivityField, maxTaskConnectivityField;
    private TextField connectivityGrowStep;
    private TextField minTaskWeightField, maxTaskWeightField;
    private TextField graphNumber;


    public StatisticsToolBar() {
        minTaskNumberField = NumberField.buildIntField(1, 23);
        maxTaskNumberField = NumberField.buildIntField(10, 23);
        vertexNumberGrowStep = NumberField.buildIntField(5, 23);

        minTaskConnectivityField = NumberField.buildDoubleField(1, 23);
        maxTaskConnectivityField = NumberField.buildDoubleField(20, 23);
        connectivityGrowStep = NumberField.buildDoubleField(20, 23);

        minTaskWeightField = NumberField.buildDoubleField(45, 23);
        maxTaskWeightField = NumberField.buildDoubleField(50, 23);
        graphNumber = NumberField.buildIntField(90, 23);


        toolBar = new ToolBar();
        toolBar.getItems().addAll(createTextLabel(" N(Task):"), minTaskNumberField, new Text("-"), maxTaskNumberField);
        toolBar.getItems().addAll(createTextLabel("  N(Vertex) grow step:"), vertexNumberGrowStep);
        toolBar.getItems().addAll(createTextLabel("  Task connectivity:"), minTaskConnectivityField, new Text("-"), maxTaskConnectivityField);
        toolBar.getItems().addAll(createTextLabel("  Connectivity step:"), connectivityGrowStep);
        toolBar.getItems().addAll(createTextLabel("  Task weight:"), minTaskWeightField, new Text("-"), maxTaskWeightField);
        toolBar.getItems().addAll(createTextLabel("  N(Graph):"), graphNumber);


        toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");
    }

    private Text createTextLabel(String text) {
        Text textField = new Text(text);
        textField.setStyle("-fx-font-size: 9");
        return textField;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }


}
