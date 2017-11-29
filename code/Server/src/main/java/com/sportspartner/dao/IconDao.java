package com.sportspartner.dao;

import com.sportspartner.model.Icon;

import java.sql.SQLException;

public interface IconDao {
    public Icon getIcon(String iconUUID) throws SQLException;
    public boolean updateIcon(Icon icon) throws SQLException;
    public boolean newIcon(Icon icon) throws SQLException;
    public boolean deleteIcon(String spId) throws SQLException;
}
