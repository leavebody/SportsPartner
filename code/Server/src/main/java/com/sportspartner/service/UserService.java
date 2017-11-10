package com.sportspartner.service;

import java.util.UUID;
import com.google.gson.Gson;
import com.sportspartner.dao.impl.*;
import com.sportspartner.model.*;
import com.sportspartner.util.JsonResponse;

import com.sportspartner.modelvo.LoginVO;
import com.sportspartner.modelvo.SignupVO;

public class UserService {

    private UserDaoImpl userDaoImpl = new UserDaoImpl();
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    private AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
    private DeviceRegistrationDaoImpl deviceRegistrationDaoImpl = new DeviceRegistrationDaoImpl();
    private IconDaoImpl iconDaoImpl = new IconDaoImpl();

    /**
     *  Login
     * @param body String that contains the information of userId and password
     * @return JsonResponse to the front-end
     * @throws UserServiceException throws UserServiceException
     */
    public JsonResponse login(String body) throws UserServiceException {

        JsonResponse resp = new JsonResponse();

        try {
            LoginVO loginVO = new Gson().fromJson(body, LoginVO.class);
            User user = userDaoImpl.getUser(loginVO.getUserId());

            if (loginVO.isMissingField()) {
                resp.setResponse("false");
                resp.setMessage("Empty Input");
            } else if (user == null) {
                resp.setResponse("false");
                resp.setMessage("No such user");
            } else if (!loginVO.isCorrectPassword(user)) {
                resp.setResponse("false");
                resp.setMessage("Password is incorrect");
            } else {
                String key = UUID.randomUUID().toString();
                Authorization authorization = new Authorization(loginVO.getUserId(), key);
                DeviceRegistration deviceRegistration = new DeviceRegistration(loginVO.getUserId(), loginVO.getRegistrationId());
                if (deviceRegistrationDaoImpl.hasDeviceRegistration(deviceRegistration)) {
                    if (authorizationDaoImpl.newAuthorization(authorization)) {
                        resp.setKey(key);
                        resp.setResponse("true");
                    } else {
                        resp.setResponse("false");
                        resp.setMessage("Fail to create new authorization");
                    }
                }
                else{
                    if (authorizationDaoImpl.newAuthorization(authorization)&&deviceRegistrationDaoImpl.newDeviceRegistration(deviceRegistration)){
                        resp.setKey(key);
                        resp.setResponse("true");
                    }
                    else{
                        resp.setResponse("false");
                        resp.setMessage("Fail to create new authorization");
                    }
                }
            }
        }
        catch(Exception ex){
            throw new UserServiceException("Json format error", ex);
        }
        return resp;
    }

    /**
     *
     * @param body body String that contains the information of userId and password, password confirm
     * @param type The type of the request. person represents the personal user and facilityprovider represents the facility provider.
     * @return JsonResponse to the front-end
     * @throws UserServiceException throws UserServiceException
     */
    public JsonResponse signup(String body, String type) throws UserServiceException{
        JsonResponse resp = new JsonResponse();
        try {
            SignupVO signupVO = new Gson().fromJson(body, SignupVO.class);
            User user = userDaoImpl.getUser(signupVO.getUserId());
            signupVO.setType(type);
            if(signupVO.isMissingField()){
                resp.setResponse("false");
                resp.setMessage("Empty input");
            }
            else if(!signupVO.isSamePassword()) {
                resp.setResponse("false");
                resp.setMessage("Password and confirm password are not consistent");
            }
            else if(user!=null){
                resp.setResponse("false");
                resp.setMessage("The email address has been taken");
            }
            else if(!signupVO.isValidType()){
                resp.setResponse("false");
                resp.setMessage("User type is invalid");
            }
            else{

                user = signupVO.cast2User();
                userDaoImpl.newUser(user);
                if(signupVO.getType().equals("PERSON")){
                    Person person = signupVO.cast2Person();
                    Icon icon  =  new Icon(user.getUserId(),"USER");
                    if(!(personDaoImpl.newPerson(person) && iconDaoImpl.newIcon(icon))){
                        resp.setResponse("false");
                        resp.setMessage("Cannot create items in the database");
                    }
                    else{
                        resp.setResponse("true");
                    }
                }
                else if(signupVO.getType().equals("PROVIDER")) {
                    //TODO
                }
            }
        } catch(Exception ex){
            throw new UserServiceException("Json format error", ex);
        }
        return resp;
    }

    /**
     * Log out by deleting an item of Authorization table, which keeps the login status of a user.
     * @param userId The email of the user who is logging out
     * @param key The login key of this session
     * @return JsonRespnonse JsonRespnonse to the front-end
     * @throws UserServiceException throws UserServiceException
     */
    public JsonResponse logOut(String userId, String key, String registrationId) throws UserServiceException{
        JsonResponse resp = new JsonResponse();
        Authorization authorization = new Authorization(userId, key);
        DeviceRegistration deviceRegistration = new DeviceRegistration(userId,registrationId);
        if(authorizationDaoImpl.deleteAuthorization(authorization) && deviceRegistrationDaoImpl.deleteDeviceRegistration(deviceRegistration)){
            resp.setResponse("true");
        }else{
            resp.setResponse("false");
            resp.setMessage("Fail to delete authorization.");
        }
        return resp;
    }
    /**
     *  Exception class for UserService
     */
    public static class UserServiceException extends Exception {
        public UserServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
