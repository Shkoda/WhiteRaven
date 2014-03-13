package com.nightingale.view.proscessor_editor_page.mpp.link;

import com.nightingale.view.config.Config;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 13.03.14.
 */
public class LinkShape {
    public static Group build(ProcessorLinkVO linkVO) {
        Shape line = new Line(linkVO.getTranslateX1(), linkVO.getTranslateY1(), linkVO.getTranslateX2(), linkVO.getTranslateY2());
        line.setFill(Color.ORANGE);
        Group view = new Group();
        view.getChildren().addAll(line, new Text(Config.PROCESSOR_ELEMENT_WIDTH - 5, Config.PROCESSOR_ELEMENT_HEIGHT + 10, linkVO.getName()));
        return view;

    }
}
