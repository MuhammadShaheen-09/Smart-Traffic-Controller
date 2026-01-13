package traffic.model;

public class VehicleQueue {

    private int count = 0;

    public void addVehicle() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public void clear() {
        count = 0;
    }
}
