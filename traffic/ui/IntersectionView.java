package traffic.ui;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import traffic.controller.NormalTrafficController;

public class IntersectionView extends Pane {

    private NormalTrafficController controller;

    // Roads
    private Rectangle roadALeft, roadARight;
    private Rectangle roadBLeft, roadBRight;

    // Stop lines
    private Rectangle stopLineA, stopLineB;

    // Signal stands
    private TrafficSignalStand signalA;
    private TrafficSignalStand signalB;

    // Lanes
    private Pane laneA, laneB;

    // Vehicles
    private List<Rectangle> carsA = new ArrayList<>();
    private List<Rectangle> carsB = new ArrayList<>();

    private Random random = new Random();

    private static final int GAP = 45;
    private static final int Y_POS = 8;

    // ===== CONSTRUCTOR (🔥 FIXED) =====
    public IntersectionView(NormalTrafficController controller) {
        this.controller = controller;   // 🔥 THIS WAS THE BUG

        setPrefSize(600, 300);

        stopLineA = new Rectangle(255, 140, 5, 40);
        stopLineA.setFill(Color.WHITE);

        stopLineB = new Rectangle(340, 200, 5, 40);
        stopLineB.setFill(Color.WHITE);

        roadALeft = new Rectangle(0, 140, 260, 40);
        roadALeft.setFill(Color.DARKGRAY);

        roadARight = new Rectangle(340, 140, 260, 40);
        roadARight.setFill(Color.DARKGRAY);

        roadBLeft = new Rectangle(0, 200, 260, 40);
        roadBLeft.setFill(Color.DIMGRAY);

        roadBRight = new Rectangle(340, 200, 260, 40);
        roadBRight.setFill(Color.DIMGRAY);

        signalA = new TrafficSignalStand(235, 50, 0);
        signalB = new TrafficSignalStand(340, 240, 180);

        laneA = new Pane();
        laneA.setLayoutY(145);
        laneA.setPrefSize(600, 30);

        laneB = new Pane();
        laneB.setLayoutY(205);
        laneB.setPrefSize(600, 30);

        getChildren().addAll(
                roadALeft, roadARight,
                roadBLeft, roadBRight,
                stopLineA, stopLineB,
                laneA, laneB,
                signalA, signalB
        );

        setAllRed();
    }

    // ================= SIGNAL =================

    public void setAllRed() {
        signalA.setRed();
        signalB.setRed();
    }

    public void setRoadAGreen() {
        signalA.setGreen();
        signalB.setRed();
        releaseRoadA();   // 🔥 movement starts HERE
    }

    public void setRoadBGreen() {
        signalB.setGreen();
        signalA.setRed();
        releaseRoadB();
    }

    // ================= VEHICLES =================

    private Rectangle createRandomVehicle() {
        int type = random.nextInt(3);
        Rectangle v;

        if (type == 0)       v = new Rectangle(18, 10, Color.DARKRED);
        else if (type == 1)  v = new Rectangle(28, 14, Color.BLUE);
        else                 v = new Rectangle(40, 16, Color.DARKGREEN);

        v.setArcWidth(6);
        v.setArcHeight(6);
        v.setStroke(Color.BLACK);
        return v;
    }

    public void addCarRoadA() {
        Rectangle v = createRandomVehicle();
        carsA.add(v);
        laneA.getChildren().add(v);
        relayoutRoadA();
    }

    public void addCarRoadB() {
        Rectangle v = createRandomVehicle();
        carsB.add(v);
        laneB.getChildren().add(v);
        relayoutRoadB();
    }

    // ================= RELAYOUT =================

    private void relayoutRoadA() {
        int i = 0;
        for (Rectangle car : carsA) {
            car.setLayoutX(200 - i * GAP);
            car.setLayoutY(Y_POS);
            i++;
        }
    }

    private void relayoutRoadB() {
        int i = 0;
        for (Rectangle car : carsB) {
            car.setLayoutX(380 + i * GAP);
            car.setLayoutY(Y_POS);
            i++;
        }
    }

    // ================= RELEASE + CALLBACK =================

    private void releaseRoadA() {

        if (carsA.isEmpty()) {
            controller.onRoadCleared();
            return;
        }

        SequentialTransition seq = new SequentialTransition();

        for (Rectangle car : carsA) {
            TranslateTransition tt =
                    new TranslateTransition(Duration.seconds(1.2), car);
            tt.setByX(220);
            tt.setOnFinished(e -> laneA.getChildren().remove(car));
            seq.getChildren().add(tt);
        }

        seq.setOnFinished(e -> {
            carsA.clear();
            controller.onRoadCleared();   // 🔥 OPENS 2ND ROAD
        });

        seq.play();
    }

    private void releaseRoadB() {

        if (carsB.isEmpty()) {
            controller.onRoadCleared();
            return;
        }

        SequentialTransition seq = new SequentialTransition();

        for (Rectangle car : carsB) {
            TranslateTransition tt =
                    new TranslateTransition(Duration.seconds(1.2), car);
            tt.setByX(-220);
            tt.setOnFinished(e -> laneB.getChildren().remove(car));
            seq.getChildren().add(tt);
        }

        seq.setOnFinished(e -> {
            carsB.clear();
            controller.onRoadCleared();
        });

        seq.play();
    }
    // ================= 🚑 EMERGENCY VEHICLES =================

    public void addEmergencyRoadA() {
        Rectangle ev = new Rectangle(36, 16, Color.WHITE);
        ev.setStroke(Color.RED);
        ev.setArcWidth(6);
        ev.setArcHeight(6);

        carsA.add(ev);
        laneA.getChildren().add(ev);
        ev.toBack();

        relayoutRoadA();
    }

    public void addEmergencyRoadB() {
        Rectangle ev = new Rectangle(36, 16, Color.WHITE);
        ev.setStroke(Color.RED);
        ev.setArcWidth(6);
        ev.setArcHeight(6);

        carsB.add(ev);
        laneB.getChildren().add(ev);
        ev.toBack();

        relayoutRoadB();
    }

}
