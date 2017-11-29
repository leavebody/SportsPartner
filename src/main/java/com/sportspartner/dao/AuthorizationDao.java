package com.sportspartner.dao;
import com.sportspartner.model.Authorization;

import java.sql.SQLException;
import java.util.List;

public interface AuthorizationDao {
    public List<Authorization> getAllAuthorizations() throws SQLException;
    public boolean hasAuthorization(Authorization authorization) throws SQLException;
    public boolean newAuthorization(Authorization authorization) throws SQLException;
    public boolean updateAuthorization(Authorization authorization, String newKey) throws SQLException;
    public boolean deleteAuthorization(Authorization authorization) throws SQLException;
}
