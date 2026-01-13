package traffic.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TrafficSignalStand extends Pane {

    private Circle red;
    private Circle yellow;
    private Circle green;

    /**
     * @param x X position
     * @param y Y position
     * @param rotateDeg 0 = normal, 180 = upside down
     */
    public TrafficSignalStand(double x, double y, double rotateDeg) {

        // Signal box
        Rectangle box = new Rectangle(25, 50);
        box.setFill(Color.BLACK);
        box.setArcWidth(6);
        box.setArcHeight(6);

        // Pole
        Rectangle pole = new Rectangle(4, 40);
        pole.setFill(Color.SADDLEBROWN);
        pole.setLayoutX(10);
        pole.setLayoutY(50);

        // Lights (small)
        red = new Circle(5, Color.RED);
        red.setLayoutX(12.5);
        red.setLayoutY(10);

        yellow = new Circle(5, Color.DARKGOLDENROD);
        yellow.setLayoutX(12.5);
        yellow.setLayoutY(25);

        green = new Circle(5, Color.DARKGREEN);
        green.setLayoutX(12.5);
        green.setLayoutY(40);

        getChildren().addAll(box, red, yellow, green, pole);

        setLayoutX(x);
        setLayoutY(y);
        setRotate(rotateDeg);

        setRed(); // default
    }

    // -------- Signal states --------

    public void setRed() {
        red.setFill(Color.RED);
        yellow.setFill(Color.DARKGOLDENROD);
        green.setFill(Color.DARKGREEN);
    }

    public void setGreen() {
        red.setFill(Color.DARKRED);
        yellow.setFill(Color.DARKGOLDENROD);
        green.setFill(Color.GREEN);
    }

    public void setYellow() {
        red.setFill(Color.DARKRED);
        yellow.setFill(Color.YELLOW);
        green.setFill(Color.DARKGREEN);
    }
}
