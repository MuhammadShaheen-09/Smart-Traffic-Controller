package traffic.controller;

public class EmergencyTrafficController {

    public enum Road { A, B }

    private boolean emergencyA = false;
    private boolean emergencyB = false;

    // 🔥 YE METHOD MISSING THA
    public void addEmergency(Road road) {
        if (road == Road.A) {
            emergencyA = true;
        } else if (road == Road.B) {
            emergencyB = true;
        }
    }

    public boolean hasEmergency() {
        return emergencyA || emergencyB;
    }

    // A always has priority
    public Road getNextEmergencyRoad() {
        if (emergencyA) return Road.A;
        if (emergencyB) return Road.B;
        return null;
    }

    public void clearEmergency(Road road) {
        if (road == Road.A) emergencyA = false;
        if (road == Road.B) emergencyB = false;
    }
}
