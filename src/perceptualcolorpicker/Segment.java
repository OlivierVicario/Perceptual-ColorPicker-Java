/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptualcolorpicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author olivier
 */
public class Segment extends Base {

    int nbDivisions;
    public Circle[] circleDiv;

    Segment() {
        super();
    }

    Segment(double centerX, double centerY, double periX, double periY, int nbDiv) {
        super();
        center = new Thumb(centerX, centerY);
        peri = new Thumb(periX, periY);
        ChangeListener centerMoveX = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            peri.setCenterX(peri.getCenterX() + (newVal.doubleValue() - oldVal.doubleValue()));
            dessinDivisions(center.getCenterX(), center.getCenterY(), peri.getCenterX(), peri.getCenterY(), nbDiv);
        };
        ChangeListener centerMoveY = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            peri.setCenterY(peri.getCenterY() + (newVal.doubleValue() - oldVal.doubleValue()));
            dessinDivisions(center.getCenterX(), center.getCenterY(), peri.getCenterX(), peri.getCenterY(), nbDiv);
        };
        center.centerXProperty().addListener(centerMoveX);
        center.centerYProperty().addListener(centerMoveY);
        //à changer en invalidation listener
        ChangeListener periMoveX = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            dessinDivisions(center.getCenterX(), center.getCenterY(), peri.getCenterX(), peri.getCenterY(), nbDiv);
        };
        ChangeListener periMoveY = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            dessinDivisions(center.getCenterX(), center.getCenterY(), peri.getCenterX(), peri.getCenterY(), nbDiv);
        };
        peri.centerXProperty().addListener(periMoveX);
        peri.centerYProperty().addListener(periMoveY);        
        baseSegment = new Line(centerX, centerY, periX, periY);
        baseSegment.setStroke(Color.WHITE);
        baseSegment.setOpacity(0.5);
        baseSegment.startXProperty().bind(center.centerXProperty());
        baseSegment.startYProperty().bind(center.centerYProperty());
        baseSegment.endXProperty().bind(peri.centerXProperty());
        baseSegment.endYProperty().bind(peri.centerYProperty());
        this.getChildren().addAll(center, peri, baseSegment);

        circleDiv = new Circle[nbDiv - 2];
        for (int i = 0; i < nbDiv - 2; i++) {
            circleDiv[i] = new Circle();
            this.getChildren().add(circleDiv[i]);
        }

        dessinDivisions(center.getCenterX(), center.getCenterY(), peri.getCenterX(), peri.getCenterY(), nbDiv);
    }

    public void dessinDivisions(double startX, double startY, double endX, double endY, int divisions) {
        double deltaX = (endX - startX) / (divisions - 1);
        double deltaY = (endY - startY) / (divisions - 1);
        for (int i = 0; i < divisions - 2; i++) {
            circleDiv[i].setCenterX(startX + ((i + 1) * deltaX));
            circleDiv[i].setCenterY(startY + ((i + 1) * deltaY));
            circleDiv[i].setRadius(2);
            circleDiv[i].setFill(Color.TRANSPARENT);
            circleDiv[i].setOpacity(0.5);
            circleDiv[i].setStroke(Color.WHITE);
        }
    }
}
