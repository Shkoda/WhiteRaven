package com.nightingale.view.main_page.generate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


/**
 * Created by Nightingale on 24.03.2014.
 */
@Singleton
public class GeneratorMediator implements IGeneratorMediator {
    @Inject
    IGeneratorView generatorView;

    private TextField maxVertexWeightField, minVertexWeightField;
    private Button submitButton;
    private TextField taskNumberField;
    private TextField connectivityField;

    @Override
    public void initFieldValidators() {
        submitButton = generatorView.getSubmitButton();
        maxVertexWeightField = generatorView.getMaxVertexWeightField();
        minVertexWeightField = generatorView.getMinVertexWeightField();
        taskNumberField = generatorView.getVertexNumberField();
        connectivityField = generatorView.getConnectivityField();


        minVertexWeightField.textProperty().addListener((observable, oldValue, newValue) -> {
            Double minValue = newValue.length() == 0 ? Double.MAX_VALUE : Double.valueOf(newValue);
            Double maxValue = maxVertexWeightField.getText().length() == 0 ? Double.MIN_VALUE : Double.valueOf(maxVertexWeightField.getText());
            setStyle(minValue > 0 && minValue <= maxValue, minVertexWeightField, maxVertexWeightField);
        });

        maxVertexWeightField.textProperty().addListener((observable, oldValue, newValue) -> {
            Double minValue = minVertexWeightField.getText().length() == 0 ? Double.MAX_VALUE : Double.valueOf(minVertexWeightField.getText());
            Double maxValue = newValue.length() == 0 ? Double.MIN_VALUE : Double.valueOf(newValue);
            setStyle(minValue > 0 && minValue <= maxValue, minVertexWeightField, maxVertexWeightField);
        });


        taskNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer number = newValue.length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(newValue);
            setStyle(number > 0, taskNumberField);
        });

        connectivityField.textProperty().addListener((observable, oldValue, newValue) -> {
            Double connectivity = newValue.length() == 0 ? Double.MIN_VALUE : Double.valueOf(newValue);
            setStyle(connectivity > 0 && connectivity <= 1, connectivityField);
        });

    }


    private void setStyle(boolean allCorrect, Node... nodes) {
        String style = allCorrect ? null : "-fx-control-inner-background:  rgb(247, 157, 173)";
        for (Node node : nodes)
            node.setStyle(style);
        submitButton.setDisable(!allCorrect);
    }


}
