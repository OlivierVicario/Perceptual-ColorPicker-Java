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

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

/**
 *
 * @author olivier
 */
public class ORectangle extends Group {

    public Line ligne;
    public Thumb thumb1;
    public Thumb thumb2;
    public int nbCouleurs;
    public Circle[] circleCarets;

    public ORectangle(double _startX, double _startY, double _endX, double _endY) {

        ligne = new Line(_startX, _startY, _endX, _endY);
        ligne.setStroke(Color.WHITE);
        ligne.setOpacity(0.5);
        this.getChildren().add(ligne);

        thumb1 = new Thumb(_startX, _startY, 5);
        thumb1.localisation = "debut";
        thumb2 = new Thumb(_endX, _endY, 5);
        thumb2.localisation = "fin";
        this.getChildren().add(thumb1);
        this.getChildren().add(thumb2);

        nbCouleurs = 2;
        circleCarets = new Circle[nbCouleurs];
        for (int i = 0; i < nbCouleurs; i++) {
            circleCarets[i] = new Circle();
            this.getChildren().add(circleCarets[i]);
        }

        dessinCarets(_startX, _startY, _endX, _endY);

        this.setFocusTraversable(true);//sinon ca ne tourne pas !

    }
    
    public Point2D rotation(Point2D p, Point2D c, double angle) {
        p = new Point2D(p.getX() - c.getX(), p.getY() - c.getY());
        double newx = p.getX() * Math.cos(angle) - p.getY() * Math.sin(angle);
        double newy = p.getX() * Math.sin(angle) + p.getY() * Math.cos(angle);
        Point2D newPoint = new Point2D(newx, newy);
        return newPoint.add(c);
        //return newPoint;
    }

    public void dessinCarets(double startX, double startY, double endX, double endY) {
        circleCarets[0].setCenterX(startX);
        circleCarets[0].setCenterY(endY);
        circleCarets[0].setRadius(2);
        circleCarets[0].setFill(Color.TRANSPARENT);
        circleCarets[0].setOpacity(0.5);
        circleCarets[0].setStroke(Color.WHITE);

        circleCarets[1].setCenterX(endX);
        circleCarets[1].setCenterY(startY);
        circleCarets[1].setRadius(2);
        circleCarets[1].setFill(Color.TRANSPARENT);
        circleCarets[1].setOpacity(0.5);
        circleCarets[1].setStroke(Color.WHITE);
    }

    class Thumb extends Circle {

        String localisation;

        Thumb(double centerX, double centerY, double radius) {
            super(centerX, centerY, radius);
            setStroke(Color.WHITE);
            setStrokeWidth(2);
            setOpacity(0.5);
            setFill(Color.TRANSPARENT);
            //final DragContext dragContext = new DragContext();

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

            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {

                }
            });

            setOnMouseDragged(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    if (me.getX() > 0 && me.getX() < 430 && me.getY() > 0 && me.getY() < 430) {
                        setCenterX(me.getX());
                        setCenterY(me.getY());
                        if (localisation == "debut") {
                            ligne.setStartX(getCenterX());
                            ligne.setStartY(getCenterY());
                        } else {
                            ligne.setEndX(getCenterX());
                            ligne.setEndY(getCenterY());
                        }
                        dessinCarets(ligne.getStartX(), ligne.getStartY(), ligne.getEndX(), ligne.getEndY());
                    }
                }
            });

        }
    }
}
