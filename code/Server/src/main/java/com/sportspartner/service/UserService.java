package com.sportspartner.service;

import java.util.UUID;
import com.google.gson.Gson;
import com.sportspartner.util.JsonResponse;

import com.sportspartner.model.Person;
import com.sportspartner.dao.impl.PersonDaoImpl;

import com.sportspartner.model.User;
import com.sportspartner.dao.impl.UserDaoImpl;

import com.sportspartner.modelvo.LoginVO;
import com.sportspartner.modelvo.SignupVO;

public class UserService {

    private UserDaoImpl userDaoImpl = new UserDaoImpl();
    private PersonDaoImpl personDaoImpl = new PersonDaoImpl();

    public JsonResponse login(String body) throws UserServiceException {

        JsonResponse resp = new JsonResponse();

        try {
            LoginVO loginVO = new Gson().fromJson(body, LoginVO.class);
            User user = userDaoImpl.getUser(loginVO.getUserId());
            if (loginVO.isMissingField()) {
                resp.setResponse("false");
                resp.setMessage("Empty Input");
            } else if (user==null) {
                resp.setResponse("false");
                resp.setMessage("No such user");
            } else if (!loginVO.isCorrectPassword(user)) {
                resp.setResponse("false");
                resp.setMessage("Password is incorrect");
            } else {
                String key = UUID.randomUUID().toString();
                resp.setKey(key);
                resp.setResponse("true");
            }
        } catch(Exception ex){
            throw new UserServiceException("Json format error", ex);
        }
//      System.out.println(resp);
        return resp;
    }

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
                resp.setResponse("true");
                user = signupVO.cast2User();
                userDaoImpl.newUser(user);
                if(signupVO.getType().equals("PERSON")){
                    Person person = signupVO.cast2Person();
                    personDaoImpl.newPerson(person);
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


    public void signOut(){
        //TODO
    }
    public static class UserServiceException extends Exception {
        public UserServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}