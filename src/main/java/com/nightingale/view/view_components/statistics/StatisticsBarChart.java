package com.nightingale.view.view_components.statistics;

import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 22.03.2014.
 */
public class StatisticsBarChart {
    public final static String TASK_QUEUE_2_PLANNING_3 = "Task queue - (2), Planning - (3)";
    public final static String TASK_QUEUE_6_PLANNING_3 = "Task queue - (6), Planning - (3)";
    public final static String TASK_QUEUE_16_PLANNING_3 = "Task queue - (16), Planning - (3)";
    public final static String TASK_QUEUE_2_PLANNING_5 = "Task queue - (2), Planning - (5)";
    public final static String TASK_QUEUE_6_PLANNING_5 = "Task queue - (6), Planning - (5)";
    public final static String TASK_QUEUE_16_PLANNING_5 = "Task queue - (16), Planning - (5)";

    public final static String ACCELERATION_FACTOR = "Acceleration";
    public final static String EFFICIENCY_RATIO = "Efficiency";
    public final static String SCHEDULING_ALGORITHM_EFFICIENCY_RATIO = "Scheduling algorithm";

    private BarChart<String, Number> barChart;
    private Pane container;

    public StatisticsBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series TASK_QUEUE_2_PLANNING_3_SERIES = new StatisticsSeries(TASK_QUEUE_2_PLANNING_3, 3, 4, 2.6).series;
        XYChart.Series TASK_QUEUE_6_PLANNING_3_SERIES = new StatisticsSeries(TASK_QUEUE_6_PLANNING_3, 2, 4, 5.8).series;
        XYChart.Series TASK_QUEUE_16_PLANNING_3_SERIES = new StatisticsSeries(TASK_QUEUE_16_PLANNING_3, 4, 7, 4.4).series;
        XYChart.Series TASK_QUEUE_2_PLANNING_5_SERIES= new StatisticsSeries(TASK_QUEUE_2_PLANNING_5, 6, 1, 3.3).series;
        XYChart.Series TASK_QUEUE_6_PLANNING_5_SERIES = new StatisticsSeries(TASK_QUEUE_6_PLANNING_5, 8, 6, 8.1).series;
        XYChart.Series TASK_QUEUE_16_PLANNING_5_SERIES = new StatisticsSeries(TASK_QUEUE_16_PLANNING_5, 1.7, 4, 2.6).series;


        barChart.getData().addAll(TASK_QUEUE_2_PLANNING_3_SERIES, TASK_QUEUE_6_PLANNING_3_SERIES, TASK_QUEUE_16_PLANNING_3_SERIES, TASK_QUEUE_2_PLANNING_5_SERIES, TASK_QUEUE_6_PLANNING_5_SERIES, TASK_QUEUE_16_PLANNING_5_SERIES);
        barChart.setLegendSide(Side.RIGHT);

        barChart.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bababa");

        container = new Pane();

        //  chartContainer.setContent(bc);
        container.getChildren().addAll(barChart);
        container.widthProperty().addListener((observable, oldValue, newValue) -> {
            barChart.setPrefWidth(newValue.doubleValue());
        });

        container.heightProperty().addListener((observable, oldValue, newValue) -> {
            barChart.setPrefHeight(newValue.doubleValue());
        });


    }

    public Pane getContainer() {
        return container;
    }

    class StatisticsSeries {
        private XYChart.Series series;
        private XYChart.Data<String, Double> acceleration, efficiency, scheduling;

        StatisticsSeries(String name, double acceleration, double efficiency, double scheduling) {
            series = new XYChart.Series();
            series.setName(name);
            this.acceleration = new XYChart.Data<>(ACCELERATION_FACTOR, acceleration);
            this.efficiency = new XYChart.Data<>(EFFICIENCY_RATIO, efficiency);
            this.scheduling = new XYChart.Data<>(SCHEDULING_ALGORITHM_EFFICIENCY_RATIO, scheduling);

            series.getData().addAll(this.acceleration, this.efficiency, this.scheduling);
        }
    }
}
