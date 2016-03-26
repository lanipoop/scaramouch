package always;

import java.util.Map;

public class Measurements {
    private String type;
    private String size;
    private Map<String, String> measurements;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Map<String, String> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Map<String, String> measurements) {
        this.measurements = measurements;
    }
}
