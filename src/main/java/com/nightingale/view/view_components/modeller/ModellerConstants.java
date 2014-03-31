package com.nightingale.view.view_components.modeller;

import com.nightingale.view.utils.GridPosition;

/**
 * Created by Nightingale on 19.03.14.
 */
public class ModellerConstants {
    public static final GridPosition SELECT_QUEUE_ALGORITHM_POSITION = new GridPosition(0, 1);
    public static final GridPosition SELECT_LOAD_ALGORITHM_POSITION = new GridPosition(1, 1);
    public static final GridPosition OUEUE_PANE_POSITION = new GridPosition(0, 0);
    public static final GridPosition MPP_PANE_POSITION = new GridPosition(1, 0);

    public static final GridPosition OUEUE_POSITION = new GridPosition(0, 1);
    public static final GridPosition TASK_GRAPH_POSITION = new GridPosition(0, 0);

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

    public enum ScheduleType {
        QUEUE_2_SCHEDULE_3,
        QUEUE_6_SCHEDULE_3,
        QUEUE_16_SCHEDULE_3,

        QUEUE_2_SCHEDULE_5,
        QUEUE_6_SCHEDULE_5,
        QUEUE_16_SCHEDULE_5;

        public static ScheduleType get(String queueType, String scheduleType) {
            switch (scheduleType) {
                case FIRST_LOADING_ALGORITHM_TEXT:
                    switch (queueType) {
                        case FIRST_QUEUE_ALGORITHM_TEXT:
                            return QUEUE_2_SCHEDULE_3;
                        case SECOND_QUEUE_ALGORITHM_TEXT:
                            return QUEUE_6_SCHEDULE_3;
                        case THIRD_QUEUE_ALGORITHM_TEXT:
                            return QUEUE_16_SCHEDULE_3;
                        default:
                            return null;
                    }
                case SECOND_LOADING_ALGORITHM_TEXT:
                    switch (queueType) {
                        case FIRST_QUEUE_ALGORITHM_TEXT:
                            return QUEUE_2_SCHEDULE_5;
                        case SECOND_QUEUE_ALGORITHM_TEXT:
                            return QUEUE_6_SCHEDULE_5;
                        case THIRD_QUEUE_ALGORITHM_TEXT:
                            return QUEUE_16_SCHEDULE_5;
                        default:
                            return null;
                    }
                default:
                    return null;
            }
        }
    }
}























