/*MIT License

Copyright (c) 2018 Olivier Vicario

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/
package perceptualcolorpicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author olivier
 */
public class Polygone extends Base {

    int nbSides;
    public Circle[] circleSommets;
    //double angleCP;

    Polygone() {
        super();
    }

    Polygone(double centerX, double centerY, double periX, double periY, int nbSom) {
        super();
        center = new Thumb(centerX, centerY);
        peri = new Thumb(periX, periY);
        nbSides = nbSom;
        ChangeListener centerMoveX = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            peri.setCenterX(peri.getCenterX() + (newVal.doubleValue() - oldVal.doubleValue()));
            dessinSommets(center.getCenterX(), center.getCenterY(), peri.getCenterX(), peri.getCenterY(), nbSom);
        };
        ChangeListener centerMoveY = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            peri.setCenterY(peri.getCenterY() + (newVal.doubleValue() - oldVal.doubleValue()));
            dessinSommets(center.getCenterX(), center.getCenterY(), peri.getCenterX(), peri.getCenterY(), nbSom);
        };
        center.centerXProperty().addListener(centerMoveX);
        center.centerYProperty().addListener(centerMoveY);
        //à changer en invalidation listener
        ChangeListener periMoveX = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {
            dessinSommets(center.getCenterX(), center.getCenterY(), newVal.doubleValue(), peri.getCenterY(), nbSom);
        };
        ChangeListener periMoveY = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) -> {

            dessinSommets(center.getCenterX(), center.getCenterY(), peri.getCenterX(), newVal.doubleValue(), nbSom);
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
        circleSommets = new Circle[nbSom];
        for (int i = 1; i < nbSom; i++) {
            circleSommets[i] = new Circle();
            this.getChildren().add(circleSommets[i]);
        }
        dessinSommets(center.getCenterX(), center.getCenterY(), peri.getCenterX(), peri.getCenterY(), nbSom);
    }

    public void dessinSommets(double centerX, double centerY, double periX, double periY, int nbSommets) {
        Point2D centre = new Point2D(centerX, centerY);
        Point2D peri = new Point2D(periX, periY);
        Point2D axeX = new Point2D(1, 0);
        Point2D vecteurPeriCentre = new Point2D(peri.getX() - centre.getX(), peri.getY() - centre.getY());
        double angleCP = axeX.angle(vecteurPeriCentre) * Math.PI / 180;
        double radius = centre.distance(peri);
        double angle = 2 * Math.PI / nbSommets;
        for (int i = 1; i < nbSommets; i++) {
            if (periY > centerY) {
                circleSommets[i].setCenterX(centre.getX() + Math.cos(angleCP + angle * i) * radius);
                circleSommets[i].setCenterY(centre.getY() + Math.sin(angleCP + angle * i) * radius);
            } else {
                circleSommets[i].setCenterX(centre.getX() + Math.cos(-(angleCP + angle * i)) * radius);
                circleSommets[i].setCenterY(centre.getY() + Math.sin(-(angleCP + angle * i)) * radius);
            }
            circleSommets[i].setRadius(2);
            circleSommets[i].setFill(Color.TRANSPARENT);
            circleSommets[i].setOpacity(0.5);
            circleSommets[i].setStroke(Color.WHITE);
        }
    }
}
