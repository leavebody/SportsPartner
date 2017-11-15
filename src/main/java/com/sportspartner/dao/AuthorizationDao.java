package com.sportspartner.dao;
import com.sportspartner.model.Authorization;
import java.util.List;

public interface AuthorizationDao {
    public List<Authorization> getAllAuthorizations();
    public boolean hasAuthorization(Authorization authorization);
    public boolean newAuthorization(Authorization authorization);
    public boolean updateAuthorization(Authorization authorization, String newKey);
    public boolean deleteAuthorization(Authorization authorization);
}
