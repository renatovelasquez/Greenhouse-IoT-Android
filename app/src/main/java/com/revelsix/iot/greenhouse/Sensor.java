package com.revelsix.iot.greenhouse;

/**
 * Created by renato on 6/21/16.
 */
public class Sensor {

    private int idSensor;
    private String sensor;
    private String value;

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
