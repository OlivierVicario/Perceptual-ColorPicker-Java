/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptualcolorpicker;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author olivier
 */
class Thumb extends Circle {

    Thumb() {
        super();
    }

    Thumb(double centerX, double centerY) {
        super(centerX, centerY, 5);
        setStroke(Color.WHITE);
        setStrokeWidth(2);
        setOpacity(0.5);
        setFill(Color.TRANSPARENT);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                setStrokeWidth(4);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                setStrokeWidth(2);
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                if (me.getX() > 0 && me.getX() < 430 && me.getY() > 0 && me.getY() < 430) {
                    setCenterX(me.getX());
                    setCenterY(me.getY());
                }
            }
        });
    }
}
