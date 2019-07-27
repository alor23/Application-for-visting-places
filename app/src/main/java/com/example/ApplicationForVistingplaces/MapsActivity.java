package com.example.ApplicationForVistingplaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnInfoWindowCloseListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        LocationListener {

    private RelativeLayout places_view;
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private static final int Request_User_Location_Code = 99;
    private String jsonResult;

    public  ArrayList<Places> placesList = new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        places_view = findViewById(R.id.places_view);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
            mMap.setMapType(MAP_TYPE_HYBRID);
            mUiSettings.setZoomControlsEnabled(true);
            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            mMap.setOnInfoWindowCloseListener(this);
            mMap.setOnInfoWindowClickListener(this);
            accessWebService();
            LatLng latLng = new LatLng(50.26,19.02);
            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(latLng)
                    .bearing(4.3f)
                    .zoom(14f).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.animateCamera(cameraUpdate, 3000, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            if(requestCode == Request_User_Location_Code)
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                    }
                } else {
                    Toast.makeText(this, "Brak dostępu", Toast.LENGTH_SHORT).show();
                }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        lastLocation = location;

        for(int i = 0; i< placesList.size(); i++)
        {
            Location target = new Location("target");
            target.setLatitude(placesList.get(i).getLatitude());
            target.setLongitude(placesList.get(i).getLongitiude());
            if (lastLocation.distanceTo(target) < 100) {
                getpoints();
                int points = User.points;
                points +=50;
                String urlSuffix = "?username=" + User.userName + "&place_name=" + placesList.get(i).getTitle() + "&points=" + points;
                class checkifvisited extends AsyncTask<String, Void, String> {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        if(result.equals("Visited"))
                        {
                            Toast.makeText(getApplicationContext(),getString(R.string.visited_succes), Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("Already visited"))
                        {
                            Toast.makeText(getApplicationContext(),getString(R.string.visited_already), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    protected String doInBackground(String... params) {
                        String parameters = params[0];
                        BufferedReader bufferReader=null;
                        try {
                            URL url=new URL(Values.URL+"visitplace.php"+parameters);
                            HttpURLConnection con=(HttpURLConnection)url.openConnection();
                            bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String result;
                            result=bufferReader.readLine();
                            return  result;

                        }catch (Exception e){
                            return null;
                        }
                    }
                }
                checkifvisited ur=new checkifvisited();
                ur.execute(urlSuffix);
            }
        }

        if (lastLocation != null) {
            LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(latLng)
                    .bearing(4.3f)
                    .zoom(17.5f).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

            mMap.animateCamera(cameraUpdate, 3000, null);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setSmallestDisplacement(50);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void addMarkersToMap() {
    for(int i = 0; i< placesList.size(); i++)
    {
        LatLng latLng = new LatLng(placesList.get(i).getLatitude(),placesList.get(i).getLongitiude());

        markers.add(mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(placesList.get(i).getTitle())
                .snippet(placesList.get(i).getDescription())));
    }

    }

    @Override
    public void onInfoWindowClose(Marker marker) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String url = "";
        for(int i =0;i<placesList.size();i++)
        {
            if(marker.getTitle().equals(markers.get(i).getTitle()))
            {
                url=placesList.get(i).getUrl();
            }
        }
        RedirectUsingCustomTab(url);
    }

    public void Logout(View v) {
        User.userName = "";
        User.points = 0;
        Intent intent = new Intent(MapsActivity.this, Login_window.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void Ranking(View v) {
        Intent intent = new Intent(MapsActivity.this, Ranking_window.class);
        startActivity(intent);
    }

    private void RedirectUsingCustomTab(String url) {
        Uri uri = Uri.parse(url);

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        intentBuilder.setStartAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = intentBuilder.build();

        customTabsIntent.launchUrl(this, uri);
    }

    public void Back(View v)
    {

        if(places_view.getVisibility() == View.GONE)
        {
            places_view.setVisibility(View.VISIBLE);
        }
        else
        {
            places_view.setVisibility(View.GONE);
        }
    }

    public void Show_on_map(View v)
    {
        for(int i =0;i<placesList.size();i++)
        {
            if(v.getTag().equals(placesList.get(i).getTitle()))
            {
                LatLng latLng = new LatLng(placesList.get(i).getLatitude(),placesList.get(i).getLongitiude());
                CameraPosition cameraPosition = CameraPosition.builder()
                        .target(latLng)
                        .bearing(4.3f)
                        .zoom(18).build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cameraUpdate, 3000, null);
                places_view.setVisibility(View.GONE);
            }
        }
    }
    private class MarkerCallback implements Callback {
        Marker marker=null;

        MarkerCallback(Marker marker) {
            this.marker=marker;
        }

        @Override
        public void onSuccess() {
            if (marker != null && marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
                marker.showInfoWindow();
            }
        }

        @Override
        public void onError(Exception e) {

        }
    }
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View mContents;
        CustomInfoWindowAdapter() {
            mContents = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }

        private void render(Marker marker, View view) {
            ImageView image = view.findViewById(R.id.photo);
            String url = Values.URL+"Photos/";
            Display display = getWindowManager(). getDefaultDisplay();
            Point size = new Point();
            display. getSize(size);
            for(int i =0;i<markers.size();i++) {
                if (marker.getTitle().equals(markers.get(i).getTitle())) {
                    url +=markers.get(i).getTitle() + ".jpg";
                    Picasso.get().load(url).resize(size.x/3,size.y/5)
                            .into(image ,new MarkerCallback(marker));
                }
            }
            String title = marker.getTitle();
            TextView titleUi = view.findViewById(R.id.name);
            titleUi.setText(title);

            String snippet = marker.getSnippet();
            TextView snippetUi = view.findViewById(R.id.description);
            snippetUi.setText(snippet);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }
    }

    public void accessWebService(){
        JsonReadTask task = new JsonReadTask();
        task.execute(Values.URL+"getlocations.php");
    }

    public void Addplacestolist (){
        try{
            JSONObject jsonResonse = new JSONObject(jsonResult.substring(jsonResult.indexOf("{"), jsonResult.lastIndexOf("}")+1));
            JSONArray jsonMainNode = jsonResonse.optJSONArray("places");
            Places places;

            for(int i=0; i<jsonMainNode.length(); i++){
                JSONObject c = jsonMainNode.getJSONObject(i);

                places = new Places(c.getString("title_name"),c.getString("description"),c.getString("url"),c.getDouble("latitude"),c.getDouble("longitude"));

                placesList.add(places);
            }
        }catch (JSONException e){
            Toast.makeText(getApplicationContext(),"error " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    private class JsonReadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try{
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();

            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is){
            String Line;
            StringBuilder answer = new StringBuilder();
            BufferedReader RdLine = new BufferedReader(new InputStreamReader(is));
            try{
                while((Line = RdLine.readLine()) != null){
                    answer.append(Line);
                }
            }catch(IOException e){
                Toast.makeText(getApplicationContext(),"error" + e.toString(), Toast.LENGTH_LONG).show();
            }
            return  answer;
        }

        @Override
        protected void onPostExecute(String result){
            Addplacestolist();
            addMarkersToMap();
        }
    }
    public void getpoints() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Values.URL+"getuserpoints.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        User.points = Integer.parseInt(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this, getString(R.string.error_conncection_db),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("user", User.userName);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
