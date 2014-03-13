package com.nightingale.view.main_page;

import com.google.inject.Inject;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.start_page.IStartPageView;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 09.03.14.
 */
public class MainMediator implements IMainMediator {
    @Inject
    public ICommandProvider commandProvider;
    @Inject
    public IMainView mainView;
    @Inject
    public IStartPageView startPageView;

    public void init() {
        resetHandler(mainView.getBackButton(), startPageView);
    }




    @Override
    public void updateNavigation() {
        ViewablePage currentPage = mainView.getCurrentPage();

        Button backButton = mainView.getBackButton();
        Button previousButton = mainView.getPreviousButton();
        Button nextButton = mainView.getNextButton();

        boolean navigationVisible = currentPage.isNavigationVisible();

        backButton.setVisible(navigationVisible);
        previousButton.setVisible(navigationVisible && currentPage.getPreviousPage() != null);
        nextButton.setVisible(navigationVisible && currentPage.getNextPage() != null);

        resetHandler(previousButton, currentPage.getPreviousPage());
        resetHandler(nextButton, currentPage.getNextPage());
    }

    private void resetHandler(Button button, final ViewablePage goToPage) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                if (goToPage != null)
                    mainView.show(goToPage);
            }
        });
    }
}
