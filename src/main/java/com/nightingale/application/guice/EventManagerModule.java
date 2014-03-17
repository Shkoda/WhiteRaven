package com.nightingale.application.guice;

import com.google.inject.AbstractModule;
import com.nightingale.command.editor.CheckMppCommand;
import com.nightingale.command.editor.CreateLinkCommand;
import com.nightingale.command.editor.CreateProcessorCommand;
import com.nightingale.command.editor.DeleteCommand;
import com.nightingale.command.menu.new_file.NewMppCommand;
import com.nightingale.command.menu.new_file.NewProjectCommand;
import com.nightingale.command.menu.new_file.NewTaskGraphCommand;
import com.nightingale.command.menu.open.OpenMppCommand;
import com.nightingale.command.menu.open.OpenProjectCommand;
import com.nightingale.command.menu.open.OpenTaskGraphCommand;
import com.nightingale.command.menu.save.SaveCommand;
import com.nightingale.model.DataManager;
import com.nightingale.service.DataService;
import com.nightingale.service.IDataService;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.view.editor.proscessor_editor_page.ProcessorEditorMediator;
import com.nightingale.view.main_page.IMainMediator;
import com.nightingale.view.main_page.IMainView;
import com.nightingale.view.main_page.MainMediator;
import com.nightingale.view.main_page.MainView;
import com.nightingale.view.main_page.settings.ISettingsMediator;
import com.nightingale.view.main_page.settings.ISettingsView;
import com.nightingale.view.main_page.settings.SettingsMediator;
import com.nightingale.view.main_page.settings.SettingsView;
import com.nightingale.view.modeller_page.IModellerMediator;
import com.nightingale.view.modeller_page.IModellerView;
import com.nightingale.view.modeller_page.ModellerMediator;
import com.nightingale.view.modeller_page.ModellerView;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorView;
import com.nightingale.view.editor.proscessor_editor_page.ProcessorEditorView;
import com.nightingale.view.editor.proscessor_editor_page.mpp.IMppMediator;
import com.nightingale.view.editor.proscessor_editor_page.mpp.IMppView;
import com.nightingale.view.editor.proscessor_editor_page.mpp.MppMediator;
import com.nightingale.view.editor.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.start_page.IStartPageMediator;
import com.nightingale.view.start_page.IStartPageView;
import com.nightingale.view.start_page.StartPageMediator;
import com.nightingale.view.start_page.StartPageView;
import com.nightingale.view.statistics_page.IStatisticsMediator;
import com.nightingale.view.statistics_page.IStatisticsView;
import com.nightingale.view.statistics_page.StatisticsMediator;
import com.nightingale.view.statistics_page.StatisticsView;
import com.nightingale.view.editor.tasks_editor_page.ITasksEditorMediator;
import com.nightingale.view.editor.tasks_editor_page.ITasksEditorView;
import com.nightingale.view.editor.tasks_editor_page.TasksEditorMediator;
import com.nightingale.view.editor.tasks_editor_page.TasksEditorView;
import com.nightingale.view.editor.tasks_editor_page.task_graph.ITaskGraphMediator;
import com.nightingale.view.editor.tasks_editor_page.task_graph.ITaskGraphView;
import com.nightingale.view.editor.tasks_editor_page.task_graph.TaskGraphMediator;
import com.nightingale.view.editor.tasks_editor_page.task_graph.TaskGraphView;

import javax.inject.Singleton;

/**
 * Guice module which maps the injections.
 */
public class EventManagerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventManagerModule.class).toInstance(this);

        mapViews();
        mapMediators();
        mapCommands();
        mapServices();
        mapModels();
        mapInfrastructure();

        bind(DataManager.class).in(Singleton.class);
    }

    private void mapViews() {
        bind(IMainView.class).to(MainView.class);
        bind(ISettingsView.class).to(SettingsView.class);

        bind(IStartPageView.class).to(StartPageView.class);
        bind(IProcessorEditorView.class).to(ProcessorEditorView.class);
     //   bind(ITasksEditorView.class).to(TasksEditorView.class);
        bind(IModellerView.class).to(ModellerView.class);
        bind(IStatisticsView.class).to(StatisticsView.class);

        bind(IMppView.class).to(MppView.class);
        bind(ITaskGraphView.class).to(TaskGraphView.class);
    }

    private void mapMediators() {
        bind(IMainMediator.class).to(MainMediator.class);
        bind(ISettingsMediator.class).to(SettingsMediator.class);

        bind(IStartPageMediator.class).to(StartPageMediator.class);
        bind(IProcessorEditorMediator.class).to(ProcessorEditorMediator.class);
        bind(ITasksEditorMediator.class).to(TasksEditorMediator.class);
        bind(IModellerMediator.class).to(ModellerMediator.class);
        bind(IStatisticsMediator.class).to(StatisticsMediator.class);

        bind(IMppMediator.class).to(MppMediator.class);
        bind(ITaskGraphMediator.class).to(TaskGraphMediator.class);
    }

    private void mapCommands() {
        bind(ICommandProvider.class).to(CommandProvider.class);

        bind(NewMppCommand.class);
        bind(NewTaskGraphCommand.class);
        bind(NewProjectCommand.class);

        bind(OpenMppCommand.class);
        bind(OpenTaskGraphCommand.class);
        bind(OpenProjectCommand.class);

        bind(SaveCommand.class);

        bind(CreateProcessorCommand.class);
        bind(DeleteCommand.class);
        bind(CreateLinkCommand.class);
        bind(CheckMppCommand.class);
    }

    private void mapServices() {
        bind(IDataService.class).to(DataService.class);

    }

    private void mapModels() {
    //    bind(IMppModel.class).to(MppModel.class).in(Singleton.class);
  //      bind(ITaskGraphModel.class).to(TaskGraphModel.class).in(Singleton.class);
    }

    private void mapInfrastructure() {
        //   bind(ITranslationProvider.class).to(ResourceBundleTranslationProvider.class).in(Singleton.class);
    }
}
