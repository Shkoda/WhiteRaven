package com.nightingale.view.main_page.settings;

import com.google.inject.Inject;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.menu.new_file.NewMppCommand;
import com.nightingale.command.menu.new_file.NewProjectCommand;
import com.nightingale.command.menu.new_file.NewTaskGraphCommand;
import com.nightingale.command.menu.open.OpenMppCommand;
import com.nightingale.command.menu.open.OpenProjectCommand;
import com.nightingale.command.menu.open.OpenTaskGraphCommand;
import com.nightingale.command.menu.save.SaveCommand;
import com.nightingale.view.config.Config;
import com.nightingale.view.editor.proscessor_editor_page.mpp.IMppMediator;
import com.nightingale.view.editor.tasks_editor_page.task_graph.ITaskGraphMediator;
import com.nightingale.view.main_page.generate.IGeneratorView;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Nightingale on 09.03.14.
 */
public class SettingsMediator implements ISettingsMediator {

    @Inject
    public ICommandProvider commandProvider;
    @Inject
    public ISettingsView settingsView;
    @Inject
    public IMppMediator mppMediator;
    @Inject
    public ITaskGraphMediator taskGraphMediator;
    @Inject
    public IGeneratorView generatorView;

    @Override
    public void initMenuItems() {
        initNewMenuItems();
        initGenerateItem();
        initOpenMenuItems();
        initSaveMenuItems();
        initExitMenuItem();
    }

    private void initGenerateItem(){
        settingsView.getGenerateTasks().setOnAction(actionEvent -> {
           generatorView.getView().show();
        });
    }

    private void initOpenMenuItems() {
        settingsView.getOpenMPP().setOnAction(actionEvent -> {
            File file = createFileChooser("Open MPP", Config.MPP_EXTENSION_FILTER).showOpenDialog(null);
            if (file != null) {
                OpenMppCommand command = commandProvider.get(OpenMppCommand.class);
                command.setOnSucceeded(workerStateEvent -> {
                    mppMediator.refresh();
                });
                command.path = file.toPath();
                command.start();
            }
        });

        settingsView.getOpenTasks().setOnAction(actionEvent -> {
            File file = createFileChooser("Open Task Graph", Config.TASK_GRAPH_EXTENSION_FILTER).showOpenDialog(null);
            if (file != null) {
                OpenTaskGraphCommand command = commandProvider.get(OpenTaskGraphCommand.class);
                command.setOnSucceeded(workerStateEvent -> {
                    taskGraphMediator.refresh();
                });
                command.path = file.toPath();
                command.start();
            }
        });

        settingsView.getOpenProject().setOnAction(actionEvent -> {
            File file = createFileChooser("Open Project", Config.PROJECT_FILE_EXTENSION_FILTER).showOpenDialog(null);
            if (file != null) {
                OpenProjectCommand command = commandProvider.get(OpenProjectCommand.class);
                command.setOnSucceeded(workerStateEvent -> {
                    taskGraphMediator.refresh();
                    mppMediator.refresh();
                });
                command.path = file.toPath();
                command.start();
            }
        });
    }

    private void initSaveMenuItems() {
        settingsView.getSaveMPP().setOnAction(actionEvent -> {
            File file = createFileChooser("Save MPP", Config.MPP_EXTENSION_FILTER).showSaveDialog(null);
            if (file != null) {
                SaveCommand command = commandProvider.get(SaveCommand.class);
                command.path = updateExtension(file, Config.MPP_EXTENSION);
                command.type = SaveCommand.Type.SAVE_MPP;
                command.start();
            }
        });

        settingsView.getSaveTasks().setOnAction(actionEvent -> {
            File file = createFileChooser("Save Task Graph", Config.TASK_GRAPH_EXTENSION_FILTER).showSaveDialog(null);
            if (file != null) {
                SaveCommand command = commandProvider.get(SaveCommand.class);
                command.path = updateExtension(file, Config.TASK_GRAPH_EXTENSION);
                command.type = SaveCommand.Type.SAVE_TASK_GRAPH;
                command.start();
            }
        });

        settingsView.getSaveProject().setOnAction(actionEvent -> {
            File file = createFileChooser("Save Project", Config.PROJECT_FILE_EXTENSION_FILTER).showSaveDialog(null);
            if (file != null) {
                SaveCommand command = commandProvider.get(SaveCommand.class);
                command.path = updateExtension(file, Config.PROJECT_EXTENSION);
                command.type = SaveCommand.Type.SAVE_PROJECT;
                command.start();
            }
        });
    }

    private void initNewMenuItems() {
        settingsView.getNewMPP().setOnAction(actionEvent -> {
            NewMppCommand command = commandProvider.get(NewMppCommand.class);
            command.setOnSucceeded(workerStateEvent -> {
                taskGraphMediator.refresh();
                mppMediator.refresh();
            });
            command.start();
        });

        settingsView.getNewTasks().setOnAction(actionEvent -> {
            NewTaskGraphCommand command = commandProvider.get(NewTaskGraphCommand.class);
            command.setOnSucceeded(workerStateEvent -> {
                taskGraphMediator.refresh();
                mppMediator.refresh();
            });
            command.start();
        });

        settingsView.getNewProject().setOnAction(actionEvent -> {
            NewProjectCommand command = commandProvider.get(NewProjectCommand.class);
            command.setOnSucceeded(workerStateEvent -> {
                taskGraphMediator.refresh();
                mppMediator.refresh();
            });
            command.start();
        });
    }

    private void initExitMenuItem() {
        settingsView.getExit().setOnAction(actionEvent -> {
            System.exit(0);
        });
    }

    private static FileChooser createFileChooser(String title, FileChooser.ExtensionFilter extensionFilter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(extensionFilter);
        return fileChooser;
    }

    private static Path updateExtension(File file, String extension) {
        String insert = file.getAbsolutePath().endsWith("." + extension) ?
                "" : "." + extension;
        return Paths.get(file.toPath().toString() + insert);
    }


}
