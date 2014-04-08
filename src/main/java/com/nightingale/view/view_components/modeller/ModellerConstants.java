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

//    public static final String FIRST_QUEUE_ALGORITHM_TEXT = "(2) У  порядку  зростання  різниці \n" +
//            "між  пізнім  та  раннім  строками \n" +
//            "виконання вершин графу задачі";
//    public static final String SECOND_QUEUE_ALGORITHM_TEXT = "(6) У  порядку  спадання  критичних \n" +
//            "по  кількості  вершин  шляхів  до \n" +
//            "кінця графа задачі";
//    public static final String THIRD_QUEUE_ALGORITHM_TEXT = "(16) У  порядку  зростання \n" +
//            "критичного  по  часу  шляхів \n" +
//            "вершин  від  початку  графа задачі";

    public static final String QUEUE_PROMT_TEXT = "Метод формування черг...";

//    public static final String FIRST_LOADING_ALGORITHM_TEXT = "(3) Алгоритм  призначення  з  урахуванням" +
//            "\nпріоритетів  процесорів  (по  зв'язності)";
//    public static final String SECOND_LOADING_ALGORITHM_TEXT = "(5) Алгоритм  «сусіднього»  призначення  \n" +
//            "із  пересилками  «з  попередженням»";

    public static final String LOADING_PROMT_TEXT = "Алгоритм призначення...";

    public enum QueueType{
        QUEUE_2("(2) У  порядку  зростання  різниці \n" +
                "між  пізнім  та  раннім  строками \n" +
                "виконання вершин графу задачі"),
        QUEUE_6("(6) У  порядку  спадання  критичних \n" +
                "по  кількості  вершин  шляхів  до \n" +
                "кінця графа задачі"),
        QUEUE_16("(16) У  порядку  зростання \n" +
                "критичного  по  часу  шляхів \n" +
                "вершин  від  початку  графа задачі");

        public final String text;

        QueueType(String text) {
            this.text = text;
        }


        public static QueueType fromString(String string){
            for (QueueType type : QueueType.values())
                if (type.text.equals(string))
                    return type;
            return null;
        }
    }

    public enum LoadingType{
        ALGORITHM_3("(3) Алгоритм  призначення  з  урахуванням" +
                "\nпріоритетів  процесорів  (по  зв'язності)"),
        ALGORITHM_5("(5) Алгоритм  «сусіднього»  призначення  \n" +
                "із  пересилками  «з  попередженням»");

        public final String text;

        LoadingType(String text) {
            this.text = text;
        }

        public static LoadingType fromString(String string){
            for (LoadingType type : LoadingType.values())
                if (type.text.equals(string))
                    return type;
            return null;
        }
    }

    public static class ScheduleDescription{
        public static final ScheduleDescription QUEUE_2_SCHEDULE_3 = new ScheduleDescription(QueueType.QUEUE_2, LoadingType.ALGORITHM_3);
        public static final ScheduleDescription QUEUE_6_SCHEDULE_3 = new ScheduleDescription(QueueType.QUEUE_6, LoadingType.ALGORITHM_3);
        public static final ScheduleDescription QUEUE_16_SCHEDULE_3 = new ScheduleDescription(QueueType.QUEUE_16, LoadingType.ALGORITHM_3);

        public static final ScheduleDescription QUEUE_2_SCHEDULE_5 = new ScheduleDescription(QueueType.QUEUE_2, LoadingType.ALGORITHM_5);
        public static final ScheduleDescription QUEUE_6_SCHEDULE_5 = new ScheduleDescription(QueueType.QUEUE_6, LoadingType.ALGORITHM_5);
        public static final ScheduleDescription QUEUE_16_SCHEDULE_5 = new ScheduleDescription(QueueType.QUEUE_16, LoadingType.ALGORITHM_5);

        public final QueueType queueType;
        public final LoadingType loadingType;

        private ScheduleDescription(QueueType queueType, LoadingType loadingType) {
            this.queueType = queueType;
            this.loadingType = loadingType;
        }

        public static ScheduleDescription get(String queueTypeText, String loadingTypeText) {
            QueueType queueType = QueueType.fromString(queueTypeText);
            LoadingType loadingType = LoadingType.fromString(loadingTypeText);
            if (queueType == null || loadingType == null)
                throw  new NullPointerException();
            return new ScheduleDescription(queueType, loadingType);
        }

        @Override
        public String toString() {
            return "ScheduleDescription{" +
                    "queueType=" + queueType +
                    ", loadingType=" + loadingType +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ScheduleDescription that = (ScheduleDescription) o;
            return loadingType == that.loadingType && queueType == that.queueType;
        }

        @Override
        public int hashCode() {
            int result = queueType != null ? queueType.hashCode() : 0;
            result = 31 * result + (loadingType != null ? loadingType.hashCode() : 0);
            return result;
        }
    }


}























