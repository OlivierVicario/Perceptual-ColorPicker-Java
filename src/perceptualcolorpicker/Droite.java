package perceptualcolorpicker;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author olivier
 */
public class Droite extends Group {

    public Line ligne;
    public Thumb thumb1;
    public Thumb thumb2;
    public int nbCouleurs;
    public Circle[] circleCarets;

    public Droite(double _startX, double _startY, double _endX, double _endY, int _nbCouleurs) {

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

        nbCouleurs = _nbCouleurs;
        circleCarets = new Circle[nbCouleurs - 2];
        for (int i = 0; i < nbCouleurs - 2; i++) {
            circleCarets[i] = new Circle();
            this.getChildren().add(circleCarets[i]);
        }

        dessinCarets(_startX, _startY, _endX, _endY, nbCouleurs);

    }
//subtil: on ne redessinne pas on change la localisation !

    public void dessinCarets(double startX, double startY, double endX, double endY, int divisions) {

        double deltaX = (endX - startX) / (divisions - 1);
        double deltaY = (endY - startY) / (divisions - 1);
        for (int i = 0; i < divisions - 2; i++) {
            circleCarets[i].setCenterX(startX + ((i + 1) * deltaX));
            circleCarets[i].setCenterY(startY + ((i + 1) * deltaY));
            circleCarets[i].setRadius(2);
            circleCarets[i].setFill(Color.TRANSPARENT);
            circleCarets[i].setOpacity(0.5);
            circleCarets[i].setStroke(Color.WHITE);
        }
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
                        dessinCarets(ligne.getStartX(), ligne.getStartY(), ligne.getEndX(), ligne.getEndY(), nbCouleurs);
                    }
                }
            });
        }
    }
}
