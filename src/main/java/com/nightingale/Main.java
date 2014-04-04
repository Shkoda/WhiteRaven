package com.nightingale;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nightingale.application.guice.EventManagerModule;
import com.nightingale.view.config.Config;
import com.nightingale.view.main_page.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Nightingale on 09.03.14.
 */
public class Main extends Application {

    public static  Scene scene;


    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Injector injector = Guice.createInjector(new EventManagerModule());
        MainView mainView = injector.getInstance(MainView.class);

        stage.setMinHeight(Config.SCENE_HEIGHT);
        stage.setMinWidth(Config.SCENE_WIDTH);

        Pane mainPagePane;
        scene = SceneBuilder.create()
                .root(mainPagePane = mainView.getView())
                .width(Config.SCENE_WIDTH)
                .height(Config.SCENE_HEIGHT)
                .build();

        scene.getStylesheets().add(Main.class.getResource("/JMetroLightTheme.css").toExternalForm());


        mainPagePane.prefWidthProperty().bind(scene.widthProperty());
        mainPagePane.prefHeightProperty().bind(scene.heightProperty());

        mainPagePane.setStyle("-fx-background-color: #ffffff");

        stage.setScene(scene);
        stage.show();

    }
}