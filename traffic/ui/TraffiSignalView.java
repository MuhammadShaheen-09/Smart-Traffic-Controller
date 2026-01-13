package traffic.ui;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TraffiSignalView extends VBox {


    private Color[] colors = {
            Color.RED, Color.BLUE, Color.GREEN,
            Color.ORANGE, Color.PURPLE
    };


    private Circle red;
    private Circle green;
    private Rectangle road;
    private Label roadName;

    // 🚗 Cars
    private HBox carLane;
    private List<Rectangle> cars = new ArrayList<>();
    private List<TranslateTransition> animations = new ArrayList<>();

    public TraffiSignalView(String name) {

        roadName = new Label(name);

        red = new Circle(10, Color.RED);
        green = new Circle(10, Color.DARKGREEN);

        road = new Rectangle(220, 20, Color.GRAY);

        carLane = new HBox(5);
        carLane.setMinHeight(20);

        setSpacing(5);
        getChildren().addAll(roadName, red, green, road, carLane);

        setRed(); // default
    }

    // 🚗 Add a car to this road
    public void addCar() {

        Rectangle car = new Rectangle(30, 15);
        car.setArcWidth(6);
        car.setArcHeight(6);
        car.setFill(colors[cars.size() % colors.length]);

        cars.add(car);
        carLane.getChildren().add(car);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), car);
        tt.setFromX(0);
        tt.setToX(180);
        tt.setCycleCount(1);

        // 🔁 AFTER PASSING → REMOVE CAR
        tt.setOnFinished(e -> {
            carLane.getChildren().remove(car);
            cars.remove(car);
        });

        animations.add(tt);
    }




    // 🟢 Signal green → cars move
    public void setGreen() {
        green.setFill(Color.GREEN);
        red.setFill(Color.DARKRED);
        moveCars();
    }

    // 🔴 Signal red → cars stop
    public void setRed() {
        red.setFill(Color.RED);
        green.setFill(Color.DARKGREEN);
        stopCars();
    }

    private void moveCars() {
        for (TranslateTransition tt : animations) {
            tt.play();
        }
    }

    private void stopCars() {
        for (TranslateTransition tt : animations) {
            tt.pause();
        }
    }

    // 🚑 Emergency vehicle
    public void addEmergencyCar() {

        Rectangle ambulance = new Rectangle(35, 15);
        ambulance.setArcWidth(6);
        ambulance.setArcHeight(6);
        ambulance.setFill(Color.RED);

        carLane.getChildren().add(ambulance);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(2), ambulance);
        tt.setFromX(0);
        tt.setToX(180);
        tt.play(); // 🚑 always moves immediately
    }

}
