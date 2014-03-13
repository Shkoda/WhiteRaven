package com.nightingale.view.proscessor_editor_page.mpp.link;

import com.nightingale.vo.ProcessorLinkVO;
import javafx.scene.Group;

/**
 * Created by Nightingale on 10.03.14.
 */
public interface ILinkView {

    void update(ProcessorLinkVO linkVO);

    Group getView();

    int getId();
}
