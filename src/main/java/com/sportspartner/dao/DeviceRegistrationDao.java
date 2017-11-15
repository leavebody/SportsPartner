package com.sportspartner.dao;

import com.sportspartner.model.DeviceRegistration;

import java.util.List;

public interface DeviceRegistrationDao {
    public List<String> getAllDeviceRegistrations(String userId);
    public boolean hasDeviceRegistration(DeviceRegistration deviceRegistration);
    public boolean newDeviceRegistration(DeviceRegistration deviceRegistration);
    public boolean deleteDeviceRegistration(DeviceRegistration deviceRegistration);
}
