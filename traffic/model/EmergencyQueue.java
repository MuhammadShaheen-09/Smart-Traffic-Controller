package traffic.model;

import java.util.LinkedList;
import java.util.Queue;

public class EmergencyQueue {

    private Queue<String> emergencyQueue;

    public EmergencyQueue() {
        emergencyQueue = new LinkedList<>();
    }

    // 🚑 Add emergency vehicle
    public void addEmergencyVehicle(String road) {
        if (road == null) return;

        emergencyQueue.offer(road.toUpperCase());
    }

    // ✅ CHECK if any emergency exists
    public boolean hasEmergency() {
        return !emergencyQueue.isEmpty();
    }

    // 🚦 Get next emergency (FIFO)
    public String pollEmergency() {
        return emergencyQueue.poll();
    }

    // 🧹 Optional clear
    public void clear() {
        emergencyQueue.clear();
    }
}
