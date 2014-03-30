package com.nightingale.view.start_page;

import com.nightingale.view.ViewablePage;
import javafx.scene.control.Button;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IStartPageView extends ViewablePage {
     Button getProcessorEditorLink();

     Button getTaskEditorLink();

     Button getModellerLink();

     Button getStatisticsLink();

}
