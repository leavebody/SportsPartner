package com.sportspartner.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.sportspartner.R;
import com.sportspartner.models.FacilityMarker;
import com.sportspartner.models.MapApiResult;
import com.sportspartner.models.SActivityOutline;
import com.sportspartner.service.ResourceService;
import com.sportspartner.service.serviceresult.ModelResult;
import com.sportspartner.util.ActivityCallBack;
import com.sportspartner.util.PickPlaceResult;
import com.sportspartner.util.adapter.AddressesListViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * An activity that displays a map showing the place at the device's current location.
 */
public class MapActivity extends BasicActivity
        implements OnMapReadyCallback,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener {

    private static final String TAG = MapActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private ArrayList<FacilityMarker> facilityMarkers;
    private Marker mOnclickMarker;
    private BottomSheetDialog dialog;


    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location mLastLocation;
    private FusedLocationProviderApi fusedLocationProviderApi;

    private PickPlaceResult pickPlaceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        Bundle b = getIntent().getExtras();

        if (b != null) {
            pickPlaceResult = (PickPlaceResult) getIntent().getExtras()
                    .getSerializable("PickPlaceResult");
        } else {
            pickPlaceResult = new PickPlaceResult();
        }

        ViewGroup content = findViewById(R.id.layout_home);
        getLayoutInflater().inflate(R.layout.activity_map, content, true);

        dialog = new BottomSheetDialog(MapActivity.this);
        dialog.setContentView(R.layout.map_marker_popup);

        //set title of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pick a place");


        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onLocationChanged(Location location) {
        // TODO delete this when I'm sure this works
        Toast.makeText(this, "location :" + location.getLatitude() + " , " + location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        getMarkers();
        setMapMarkers(mMap);
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }
    public void onMapClick (LatLng point){
        if (mOnclickMarker != null) {
            mOnclickMarker.setPosition(point);
            mOnclickMarker.setTag(new FacilityMarker(point, null, null));
        } else {
            MarkerOptions marker = new MarkerOptions()
                    .position(point);
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mOnclickMarker = mMap.addMarker(marker);
            mOnclickMarker.setTag(new FacilityMarker(point, null, null));
        }
        onMarkerClick(mOnclickMarker);
    }

    public void setMapMarkers(GoogleMap map){
        for(FacilityMarker f: facilityMarkers) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(f.getLatitude(), f.getLongtitude())))
                    .setTag(f);
        }
    }

    public void getMarkers(){
        facilityMarkers = new ArrayList();
        facilityMarkers.add(new FacilityMarker(39.328, -76.617, "swimming", "id007"));
        facilityMarkers.add(new FacilityMarker(39.325, -76.611, "badminton", "id015"));
        facilityMarkers.add(new FacilityMarker(39.329, -76.617, "running", "id250"));
        facilityMarkers.add(new FacilityMarker(39.330, -76.613, "sleeping", "id002"));
        facilityMarkers.add(new FacilityMarker(39.323, -76.617, "basketball", "id010"));
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        FacilityMarker facilityMarker = (FacilityMarker) marker.getTag();
        if (facilityMarker.getId()!=null){
            return onFacilityMarkerClick(facilityMarker);
        } else {
            return onNewMarkerClick(facilityMarker);
        }
    }

    public boolean onFacilityMarkerClick(final FacilityMarker facilityMarker){

        TextView facilityName = dialog.findViewById(R.id.facility_name);
        Button createButton = dialog.findViewById(R.id.use_this);

        facilityName.setText(facilityMarker.getName());
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickPlaceResult result = new PickPlaceResult();
                result.setLatLng(facilityMarker.getLatLng());
                result.setName(facilityMarker.getName());
                result.setFacility(true);
                sendResultBack(result);
            }
        });
        dialog.show();
        return false;
    }

    public boolean onNewMarkerClick(final FacilityMarker facilityMarker){
        pickPlaceResult.setLatLng(facilityMarker.getLatLng());
        pickPlaceResult.setFacility(false);

        LayoutInflater inflater = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View customView = inflater.inflate(R.layout.dialog_edit_address_on_map, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(customView);
        AlertDialog alertDialog = builder.create();

        final ListView addressesList = customView.findViewById(R.id.addresses);
        final EditText addressText = customView.findViewById(R.id.address_field);
        if (pickPlaceResult!= null && pickPlaceResult.getName()!= null){
            addressText.setText(pickPlaceResult.getName());
        }
        Button useThisBtn = customView.findViewById(R.id.use_this_address);

        useThisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPlaceResult.setName(addressText.getText().toString());
                sendResultBack(pickPlaceResult);
            }
        });
//        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
//        wmlp.gravity = Gravity.TOP;
//        wmlp.y = 100;
//        alertDialog.getWindow().setAttributes(wmlp);
        alertDialog.show();


        ResourceService.getGeocoding(this, facilityMarker.getLatLng(), new ActivityCallBack<MapApiResult>(){
            @Override
            public void getModelOnSuccess(ModelResult<MapApiResult> modelResult) {
                if (!modelResult.isStatus()){
                    Toast.makeText(MapActivity.this, modelResult.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("MapActivity",
                            String.format("Geo location api filed. Latlng:%s, message:%s",
                                    facilityMarker.getLatLng(),
                                    modelResult.getMessage()));
                    return;
                }
                pickPlaceResult.setZipCode(modelResult.getModel().getZipcode());
                ArrayList<String> addresses = modelResult.getModel().getAddresses();
                final AddressesListViewAdapter adapter = new AddressesListViewAdapter(MapActivity.this, addresses);
                addressesList.setAdapter(adapter);
                if (adapter.getCount()>4){
                    ViewGroup.LayoutParams params = addressesList.getLayoutParams();
                    DisplayMetrics displayMetrics = MapActivity.this.getResources().getDisplayMetrics();
                    params.height = Math.round(225 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    addressesList.setLayoutParams(params);
                    addressesList.requestLayout();
                }
                addressesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        addressText.setText(adapter.getText(position));
                    }

                });
            }
        });

        return false;
    }

    public void sendResultBack(PickPlaceResult result){
        Intent intent = new Intent();
        intent.putExtra("PickPlaceResult", result);
        setResult(Activity.RESULT_OK, intent);
        dialog.dismiss();
        finish();

    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     * Set up the marker of selected location.
     */

    private void getDeviceLocation() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //locationRequest.setInterval(1);
        //locationRequest.setFastestInterval(10);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle arg0) {
                        LatLng point;
                        try {
                            fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, MapActivity.this);
                            mLastLocation = fusedLocationProviderApi.getLastLocation(googleApiClient);
                            if (pickPlaceResult != null) {
                                point = pickPlaceResult.getLatLng();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        point, DEFAULT_ZOOM));
                                if (pickPlaceResult.isFacility()){
                                    //TODO open the card of this facility
                                } else {
                                    if (mOnclickMarker != null) {
                                        mOnclickMarker.setPosition(point);
                                    } else {
                                        MarkerOptions marker = new MarkerOptions()
                                                .position(point);
                                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                        mOnclickMarker = mMap.addMarker(marker);
                                        mOnclickMarker.setTag(new FacilityMarker(point.latitude, point.longitude, null, null));
                                    }
                                }
                            } else {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastLocation.getLatitude(),
                                                mLastLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } catch (SecurityException e){
                            Log.e(TAG, e.getMessage());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        } catch (Exception e) {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, e.getMessage());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                    @Override
                    public void onConnectionSuspended (int i){
                    }
                })
                //.addOnConnectionFailedListener(this)
                .build();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }


}