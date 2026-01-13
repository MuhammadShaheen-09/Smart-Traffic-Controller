package traffic.model;

public class TrafficSignal
{
    private String state;

    public TrafficSignal(String state) {
        this.state = state;
    }

    public void changeSignal(String newState) {
        this.state = newState;
    }

    public String getState() {
        return state;
    }
}
