package helper;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyLocations extends AsyncTask <Object, String, String>
{

    private String googleplaceData, url;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects)
    {
        mMap = (GoogleMap)objects[0];
        url = (String) objects [1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleplaceData = downloadUrl.ReadTheURL(url);
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        return googleplaceData;
    }

    @Override
    protected void onPostExecute(String s)
    {
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList = dataParser.parse(s);

        DisplayNearbyPlaces(nearbyPlacesList);
    }

    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList)
    {
        for (int i=0; i<nearbyPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> goofleNearbyPlace = nearbyPlacesList.get(i);
            String nameOfPlace = goofleNearbyPlace.get("place_name");
            String vicinity = goofleNearbyPlace.get("vicinity");
            double lat = Double.parseDouble(goofleNearbyPlace.get("lat"));
            double lng = Double.parseDouble(goofleNearbyPlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions);
            //camre movement
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }

        }
    }

