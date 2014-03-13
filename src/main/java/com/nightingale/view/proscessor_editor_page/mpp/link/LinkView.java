package com.nightingale.view.proscessor_editor_page.mpp.link;

import com.google.inject.Inject;
import com.nightingale.view.config.Config;
import com.nightingale.vo.ProcessorLinkVO;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 09.03.14.
 */
public class LinkView implements ILinkView {
    @Inject
    public ILinkMediator mediator;

    private  int id;
    private  int firstProcessorId, secondProcessorId;
    private int translateX1, translateY1;
    private int translateX2, translateY2;
    private String name;
    private boolean fullDuplexEnabled;

    protected Group view;

    @Override
    public void update(ProcessorLinkVO processorLinkVO) {
        id = processorLinkVO.getId();
        name = processorLinkVO.getName();

        firstProcessorId = processorLinkVO.getFirstProcessorId();
        secondProcessorId = processorLinkVO.getSecondProcessorId();

        translateX1 = processorLinkVO.getTranslateX1();
        translateY1 = processorLinkVO.getTranslateY1();

        translateX2 = processorLinkVO.getTranslateX2();
        translateY2 = processorLinkVO.getTranslateY2();
    }

    @Override
    public Group getView() {
        if (view == null) {
            Shape line = new Line(translateX1, translateY1, translateX2, translateY2);
            line.setFill(Color.ORANGE);


            view = new Group();
            view.setTranslateX(translateX1);
            view.setTranslateY(translateX2);

            view.getChildren().addAll(line, new Text(Config.PROCESSOR_ELEMENT_WIDTH - 5, Config.PROCESSOR_ELEMENT_HEIGHT + 10, name));
//
//            mediator.setMouseHandlers();
        }
        return view;
    }

    @Override
    public int getId() {
        return id;
    }
}
