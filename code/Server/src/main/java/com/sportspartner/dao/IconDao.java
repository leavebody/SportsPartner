package com.sportspartner.dao;

import com.sportspartner.model.Icon;

public interface IconDao {
    public Icon getIcon(String iconUUID);
    public boolean newIcon(Icon icon);
}