package com.nightingale.view.view_components.modeller;

import javafx.scene.control.ComboBox;

/**
 * Created by Nightingale on 19.03.14.
 */
public class ModellerComboBoxBuilder {
    public static final String FIRST_QUEUE_ALGORITHM_TEXT = "(2) У  порядку  зростання  різниці \n" +
            "між  пізнім  та  раннім  строками \n" +
            "виконання вершин графу задачі";
    public static final String SECOND_QUEUE_ALGORITHM_TEXT = "(6) У  порядку  спадання  критичних \n" +
            "по  кількості  вершин  шляхів  до \n" +
            "кінця графа задачі";
    public static final String THIRD_QUEUE_ALGORITHM_TEXT = "(16) У  порядку  зростання \n" +
            "критичного  по  часу  шляхів \n" +
            "вершин  від  початку  графа задачі";

    public static final String QUEUE_PROMT_TEXT = "Метод формування черг...";

    public static final String FIRST_LOADING_ALGORITHM_TEXT = "(3) Алгоритм  призначення  з  урахуванням" +
            "\nпріоритетів  процесорів  (по  зв'язності)";
    public static final String SECOND_LOADING_ALGORITHM_TEXT = "(5) Алгоритм  «сусіднього»  призначення  \n" +
            "із  пересилками  «з  попередженням»";

    public static final String LOADING_PROMT_TEXT = "Алгоритм призначення...";

    public static ComboBox<String> buildQueueComboBox(){
        ComboBox<String> queueBox = new ComboBox<>();
        queueBox.getItems().addAll(FIRST_QUEUE_ALGORITHM_TEXT,
                SECOND_QUEUE_ALGORITHM_TEXT,
                THIRD_QUEUE_ALGORITHM_TEXT);
        queueBox.setPromptText(QUEUE_PROMT_TEXT);
        return queueBox;
    }

    public static ComboBox<String> buildLoadComboBox(){
        ComboBox<String> loadBox = new ComboBox<>();
        loadBox.getItems().addAll(FIRST_LOADING_ALGORITHM_TEXT,
                SECOND_LOADING_ALGORITHM_TEXT);
        loadBox.setPromptText(LOADING_PROMT_TEXT);
        return loadBox;
    }

}
