package com.sportspartner.util;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.DeviceRegistrationDaoImpl;

import java.io.IOException;
import java.util.Set;
import java.util.List;

public class GCMHelper {
    private final static String senderKey = "AIzaSyD6mj4I5YTNU-copAr7HY_LZ7Rwz_jcK4U";
    private DeviceRegistrationDaoImpl deviceRegistrationDaoImpl =  new DeviceRegistrationDaoImpl();


    public  boolean SendGCMData(String userId, String title, String content)  throws IOException {

        Sender sender = new Sender(senderKey);
        Message.Builder builder = new Message.Builder();
        if (title == null ||content==null) return false;
        builder.addData("title",title);
        builder.addData("content",content);
        Message message = builder.build();
        List<String> devices = deviceRegistrationDaoImpl.getAllDeviceRegistrations(userId);
        MulticastResult multicastResult = sender.send(message,devices, 5);
        return (multicastResult.getSuccess() > 0);

    }
    public  boolean SendGCMDataGeneral(String userId, JsonObject datainput)  throws IOException {

        Sender sender = new Sender(senderKey);
        Message.Builder builder = new Message.Builder();
        if (datainput == null) return false;
        Set<String> keySet = datainput.keySet();
        for (String key : keySet) {
            String value = datainput.get(key).getAsString();
            builder.addData(key, value);
        }
        Message message = builder.build();
        List<String> devices = deviceRegistrationDaoImpl.getAllDeviceRegistrations(userId);
        MulticastResult multicastResult = sender.send(message,devices, 5);
        return (multicastResult.getSuccess() > 0);

    }
}
