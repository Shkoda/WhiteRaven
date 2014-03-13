package com.nightingale.view.main_page.settings;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface ISettingsView {
    public MenuButton getView();

    public MenuItem getNewProject();

    public MenuItem getNewMPP();

    public MenuItem getNewTasks();

    public MenuItem getGenerateTasks();

    public MenuItem getOpenProject();

    public MenuItem getOpenMPP();

    public MenuItem getOpenTasks();

    public MenuItem getSaveProject();

    public MenuItem getSaveMPP();

    public MenuItem getSaveTasks();

    public MenuItem getExit();
}
