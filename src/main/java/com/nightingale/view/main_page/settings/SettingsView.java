package com.nightingale.view.main_page.settings;

import com.google.inject.Inject;
import com.nightingale.view.config.Config;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


/**
 * Created by Nightingale on 09.03.14.
 */
public class SettingsView implements ISettingsView {
    @Inject
    public SettingsMediator mediator;

    private MenuButton settingsButton;
    private MenuItem newProject, newMPP, newTasks, generateTasks,
            openProject, openMPP, openTasks,
            saveProject, saveMPP, saveTasks,
            exit;

    @Override
    public MenuButton getView() {
        if (settingsButton == null){
            initSettingsButton();
            mediator.initMenuItems();
        }
        return settingsButton;
    }

    private void initSettingsButton(){
        this.settingsButton = new MenuButton();
        settingsButton.setId("SettingsButton");
        settingsButton.setMinSize(Config.SYSTEM_MENU_BUTTON_SIZE, Config.SYSTEM_MENU_BUTTON_SIZE);
        settingsButton.setPrefSize(Config.SYSTEM_MENU_BUTTON_SIZE, Config.SYSTEM_MENU_BUTTON_SIZE);
        GridPane.setHalignment(settingsButton, HPos.CENTER);

        newProject = new MenuItem("New Project");
        newMPP = new MenuItem("New MPP");
        newTasks = new MenuItem("New Task Graph");
        generateTasks = new MenuItem("Generate Task Graph");

        openProject = new MenuItem("Open Project");
        openMPP = new MenuItem("Open MPP");
        openTasks = new MenuItem("Open Task Graph");

        saveProject = new MenuItem("Save Project");
        saveMPP = new MenuItem("Save MPP");
        saveTasks = new MenuItem("Save Task Graph");

        exit = new MenuItem("Exit");

        settingsButton.getItems().addAll(newProject, newMPP, newTasks, new SeparatorMenuItem(),
                generateTasks, new SeparatorMenuItem(),
                openProject, openMPP, openTasks, new SeparatorMenuItem(),
                saveProject, saveMPP, saveTasks, new SeparatorMenuItem(),
                exit);
    }

    public MenuItem getNewProject() {
        return newProject;
    }

    public MenuItem getNewMPP() {
        return newMPP;
    }

    public MenuItem getNewTasks() {
        return newTasks;
    }

    public MenuItem getGenerateTasks() {
        return generateTasks;
    }

    public MenuItem getOpenProject() {
        return openProject;
    }

    public MenuItem getOpenMPP() {
        return openMPP;
    }

    public MenuItem getOpenTasks() {
        return openTasks;
    }

    public MenuItem getSaveProject() {
        return saveProject;
    }

    public MenuItem getSaveMPP() {
        return saveMPP;
    }

    public MenuItem getSaveTasks() {
        return saveTasks;
    }

    public MenuItem getExit() {
        return exit;
    }
}
