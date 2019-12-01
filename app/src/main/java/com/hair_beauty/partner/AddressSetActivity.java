package com.hair_beauty.partner;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.hair_beauty.partner.utility.ServerLinks;
import com.hair_beauty.partner.utility.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddressSetActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    TextView currentddress,edit,taptoset;
    CardView card;
    LinearLayout edit_layout;
    EditText city,locality,plotno,pin,state,landmark;
    private Location mylocation;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    int tap_to_tag =0;
    String getAddress,barber_id;
    Double latitude,longitude;
    SharedPreferenceClass sharedPreferenceClass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        sharedPreferenceClass = new SharedPreferenceClass(AddressSetActivity.this);

        currentddress = findViewById(R.id.currentddress);
        taptoset=findViewById(R.id.taptoset);
        barber_id=sharedPreferenceClass.getValue_string("BARBERID");
        Log.i("barber_id",barber_id);

//        edit = findViewById(R.id.edit);
//        edit_layout = findViewById(R.id.edit_layout);
//        card = findViewById(R.id.card);
//        city = findViewById(R.id.city);
//        locality = findViewById(R.id.locality);
//        plotno = findViewById(R.id.plotno);
//        pin = findViewById(R.id.pin);
//        state = findViewById(R.id.state);
//        landmark = findViewById(R.id.landmark);
//
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                card.setVisibility(View.GONE);
//                edit_layout.setVisibility(View.VISIBLE);
//            }
//        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setUpGClient();

        taptoset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tap_to_tag==1){
                    setLocation(getAddress,latitude,longitude);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Get Location to update",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private synchronized void setUpGClient() {

        try {

            if (googleApiClient == null || !googleApiClient.isConnected()) {
                googleApiClient = new GoogleApiClient.Builder(AddressSetActivity.this)
                        .enableAutoManage(AddressSetActivity.this, 0, this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                googleApiClient.connect();
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }



    @Override
    public void onLocationChanged(Location location) {

        mylocation = location;

        if (mylocation != null) {

             latitude = mylocation.getLatitude();
             longitude = mylocation.getLongitude();

            try {

                List<Address> addresses = null;
                Geocoder geocoder = new Geocoder(AddressSetActivity.this, Locale.getDefault());
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                String statee = addresses.get(0).getAdminArea();
                String districtt = addresses.get(0).getSubAdminArea();
                String Locality = addresses.get(0).getSubLocality();
                String cityy =addresses.get(0).getLocality();
                String getCountryCode = addresses.get(0).getCountryCode();
                String countryname = addresses.get(0).getCountryName();
                String pinn = addresses.get(0).getPostalCode();
                getAddress=address;
                currentddress.setText(address);
                tap_to_tag=1;

//                city.setText(cityy);
//                state.setText(statee);
//                pin.setText(pinn);
//                locality.setText(Locality);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else{
            tap_to_tag=0;
            currentddress.setText("No Location Available");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        this.finish();
                        break;
                }

                break;
        }
    }

    private void checkPermissions() {

        int permissionLocation = ContextCompat.checkSelfPermission(AddressSetActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(AddressSetActivity.this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }
    }
    private void getMyLocation() {

        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {

                int permissionLocation = ContextCompat.checkSelfPermission(AddressSetActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation =LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(2000);
                    locationRequest.setFastestInterval(2000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest,this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(AddressSetActivity.this,
                                                    android.Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(AddressSetActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(AddressSetActivity.this);
        googleApiClient.disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(AddressSetActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            checkPermissions();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setLocation(final String address, final Double latitude, final Double longitude) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLinks.SET_LOC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response_my_loc",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if(message.equals("Location set successfully")){
                        Toast.makeText(getApplicationContext(),"Location set successfully",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token <token>");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("emp_id",barber_id);
                params.put("lat",String.valueOf(latitude));
                params.put("long",String.valueOf(longitude));
                params.put("address",address);//B7982
                //  params.put("password",userPassword.getText().toString()); //dipak
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddressSetActivity.this);
        requestQueue.add(stringRequest);
    }
}
