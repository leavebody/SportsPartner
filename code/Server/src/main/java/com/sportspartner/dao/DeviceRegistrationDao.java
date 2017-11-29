package com.sportspartner.dao;

import com.sportspartner.model.DeviceRegistration;

import java.sql.SQLException;
import java.util.List;

public interface DeviceRegistrationDao {
    public List<String> getAllDeviceRegistrations(String userId) throws SQLException;
    public boolean hasDeviceRegistration(DeviceRegistration deviceRegistration) throws SQLException;
    public boolean newDeviceRegistration(DeviceRegistration deviceRegistration) throws SQLException;
    public boolean deleteDeviceRegistration(DeviceRegistration deviceRegistration) throws SQLException;
}
