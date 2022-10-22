public class Measurements {
    private int id;
    private Sensor sensor;
    private double value;
    private String measuredAt;
    private boolean raining;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(String measuredAt) {
        this.measuredAt = measuredAt;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    @Override
    public String toString() {
        return "Measurements{" +
                "id=" + id +
                ", sensor=" + sensor +
                ", value=" + value +
                ", measuredAt='" + measuredAt + '\'' +
                ", raining=" + raining +
                '}';
    }
}
