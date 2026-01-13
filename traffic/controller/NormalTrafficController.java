package traffic.controller;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import traffic.model.VehicleQueue;
import traffic.controller.EmergencyTrafficController.Road;

public class NormalTrafficController {

    private VehicleQueue roadA = new VehicleQueue();
    private VehicleQueue roadB = new VehicleQueue();

    private Runnable openRoadAAction;
    private Runnable openRoadBAction;

    private EmergencyTrafficController emergencyController;

    private Road firstRoad = null;
    private Road secondRoad = null;

    private PauseTransition analysisTimer;

    public NormalTrafficController(EmergencyTrafficController emergencyController) {
        this.emergencyController = emergencyController;

        analysisTimer = new PauseTransition(Duration.seconds(3));
        analysisTimer.setOnFinished(e -> decide());
    }

    // ===== UI CONNECT =====
    public void setOpenRoadAAction(Runnable action) {
        this.openRoadAAction = action;
    }

    public void setOpenRoadBAction(Runnable action) {
        this.openRoadBAction = action;
    }

    // ===== NORMAL INPUT =====
    public void addVehicleToRoadA() {
        roadA.addVehicle();
        restartTimer();
    }

    public void addVehicleToRoadB() {
        roadB.addVehicle();
        restartTimer();
    }

    private void restartTimer() {
        analysisTimer.stop();
        analysisTimer.play();
    }

    // ===== CORE DECISION =====
    private void decide() {

        // 🚑 EMERGENCY HAS ABSOLUTE PRIORITY
        if (emergencyController.hasEmergency()) {

            Road emergencyRoad = emergencyController.getNextEmergencyRoad();
            firstRoad = emergencyRoad;

            // agar dono emergency hain
            if (emergencyRoad == Road.A &&
                    emergencyController.getNextEmergencyRoad() != null) {
                secondRoad = Road.B;
            } else {
                secondRoad = null;
            }

            open(firstRoad);
            return;
        }

        // ===== NORMAL DENSITY =====
        int a = roadA.getCount();
        int b = roadB.getCount();

        if (a == 0 && b == 0) return;

        if (a >= b) {
            firstRoad = Road.A;
            secondRoad = (b > 0) ? Road.B : null;
        } else {
            firstRoad = Road.B;
            secondRoad = (a > 0) ? Road.A : null;
        }

        open(firstRoad);
    }

    private void open(Road road) {
        if (road == Road.A && openRoadAAction != null)
            openRoadAAction.run();

        if (road == Road.B && openRoadBAction != null)
            openRoadBAction.run();
    }

    // ===== CALLBACK FROM UI =====
    public void onRoadCleared() {

        // clear emergency if active
        if (firstRoad != null)
            emergencyController.clearEmergency(firstRoad);

        if (secondRoad != null) {
            Road next = secondRoad;
            secondRoad = null;
            firstRoad = next;
            open(firstRoad);
            return;
        }

        // reset
        firstRoad = null;
        roadA.clear();
        roadB.clear();
    }
}




//package traffic.controller;
//
//import javafx.animation.PauseTransition;
//import javafx.util.Duration;
//import traffic.model.VehicleQueue;
//
//public class NormalTrafficController {
//
//    private VehicleQueue roadA = new VehicleQueue();
//    private VehicleQueue roadB = new VehicleQueue();
//
//    private Runnable openRoadAAction;
//    private Runnable openRoadBAction;
//
//    // input wait
//    private PauseTransition analysisTimer;
//
//    // sequence state
//    private enum Road { A, B }
//    private Road firstRoad = null;
//    private Road secondRoad = null;
//
//    public NormalTrafficController() {
//        analysisTimer = new PauseTransition(Duration.seconds(3));
//        analysisTimer.setOnFinished(e -> decide());
//    }
//
//    // ===== UI CONNECT =====
//    public void setOpenRoadAAction(Runnable action) {
//        this.openRoadAAction = action;
//    }
//
//    public void setOpenRoadBAction(Runnable action) {
//        this.openRoadBAction = action;
//    }
//
//    // ===== INPUT =====
//    public void addVehicleToRoadA() {
//        roadA.addVehicle();
//        restartTimer();
//    }
//
//    public void addVehicleToRoadB() {
//        roadB.addVehicle();
//        restartTimer();
//    }
//
//    private void restartTimer() {
//        analysisTimer.stop();
//        analysisTimer.play();
//    }
//
//    // ===== DENSITY DECISION =====
//    private void decide() {
//
//        int a = roadA.getCount();
//        int b = roadB.getCount();
//
//        if (a == 0 && b == 0) return;
//
//        if (a >= b) {
//            firstRoad = Road.A;
//            secondRoad = (b > 0) ? Road.B : null;
//        } else {
//            firstRoad = Road.B;
//            secondRoad = (a > 0) ? Road.A : null;
//        }
//
//        open(firstRoad);
//    }
//
//    private void open(Road road) {
//        if (road == Road.A && openRoadAAction != null) {
//            openRoadAAction.run();
//        }
//        if (road == Road.B && openRoadBAction != null) {
//            openRoadBAction.run();
//        }
//    }
//
//    // ===== 🔥 UI MUST CALL THIS WHEN ROAD IS EMPTY =====
//    public void onRoadCleared() {
//
//        // first road just finished
//        if (firstRoad == Road.A) roadA.clear();
//        if (firstRoad == Road.B) roadB.clear();
//
//        if (secondRoad != null) {
//            Road next = secondRoad;
//            secondRoad = null;
//            firstRoad = next;
//            open(firstRoad);   // 🔥 second road opens HERE
//            return;
//        }
//
//        // cycle finished
//        firstRoad = null;
//        secondRoad = null;
//        roadA.clear();
//        roadB.clear();
//    }
//}
