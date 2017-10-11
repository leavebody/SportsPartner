package com.sportspartner.Service;

import com.google.gson.Gson;
import com.sportspartner.Models.FacilityProvider;
import com.sportspartner.Models.Person;
import com.sportspartner.Models.User;

public class UserService {
    public User login(String body) throws Exception {
        //TODO
        try {
            User user = new Gson().fromJson(body, User.class);
            if(user.getUserName().equals(null) || user.getPassword().equals(null)){
                throw new Exception();
            }
            return user;
        } catch(Exception ex){
            throw new Exception(ex);
        }
    }

    public void signUpPerson(String body) throws Exception{
        //TODO
        try {
            Person person = new Gson().fromJson(body, Person.class);
            if(person.getUserName().equals(null) || person.getPassword().equals(null)
                    || person.getEmail().equals(null)){
                throw new Exception();
            }
        } catch(Exception ex){
            throw new Exception(ex);
        }
    }

    public void signUpActivityProvider(String body) throws Exception{
        //TODO
        try {
            FacilityProvider provider = new Gson().fromJson(body, FacilityProvider.class);
            if(provider.getUserName().equals(null) || provider.getPassword().equals(null)
                    || provider.getEmail().equals(null)){
                throw new Exception();
            }
        } catch(Exception ex){
            throw new Exception(ex);
        }
    }

    public void signOut(){
        //TODO
    }
}
