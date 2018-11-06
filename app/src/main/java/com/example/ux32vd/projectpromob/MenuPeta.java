package com.example.ux32vd.projectpromob;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import helper.GetNearbyLocations;

public class MenuPeta extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener

{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private double latitude, longitude;
    private int ProximityRadius = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_peta);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //method on click di Menu Peta
    public void onClick(View v)
    {
        String rumahsakit = "rumahsakit", sekolah = "sekolah", restoran = "restoran";
        Object transferData[] = new Object[2];
        GetNearbyLocations getNearbyLocations = new GetNearbyLocations();




        switch (v.getId())
        {
            case R.id.search_address:
                EditText addressfield = (EditText) findViewById(R.id.CariLokasi);
                String address = addressfield.getText().toString();

                //konfigurasi saat user mencari lokasi di search bar
                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(address))
                {
                    Geocoder geocoder = new Geocoder(this);
                    try
                    {
                        addressList = geocoder.getFromLocationName(address, 6);
                        if (addressList != null)
                        {
                            for (int i=0; i<addressList.size(); i++)
                            {

                                //lokasi yang dicari di user di mark
                                Address userAddress = addressList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title("Inilah Lokasi Yang Anda Cari!");
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                                mMap.addMarker(userMarkerOptions);
                                //camre movement
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                            }
                        }
                        else
                        {
                            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                        }



                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }



                }
                else
                {
                    Toast.makeText(this, "harap lengkapi pencarian anda...", Toast.LENGTH_SHORT).show();
                }
                break;

                //method on click pada setiap icon
            case R.id.rumahsakit:
                mMap.clear();
                String url = getUrl( latitude,  longitude, rumahsakit);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyLocations.execute(transferData);
                Toast.makeText(this, "Mencari rumah sakit terdekat...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Menampilkan rumah sakit terdekat...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.sekolah:
                mMap.clear();
                url = getUrl(latitude, longitude, sekolah);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyLocations.execute(transferData);
                Toast.makeText(this, "Mencari sekolah terdekat...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Menampilkan sekolah terdekat...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.restoran:
                mMap.clear();
                url = getUrl(latitude, longitude, restoran);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyLocations.execute(transferData);
                Toast.makeText(this, "Mencari restoran terdekat...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Menampilkan restoran terdekat...", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    //konfigurasi pencarian lokasi spesifik terdekat
    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyCAisLIs7AZjDriPF1ByDg5pGDF2MQAgqg");

        //keterangan error
        Log.d("MenuPeta" , "url = " + googleURL.toString());

        return googleURL.toString();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //memanggil fungsi API googlemap
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //hak akses lokasi
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
        }
    }

    public boolean checkUserLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},  Request_User_Location_Code);
            }

            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},  Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }



    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        lastLocation = location;

        if (currentUserLocationMarker != null)
        {
            currentUserLocationMarker.remove();
        }

        //konfigurasi marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Inilah Lokasi Anda!");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        currentUserLocationMarker = mMap.addMarker(markerOptions);

        //camre movement
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));

        //stop update lokasi
        if (googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiClient();;
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                //jika tidak dikasi permissiion
                else
                {
                    Toast.makeText(this, "Permission Gagal", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //mendeteksi objek bergerak
        locationRequest= new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        //mendeteksi objek bergerak
        locationRequest= new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
