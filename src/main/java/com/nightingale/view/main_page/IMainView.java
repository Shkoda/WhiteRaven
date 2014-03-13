package com.nightingale.view.main_page;


import com.nightingale.view.ViewablePage;
import javafx.scene.control.Button;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IMainView {

    void show(ViewablePage viewablePage);

    Button getPreviousButton();

    Button getNextButton();

    Button getBackButton();

    ViewablePage getCurrentPage();

}
