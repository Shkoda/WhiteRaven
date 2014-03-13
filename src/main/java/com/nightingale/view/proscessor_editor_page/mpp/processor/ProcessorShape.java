package com.nightingale.view.proscessor_editor_page.mpp.processor;

import com.nightingale.Main;
import com.nightingale.view.config.Config;
import com.nightingale.vo.ProcessorVO;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 10.03.14.
 */
public class ProcessorShape {

    public final static ImagePattern DEFAULT_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/processor.png")));
    public final static ImagePattern SELECTED_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/processor-selected.png")));
    public final static ImagePattern BAD_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/processor-bad.png")));


    public static Group build(ProcessorVO processorVO) {
        final Shape rectangle = new Rectangle(0, 0, Config.PROCESSOR_ELEMENT_WIDTH, Config.PROCESSOR_ELEMENT_WIDTH);
        rectangle.setFill(DEFAULT_IMAGE);

        final Group view = new Group();
        view.setTranslateX(processorVO.getTranslateX());
        view.setTranslateY(processorVO.getTranslateY());

        view.getChildren().addAll(rectangle, new Text(Config.PROCESSOR_ELEMENT_WIDTH - 5, Config.PROCESSOR_ELEMENT_HEIGHT + 10, processorVO.getName()));

        view.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                ((Node) me.getSource()).setCursor(Cursor.HAND);
            }
        });


        view.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                ((Node) me.getSource()).setCursor(Cursor.DEFAULT);
            }
        });

        return view;

    }
}
