package com.sportspartner.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sportspartner.dao.impl.AuthorizationDaoImpl;
import com.sportspartner.dao.impl.IconDaoImpl;
import com.sportspartner.model.Authorization;
import com.sportspartner.model.Icon;
import com.sportspartner.util.ImageUtil;
import com.sportspartner.util.JsonResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

public class ImageService {
    private IconDaoImpl iconDaoImpl = new IconDaoImpl();
    private ImageUtil imageUtil = new ImageUtil();

    public JsonResponse getImage(String iconUUID, String type) throws Exception{

        JsonResponse resp = new JsonResponse();
        Icon icon = iconDaoImpl.getIcon(iconUUID);
        if(icon==null){
            resp.setResponse("false");
            resp.setMessage("no such icon");
            return resp;
        }
        String imagePath = "";
        if(type.equals("small")){
            imagePath = icon.getSmallPath();
        }
        else if(type.equals("origin")){
            imagePath = icon.getOriginPath();
        }
        else{
            resp.setResponse("false");
            resp.setMessage("wrong type");
            return resp;
        }

        try {
            BufferedImage image = imageUtil.getImage(imagePath);
            String base64String = imageUtil.imageToBase64(image);
            resp.setImage(base64String);
            resp.setResponse("true");
        }catch(IOException ex){
            resp.setResponse("false");
            resp.setMessage("image processing error");
            ex.printStackTrace();
        }
        return resp;
    }

    public JsonResponse updateIcon(String spId, String body) throws Exception{
        JsonResponse resp = new JsonResponse();

        JsonObject json = new Gson().fromJson(body, JsonObject.class);

        String userId = json.get("userId").getAsString();
        String key = json.get("key").getAsString();
        // Check
        if(!isAuthorized(userId, key)) {
            resp.setResponse("false");
            resp.setMessage("Lack authorization to upload an icon");
            return resp;
        }
        String object = json.get("object").getAsString();
        //String base64String = imageUtil.imageToBase64(imageUtil.getImage(json.get("image").getAsString()));
        String base64String = json.get("image").getAsString();
        // new database item
        String iconUUID = UUID.randomUUID().toString();
        String smallPath =  imageUtil.getImagePath(spId, object, "small");
        String originPath = imageUtil.getImagePath(spId, object, "origin");
        if(!iconDaoImpl.updateIcon(new Icon(spId, iconUUID, smallPath, originPath, object))){
            resp.setResponse("false");
            resp.setMessage("fail to create a new icon item in database");
            return resp;
        }

        // new image files in server
        BufferedImage image = imageUtil.base64ToImage(base64String);
        BufferedImage smallImage = imageUtil.resizeImage(image);
        if(!imageUtil.saveImage(smallImage, smallPath) || !imageUtil.saveImage(image, originPath)){
            resp.setResponse("false");
            resp.setMessage("fail to save images in server");
        }else{
            resp.setResponse("true");
            resp.setIconUUID(iconUUID);
        }
        return resp;
    }
    /**
     * Check whether a user is authorized
     * @param userId Id of a user
     * @param key login key of a user
     * @return true means the user is authorized,  false means the user isn't authorized
     */
    public boolean isAuthorized(String userId, String key){
        Authorization authorization = new Authorization(userId, key);
        AuthorizationDaoImpl authorizationDaoImpl = new AuthorizationDaoImpl();
        return authorizationDaoImpl.hasAuthorization(authorization);
    }
}
