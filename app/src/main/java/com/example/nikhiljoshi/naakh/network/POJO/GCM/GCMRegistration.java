package com.example.nikhiljoshi.naakh.network.POJO.GCM;

/**
 * Created by nikhiljoshi on 3/18/16.
 */
public class GCMRegistration {

    private String registration_id;
    private String device_uuid;

    public String getRegistrationId() {
        return registration_id;
    }

    public void setRegistrationId(String registration_id) {
        this.registration_id = registration_id;
    }

    public String getDeviceUuid() {
        return device_uuid;
    }

    public void setDeviceUuid(String device_uuid) {
        this.device_uuid = device_uuid;
    }
}
