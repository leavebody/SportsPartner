package com.sportspartner.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sportspartner.R;
import com.sportspartner.models.MapApiResult;
import com.sportspartner.models.Sport;
import com.sportspartner.request.ResourceRequest;
import com.sportspartner.util.BitmapHelper;
import com.sportspartner.util.GeocodeRaw;
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
    public final static String IMAGE_SMALL = "small";
    public final static String IMAGE_ORIGIN = "origin";

    /**
     * Get an image.
     *
     * @param c        Caller context
     * @param uuid     The uuid of the image.
     * @param callback
     * @param type     The type of the requested image. 'small' for the small version of the icon, 'origin' for the original icon image.
     */
    public static void getImage(final Context c, final String uuid, final String type, final ActivityCallBack callback) {
        if (!type.equals(IMAGE_ORIGIN) && !type.equals(IMAGE_SMALL)) {
            ModelResult<Bitmap> result = new ModelResult<>();
            result.setStatus(false);
            result.setMessage("type is not valid");
            return;
        }

        // find image in cache by uuid
        File cacheDir = c.getApplicationContext().getCacheDir();
        String cacheDirPath = cacheDir.toString();
        String imagePath = cacheDirPath + "/" + uuid + "-" + type + ".image";
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            try {
                FileInputStream fos = new FileInputStream(imageFile);
                String imageString = IOUtils.toString(fos);
                fos.close();
                Bitmap bmp = BitmapHelper.StringToBitMap(imageString);

                // return the Bitmap to caller
                ModelResult<Bitmap> result = new ModelResult<>();
                result.setStatus(true);
                result.setModel(bmp);
                callback.getModelOnSuccess(result);
                return;
            } catch (java.io.IOException e) {
                // TODO
            }
        }
        // find it on server
        ResourceRequest request = new ResourceRequest(c);
        request.imageRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getImageRespProcess(response, c, uuid, type));
            }
        }, uuid, type);

    }

    /**
     * The helper method to process the result of get image request.
     *
     * @param response The network response to process
     * @return A ModelResult with model type Bitmap, which is the bitmap of the image
     */
    private static ModelResult<Bitmap> getImageRespProcess(NetworkResponse response, Context c, String uuid, String type) {
        ModelResult<Bitmap> result = new ModelResult<>();
        switch (response.statusCode) {
            case 200:
                boolean status;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if (status) {

                    String bmpString = jsResp.get("image").getAsString();
                    result.setModel(BitmapHelper.StringToBitMap(bmpString));

                    // store the image in cache
                    File cacheDir = c.getApplicationContext().getCacheDir();
                    String cacheDirPath = cacheDir.toString();
                    String imagePath = cacheDirPath + "/" + uuid + "-" + type + ".image";
                    File imageFile = new File(imagePath);
                    try {
                        if (!imageFile.exists()) {
                            imageFile.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(imageFile);

                        fos.write(bmpString.getBytes());
                        fos.close();
                    } catch (java.io.IOException e) {
                        // TODO
                    }
                } else {
                    result.setMessage("get image failed: " + jsResp.get("message").getAsString());
                }
                break;

            default:
                result.setMessage("bad response:" + response.statusCode);
        }
        return result;
    }


    public static void uploadUserIcon(final Context c, final Bitmap bitmap, final ActivityCallBack callback) {
        final String bmpString = BitmapHelper.BitMapToString(bitmap);
        ResourceRequest request = new ResourceRequest(c);
        request.imageUploadRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(uploadUserIconRespProcess(response, c, bmpString));
            }
        }, bmpString);
    }

    private static ModelResult uploadUserIconRespProcess(NetworkResponse response, Context c, String bmpString) {
        ModelResult<String> result = new ModelResult<>();
        switch (response.statusCode) {
            case 200:
                boolean status;

                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if (status) {
                    String uuid = jsResp.get("iconUUID").getAsString();
                    result.setModel(uuid);
                    // store the image in cache
                    File cacheDir = c.getApplicationContext().getCacheDir();
                    String cacheDirPath = cacheDir.toString();
                    String imagePath = cacheDirPath + "/" + uuid + "-" + IMAGE_ORIGIN + ".image";
                    File imageFile = new File(imagePath);
                    try {
                        if (!imageFile.exists()) {
                            imageFile.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(imageFile);

                        fos.write(bmpString.getBytes());
                        fos.close();
                    } catch (java.io.IOException e) {
                        // TODO
                    }
                } else {
                    result.setMessage("upload image failed: " + jsResp.get("message").getAsString());
                }
                break;

            default:
                result.setMessage("bad response:" + response.statusCode);
        }
        return result;
    }

    /**
     * Get an ArrayList of all sports in the APP.
     *
     * @param c        Caller context.
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
     *
     * @param response The network response to process
     * @return A ModelResult with model type ArrayList<Sport>,
     * which is the requested sports data.
     */
    private static ModelResult<ArrayList<Sport>> getAllSportsRespProcess(NetworkResponse response) {
        ModelResult<ArrayList<Sport>> result = new ModelResult();
        switch (response.statusCode) {
            case 200:
                boolean status;
                JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
                status = (jsResp.get("response").getAsString().equals("true"));
                result.setStatus(status);
                if (status) {
                    Gson gson = new Gson();
                    JsonArray jsArraySport = jsResp.getAsJsonArray("sports");
                    ArrayList<Sport> arraySport = gson.fromJson(jsArraySport,
                            new TypeToken<ArrayList<Sport>>() {
                            }.getType());

                    result.setModel(arraySport);
                } else {
                    result.setMessage("get all sports request failed: " + jsResp.get("message").getAsString());
                }
                break;
            default:
                result.setMessage("bad response:" + response.statusCode);
        }
        return result;
    }

    /**
     * Get the addresses and zipcode by latLng.
     *
     * @param c        Caller context.
     * @param callback
     */
    public static void getGeocoding(final Context c, LatLng latLng, final ActivityCallBack callback) {
        String key = c.getResources().getString(R.string.google_maps_web_key);
        ResourceRequest request = new ResourceRequest(c);
        request.geoCodingRequest(new VolleyCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                callback.getModelOnSuccess(getGeocodingRespProcess(response));
            }
        }, latLng, key);

    }

    /**
     * The helper method to process the result of get all sports request.
     *
     * @param response The network response to process
     * @return A ModelResult with model type ArrayList<Sport>,
     * which is the requested sports data.
     */
    private static ModelResult<MapApiResult> getGeocodingRespProcess(NetworkResponse response) {
        ModelResult<MapApiResult> result = new ModelResult();
        MapApiResult apiResult = new MapApiResult();

        JsonObject jsResp = NetworkResponseRequest.parseToJsonObject(response);
        Gson gson = new Gson();
        GeocodeRaw geocodeRaw = gson.fromJson(jsResp, GeocodeRaw.class);

        if (geocodeRaw.status.equals("OK")) {
            result.setStatus(true);
            String zipcode = null;
            Boolean hasZipcode = false;
            ArrayList<String> addresses = new ArrayList<>();

            for (GeocodeRaw.Result result1 : geocodeRaw.results) {
                addresses.add(result1.formatted_address);
                if (!hasZipcode) {
                    for (GeocodeRaw.Result.Address_component component : result1.address_components) {
                        if (component.types.size() == 1 && component.types.get(0).equals("postal_code")) {
                            zipcode = component.long_name;
                            hasZipcode = true;
                            break;
                        }
                    }
                }
            }
            apiResult.setZipcode(zipcode);
            apiResult.setAddresses(addresses);
            result.setModel(apiResult);

        } else {
            result.setStatus(false);
            Log.d("ResourceService", "geocoding: " + geocodeRaw.status);
            result.setMessage(geocodeRaw.status);
        }
        return result;
    }

    /**
     * Clear the cache.
     *
     * @param c Caller context.
     * @return A boolean indicating whether the clearance is successful.
     */
    public static boolean clearCache(Context c) {
        try {
            File[] files = c.getApplicationContext().getCacheDir().listFiles();
            Long totalLengthByte = 0L;
            Long singleLengthByte;
            for (File file : files) {
                // delete returns boolean we can use
                singleLengthByte = file.length();
                if (file.delete()) {
                    totalLengthByte += singleLengthByte;
                }
            }
            // if for completes all
            Toast.makeText(c, String.format("Cache cleared successfully! %s kB memory cleared", totalLengthByte.doubleValue()/1000)
                    , Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception e) {
        }
        // try stops clearing cache
        return false;
    }
}
