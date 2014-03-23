package com.nightingale.view.view_components.statistics;

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
        initNumberField(minTaskNumberField = new TextField(),23, 1);
        initNumberField(maxTaskNumberField= new TextField(), 23,10);
        initNumberField(vertexNumberGrowStep= new TextField(), 23,5);

        initNumberField(minTaskConnectivityField= new TextField(),23, 1);
        initNumberField(maxTaskConnectivityField= new TextField(),23, 20);
        initNumberField(connectivityGrowStep= new TextField(),23,20);

        initNumberField(minTaskWeightField= new TextField(),23, 45);
        initNumberField(maxTaskWeightField= new TextField(),23, 50);
        initNumberField(graphNumber= new TextField(), 23,90);



        toolBar = new ToolBar();
        toolBar.getItems().addAll(createTextLabel(" N(Task):"), minTaskNumberField, new Text("-"), maxTaskNumberField);
        toolBar.getItems().addAll(createTextLabel("  N(Vertex) grow step:"), vertexNumberGrowStep);
        toolBar.getItems().addAll(createTextLabel("  Task connectivity:"), minTaskConnectivityField, new Text("-"), maxTaskConnectivityField);
        toolBar.getItems().addAll(createTextLabel("  Connectivity step:"), connectivityGrowStep);
        toolBar.getItems().addAll(createTextLabel("  Task weight:"), minTaskWeightField, new Text("-"), maxTaskWeightField);
        toolBar.getItems().addAll(createTextLabel("  N(Graph):"), graphNumber);




        toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");

    }

    private Text createTextLabel(String text){
        Text textField = new Text(text);
        textField.setStyle("-fx-font-size: 9");
        return textField;
    }


    private void initNumberField(TextField textField, int width, int defaultValue) {
        textField.setPrefWidth(width);
        textField.setMaxWidth(width);
        textField.setPrefHeight(10);
        textField.setText(String.valueOf(defaultValue));
        textField.setStyle("-fx-font-size: 9");
        textField.setEditable(true);
        textField.addEventFilter(KeyEvent.KEY_TYPED, new NumberFilter(textField));

    }

    private class NumberFilter implements EventHandler<KeyEvent> {
        private TextField textField;

        private NumberFilter(TextField textField) {
            this.textField = textField;
        }

        @Override
        public void handle(KeyEvent event) {
            try {
                Integer.valueOf(textField.getText() + event.getCharacter());
            } catch (Exception e) {
                event.consume();
            }
        }
    }




    public ToolBar getToolBar() {
        return toolBar;
    }


}
