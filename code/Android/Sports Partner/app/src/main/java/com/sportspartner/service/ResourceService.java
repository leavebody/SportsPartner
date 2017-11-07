package com.sportspartner.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.NetworkResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.models.Sport;
import com.sportspartner.request.ResourceRequest;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.util.BitmapHelper;
import com.sportspartner.util.NetworkResponseRequest;
import com.sportspartner.util.VolleyCallback;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class ResourceService extends Service {

    /**
     * Get an image.
     * @param c Caller context
     * @param uuid The uuid of the image.
     * @param path The path of the image on the server.
     * @param callback
     */
    public static void getImage(final Context c, final String uuid, final ActivityCallBack callback) {

        // find image in cache by uuid
        File cacheDir = c.getApplicationContext().getCacheDir();
        String cacheDirPath = cacheDir.toString();
        String imagePath = cacheDirPath+"/"+uuid+".image";
        File imageFile = new File(imagePath);
        if (imageFile.exists()){
            try {
                FileInputStream fos = new FileInputStream(imageFile);
                String imageString = IOUtils.toString(fos);
                fos.close();
                Bitmap bmp = BitmapHelper.StringToBitMap(imageString);

                // return the Bitmap to caller
                ModelResult<Bitmap> result = new ModelResult();
                result.setStatus(true);
                result.setModel(bmp);
                callback.getModelOnSuccess(result);
                return;
            }catch (java.io.IOException e){
                // TODO
            }
        }
        // find it on server
        ResourceRequest request = new ResourceRequest(c);
        request.imageRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getImageRespProcess(response, c, uuid));
            }
        });

    }

    /**
     * The helper method to process the result of get image request.
     * @param response The network response to process
     * @return A ModelResult with model type Bitmap, which is the bitmap of the image
     */
    private static ModelResult<Bitmap> getImageRespProcess(NetworkResponse response, Context c, String uuid){
        ModelResult<Bitmap> result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {

                    String bmpString = jsResp.get("image").getAsString();
                    result.setModel(BitmapHelper.StringToBitMap(bmpString));

                    // store the image in cache
                    File cacheDir = c.getApplicationContext().getCacheDir();
                    String cacheDirPath = cacheDir.toString();
                    String imagePath = cacheDirPath+"/"+uuid+".image";
                    File imageFile = new File(imagePath);
                    try {
                        if (!imageFile.exists()) {
                            imageFile.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(imageFile);

                        fos.write(bmpString.getBytes());
                        fos.close();
                    } catch (java.io.IOException e){
                        // TODO
                    }
                } else {
                    result.setMessage("get image failed: "+jsResp.get("message").getAsString());
                }
                break;

            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }

    /**
     * Get an ArrayList of all sports in the APP.
     * @param c Caller context.
     * @param callback
     */
    public static void getAllSports(final Context c, final ActivityCallBack callback) {

        ResourceRequest request = new ResourceRequest(c);
        request.allSportsRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getAllSportsRespProcess(response));
            }
        });

    }

    /**
     * The helper method to process the result of get all sports request.
     * @param response The network response to process
     * @return A ModelResult with model type ArrayList<Sport>,
     *          which is the requested sports data.
     */
    private static ModelResult<ArrayList<Sport>> getAllSportsRespProcess(NetworkResponse response){
        ModelResult<ArrayList<Sport>> result = new ModelResult();
        switch (response.statusCode){
            case 200:
                boolean status;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if(status) {
                    Gson gson = new Gson();
                    JsonArray jsArraySport = jsResp.getAsJsonArray("sports");
                    ArrayList<Sport> arraySport = gson.fromJson(jsArraySport,
                            new TypeToken<ArrayList<Sport>>(){}.getType());

                    result.setModel(arraySport);
                } else {
                    result.setMessage("get all sports request failed: "+jsResp.get("message").getAsString());
                }
                break;
            default:
                result.setMessage("bad response:"+response.statusCode);
        }
        return result;
    }


    /**
     * Clear the cache.
     * @param c Caller context.
     * @return A boolean indicating whether the clearance is successful.
     */
    public static boolean clearCache(Context c) {
        try {
            File[] files = c.getApplicationContext().getCacheDir().listFiles();
            for (File file : files) {
                // delete returns boolean we can use
                if (!file.delete()) {
                    return false;
                }
            }
            // if for completes all
            return true;
        } catch (Exception e) {}
        // try stops clearing cache
        return false;
    }
}
