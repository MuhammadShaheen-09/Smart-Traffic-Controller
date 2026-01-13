package traffic.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

import traffic.controller.NormalTrafficController;
import traffic.controller.EmergencyTrafficController;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // ===== CONTROLLERS =====
        EmergencyTrafficController emergencyController =
                new EmergencyTrafficController();

        NormalTrafficController controller =
                new NormalTrafficController(emergencyController);

        // ===== VIEW =====
        IntersectionView view = new IntersectionView(controller);

        Label status = new Label("System Ready");

        // ===== NORMAL SIGNAL ACTIONS =====
        controller.setOpenRoadAAction(() -> {
            view.setRoadAGreen();
            status.setText("Road A GREEN");
        });

        controller.setOpenRoadBAction(() -> {
            view.setRoadBGreen();
            status.setText("Road B GREEN");
        });

        // ===== BUTTONS =====
        Button addA = new Button("Add Vehicle Road A");
        Button addB = new Button("Add Vehicle Road B");

        Button emergencyA = new Button("🚑 Emergency Road A");
        Button emergencyB = new Button("🚑 Emergency Road B");

        // ===== NORMAL VEHICLES =====
        addA.setOnAction(e -> {
            controller.addVehicleToRoadA();
            view.addCarRoadA();
        });

        addB.setOnAction(e -> {
            controller.addVehicleToRoadB();
            view.addCarRoadB();
        });

        // ===== EMERGENCY VEHICLES =====
        emergencyA.setOnAction(e -> {
            emergencyController.addEmergency(
                    EmergencyTrafficController.Road.A
            );
            view.addEmergencyRoadA();
        });

        emergencyB.setOnAction(e -> {
            emergencyController.addEmergency(
                    EmergencyTrafficController.Road.B
            );
            view.addEmergencyRoadB();
        });

        // ===== LAYOUT =====
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                view,
                addA,
                addB,
                emergencyA,
                emergencyB,
                status
        );

        Scene scene = new Scene(root, 650, 520);
        stage.setTitle("Smart Traffic Controller");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
