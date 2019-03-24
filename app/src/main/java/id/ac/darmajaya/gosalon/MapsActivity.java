package id.ac.darmajaya.gosalon;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Model.Toko.DataToko;
import id.ac.darmajaya.gosalon.Model.Toko.ResponseToko;
import id.ac.darmajaya.gosalon.Retrofit.client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "hahahaha";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Button btnmaps;
    private GoogleMap mMap;
    private ProgressDialog pDialog;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng garageLocation;
    private Marker marker;
    private List<DataToko> dataTokos = new ArrayList<>();
    private ResponseToko responseToko;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btnmaps = findViewById(R.id.btnmap);
        getLocationPermission();



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        fetchfrominternet();

        if (mLocationPermissionsGranted) {
//            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }
        LatLng peta = new LatLng(-5.4219919, 105.2590389);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(peta, 15f));




        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                garageLocation = latLng;
                moveCamera(latLng, DEFAULT_ZOOM);
            }
        });



        btnmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("location_lat", garageLocation.latitude);
                intent.putExtra("location_lng", garageLocation.longitude);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void moveCamera(LatLng latLng, float zoom) {
//        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
                .title("Lokasi Saya");
        marker = mMap.addMarker(options);



    }

    private void fetchfrominternet() {

        Call<ResponseToko> user = client.getApi().getdatatoko();
        user.enqueue(new Callback<ResponseToko>() {
            @Override
            public void onResponse(Call<ResponseToko> call, Response<ResponseToko> response) {
                if (response.isSuccessful()) {
                    responseToko = response.body();
                    dataTokos.addAll(responseToko.getData());
                    for(int i = 0; i < dataTokos.size(); i++){
                        String[] koordinat = dataTokos.get(i).getKordinat().split(",");
                        LatLng latLng = new LatLng(Double.parseDouble(koordinat[0]), Double.parseDouble(koordinat[1]));
                        System.out.println("kordinat " + koordinat[0] +"," + koordinat[1]);
                        MarkerOptions options = new MarkerOptions()
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .position(latLng)
                                .title(dataTokos.get(i).getNama_toko());
                        marker = mMap.addMarker(options);
                    }

                } else {
                    Toasty.error(getApplicationContext(), "Data Tidak ditemukan", Toast.LENGTH_LONG).show();
                    System.out.println("data user " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ResponseToko> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Koneksi Tidak ada", Toast.LENGTH_LONG).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
                System.out.println("data user" + t.getMessage());

            }
        });
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                Task completeListener = location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM));
                            MarkerOptions options = new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                    .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                                    .title("Lokasi Saya");
                            mMap.addMarker(options);

                        } else {
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}