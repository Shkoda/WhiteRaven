package com.nightingale.view.main_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.main_page.settings.ISettingsView;
import com.nightingale.view.start_page.StartPageView;
import com.nightingale.view.utils.*;
import com.nightingale.view.utils.ButtonBuilder;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.PaneBuilder;

import static com.nightingale.view.utils.PageGridBuilder.*;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class MainView implements IMainView {
    @Inject
    public MainMediator mediator;
    @Inject
    public StartPageView startPageView;
    @Inject
    public ISettingsView settings;

    private Pane view;
    private GridPane gridRootTemplate;
    private ViewablePage currentPage;
    private Pane currentViewPane;
    private Button backButton, previousButton, nextButton;


    public MainView() {
        currentPage = startPageView;

        previousButton = ButtonBuilder.createButton("PreviousButton", Config.PREVIOUS_NEXT_BUTTON_SIZE, null);
        nextButton = ButtonBuilder.createButton("NextButton", Config.PREVIOUS_NEXT_BUTTON_SIZE, null);
        backButton = ButtonBuilder.createButton("BackButton", Config.SYSTEM_MENU_BUTTON_SIZE, null);
    }

    public Pane getView() {
        if (currentPage == null)
            currentPage = startPageView;

        if (view == null) {
            gridRootTemplate = PageGridBuilder.getTemplate(currentPage.getName());

            gridRootTemplate.add(backButton, BACK_BUTTON_POSITION.columnNumber, BACK_BUTTON_POSITION.rowNumber);
            gridRootTemplate.add(previousButton, PREVIOUS_BUTTON_POSITION.columnNumber, PREVIOUS_BUTTON_POSITION.rowNumber);
            gridRootTemplate.add(nextButton, NEXT_BUTTON_POSITION.columnNumber, NEXT_BUTTON_POSITION.rowNumber);

            gridRootTemplate.add(settings.getView(), SETTINGS_BUTTON_POSITION.columnNumber, SETTINGS_BUTTON_POSITION.rowNumber);
            //       setNavigation(currentPage.isNavigationVisible(), currentPage.getPreviousPage(), currentPage.getNextPage());

            view = PaneBuilder.create()
                    .padding(new javafx.geometry.Insets(5))
                    .children(gridRootTemplate)
                    .build();

            gridRootTemplate.prefWidthProperty().bind(view.widthProperty());
            gridRootTemplate.prefHeightProperty().bind(view.heightProperty());

            resetContent();

            mediator.init();
            mediator.updateNavigation();
            //   gridRootTemplate.add(currentViewPane = currentPage.getView(), 1, 1);
        }
        return view;
    }


    private void resetPageName() {
        PageGridBuilder.clearCell(gridRootTemplate, NAME_POSITION);
        gridRootTemplate.add(new Label(currentPage.getName()),
                NAME_POSITION.columnNumber,
                NAME_POSITION.rowNumber);
    }

    private void resetContent() {
        PageGridBuilder.clearCell(gridRootTemplate, WORK_PANE_POSITION);
        gridRootTemplate.add(currentViewPane = currentPage.getView(), WORK_PANE_POSITION.columnNumber, WORK_PANE_POSITION.rowNumber);
    }

    @Override
    public void show(ViewablePage page) {
        currentPage = page;
        resetPageName();
        resetContent();
        mediator.updateNavigation();
    }

    @Override
    public Button getPreviousButton() {
        return previousButton;
    }

    @Override
    public Button getNextButton() {
        return nextButton;
    }

    @Override
    public Button getBackButton() {
        return backButton;
    }

    @Override
    public ViewablePage getCurrentPage() {
        return currentPage;
    }
}
