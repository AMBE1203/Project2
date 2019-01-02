package vn.com.ambe.fragment;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import vn.com.ambe.adapter.AdapterInfoWindow;
import vn.com.ambe.atmproject2.MainActivity;
import vn.com.ambe.atmproject2.R;
import vn.com.ambe.controller.CtlAtm;
import vn.com.ambe.controller.HttpDataHandler;
import vn.com.ambe.model.ATM;
import vn.com.ambe.model.Data;
import vn.com.ambe.model.GoogleData;
import vn.com.ambe.model.Leg;
import vn.com.ambe.model.Overview_polyline;
import vn.com.ambe.model.Result;
import vn.com.ambe.model.Route;

import static vn.com.ambe.atmproject2.R.id.map;
import static vn.com.ambe.fragment.SettingFragment.pre;

/**
 * Created by duong on 15/03/2017.
 */

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap mMap;
    private ImageView imgMode;
    private TextView txtViTriCuaToi;
    private TextView txtViTriKetThuc;
    private Button btnTimKiem;
    private TextView txtQuangDuong;
    private TextView txtThoiGian;
    public static ProgressDialog dialog;
    private ArrayList<Marker> arrMaker;
    private ArrayList<ATM> arrATm;

    private ArrayList<String> tenNganHangs = new ArrayList<String>();
    private CtlAtm ctlAtm;
    private LatLng myLocation;
    private LatLng end_location;
    private LatLng start_location;
    private double myLat;
    private double myLng;
    private double viDo;
    private double kinhDo;

    private int kieuDuong;
    private ArrayList<Route> routes;
    private ArrayList<Leg> legs;
    private ArrayList<LatLng> points;
    private Overview_polyline overview_polyline;

    private ArrayList<Marker> atmMarkers;
    private ArrayList<Marker> originMarkers;
    private ArrayList<Marker> destinationMarkers;
    private ArrayList<Polyline> polylinePaths;

    private Marker markerSelected;

    private boolean checkAll;
    private ArrayList<Result> results;


    private static final String GOOGLE_API_KEY = "AIzaSyAmhESjUD5OpmcdNniuZe0_BdJNFtG8qGg";
    private static final String GEOCODING_API_KEY = "AIzaSyC2Ife77z-7_RKR_HI4QQgsmE2e1MVAu1I";
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GEOCODING_URL_API = "https://maps.googleapis.com/maps/api/geocode/json?&latlng=";
    private String origin = "";
    private String destination = "";
    private String mode1 = "";
    private String alternatives = "";


    public HomeFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.home));
        addControls();
//        getMyFile();
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTimKiem();
            }
        });

        return view;
    }

    private void getMyFile() {
        String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfile.txt";
        try {
            File file = new File(sdCard);
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder sb = new StringBuilder();
            String line = bf.readLine();
            while (line != null) {
                sb.append(line);
                line = bf.readLine();
            }
            bf.close();
            fileInputStream.close();
            String x = sb.toString();
            ArrayList<String> list = new ArrayList<String>();
            ArrayList<String> arrayList = new ArrayList<>();

            list.addAll(Arrays.asList(x.split(",")));
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).equals("all")) {
                    arrATm = ctlAtm.getListAtm(MainActivity.database);
                }
                if (list.get(i).equals("0")) {
                    imgMode.setImageResource(R.drawable.walking);
                    mode1 = "walking";
                }
                if (list.get(i).equals("1")) {
                    imgMode.setImageResource(R.drawable.driving);
                    mode1 = "driving";
                }
            }

            for (int i = 0; i < getResources().getStringArray(R.array.arrNganHang).length; i++) {
                String currentItem = (String) getResources().getStringArray(R.array.arrNganHang)[i];
                if (list.contains(currentItem)) {
                    arrayList.add(currentItem);

                }

            }
            arrATm = ctlAtm.getListAtmTheoNganHang(MainActivity.database, arrayList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addControls() {

        ctlAtm = new CtlAtm();
        arrATm = new ArrayList<ATM>();
        arrMaker = new ArrayList<Marker>();
        routes = new ArrayList<Route>();
        legs = new ArrayList<Leg>();
        points = new ArrayList<LatLng>();
        originMarkers = new ArrayList<Marker>();
        destinationMarkers = new ArrayList<Marker>();
        atmMarkers = new ArrayList<Marker>();
        polylinePaths = new ArrayList<Polyline>();
        results = new ArrayList<Result>();

        txtViTriCuaToi = (TextView) view.findViewById(R.id.edit_diem_bat_dau);
        txtViTriKetThuc = (TextView) view.findViewById(R.id.edit_dia_diem_ket_thuc);
        btnTimKiem = (Button) view.findViewById(R.id.btn_tim_kiem_home);
        txtQuangDuong = (TextView) view.findViewById(R.id.txt_quang_duong);
        txtThoiGian = (TextView) view.findViewById(R.id.txt_thoi_gian);
        imgMode = (ImageView) view.findViewById(R.id.img_mode);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle(getResources().getString(R.string.thong_bao));
        dialog.setMessage(getResources().getString(R.string.dang_tai));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

    @Override
    public void onResume() {
        super.onResume();
        restore();
    }

    private void restore() {
        SharedPreferences preferences = getActivity().getSharedPreferences(pre, Context.MODE_PRIVATE);
        kieuDuong = preferences.getInt("index", -1);
        String nganHang = preferences.getString("nganhang", "");
        checkAll = preferences.getBoolean("check", true);

        this.tenNganHangs.addAll(Arrays.asList(nganHang.split(",")));
        if (checkAll) {
            arrATm = ctlAtm.getListAtm(MainActivity.database);
        } else {
            arrATm = ctlAtm.getListAtmTheoNganHang(MainActivity.database, tenNganHangs);
        }

        if (kieuDuong == 0) {
            imgMode.setImageResource(R.drawable.walking);
            mode1 = "walking";
        } else if (kieuDuong == 1) {
            imgMode.setImageResource(R.drawable.driving);
            mode1 = "driving";
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.item, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_none:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                item.setChecked(true);
                break;
            case R.id.menu_normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                item.setChecked(true);
                break;
            case R.id.menu_hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                item.setChecked(true);
                break;
            case R.id.menu_satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                item.setChecked(true);
                break;
            case R.id.menu_terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                item.setChecked(true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLat = location.getLatitude();
                myLng = location.getLongitude();
                myLocation = new LatLng(myLat, myLng);
                if (mMap != null) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(myLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
                    GetMyAddressTask task = new GetMyAddressTask();
                    //                   task.execute(String.format("%.6f,%.6f", myLat, myLng));
                    String link = GEOCODING_URL_API + myLat + "," + myLng + "&sensor=false&key=" + GEOCODING_API_KEY;
                    task.execute(link);
                    dialog.dismiss();
                }
            }
        });

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                dialog.dismiss();
            }
        });


        for (ATM atm : arrATm) {
            LatLng latLng = new LatLng(atm.getViDo(), atm.getKinhDo());
            atmMarkers.add(mMap.addMarker(new MarkerOptions().position(latLng)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }


        Bundle data = getArguments();
        if (data != null) {
            ATM atm = new ATM();
            atm = (ATM) data.getSerializable("ATM");
            dialog.show();
            mMap.clear();

            LatLng atmLat = new LatLng(atm.getViDo(), atm.getKinhDo());
            if (mMap != null) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(atmLat).title(atm.getTenNganHang().toString() + ""));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLat, 16));
                mMap.setInfoWindowAdapter(new AdapterInfoWindow(getActivity(), atm));
                marker.showInfoWindow();
            }

        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markerSelected = marker;
                txtViTriKetThuc.setText("");
                viDo = marker.getPosition().latitude;
                kinhDo = marker.getPosition().longitude;
                String diaChi = ctlAtm.getAdress(MainActivity.database, viDo, kinhDo);
                txtViTriKetThuc.setText(diaChi);
                return false;
            }
        });


    }

    private void xuLyTimKiem() {
//String link="http://maps.googleapis.com/maps/api/directions/json?origin=21.003733,105.846533&destination=20.997775,105.867925&sensor=false&units=metric&mode=walking"+GOOGLE_API_KEY;
        alternatives = "false";
        origin = myLat + "," + myLng;
        destination = viDo + "," + kinhDo;
        String link = DIRECTION_URL_API + "origin=" + origin + "&destination=" + destination + "&sensor=false&units=metric&mode=" + mode1 + "&alternatives=" + alternatives + "&key=" + GOOGLE_API_KEY;
        DirectionTask task = new DirectionTask();
        task.execute(link);
    }

    private class GetMyAddressTask extends AsyncTask<String, Void, Data> {

        @Override
        protected Data doInBackground(String... params) {

//
            try {
                URL url = new URL(params[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                Data data = new Gson().fromJson(inputStreamReader, Data.class);
                return data;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute(Data s) {
            super.onPostExecute(s);

            results = s.getResults();
            String viTriCuaToi = results.get(0).getFormatted_address();
            txtViTriCuaToi.setText(viTriCuaToi);


           }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private class DirectionTask extends AsyncTask<String, Void, GoogleData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();


            if (originMarkers != null) {
                for (Marker marker : originMarkers) {
                    marker.remove();
                }
            }

            if (destinationMarkers != null) {
                for (Marker marker : destinationMarkers) {
                    marker.remove();
                }
            }

            if (polylinePaths != null) {
                for (Polyline polyline : polylinePaths) {
                    polyline.remove();
                }

            }

//            if (atmMarkers != null){
//                for (Marker marker : atmMarkers){
//                    if (marker != markerSelected) {
//                        marker.remove();
//                    }
//                }
//            }
        }

        @Override
        protected void onPostExecute(GoogleData googleData) {
            super.onPostExecute(googleData);
            dialog.dismiss();
            routes = googleData.getRoutes();

            for (Route route : routes) {
                legs = route.getLegs();
                overview_polyline = route.getOverview_polyline();
                points = decodePolyLine(overview_polyline.getPoints());
                for (Leg leg : legs) {
                    txtThoiGian.setText(leg.getDuration().getText());
                    txtQuangDuong.setText(leg.getDistance().getText());
                    end_location = leg.getEnd_location();
                    start_location = leg.getStart_location();

                    originMarkers.add(mMap.addMarker(new MarkerOptions()
                            .position(start_location)));
                    destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                            .position(end_location)));

                    PolylineOptions polylineOptions = new PolylineOptions().
                            geodesic(true).
                            color(Color.BLUE).
                            width(10);

                    for (int i = 0; i < points.size(); i++)
                        polylineOptions.add(points.get(i));

                    polylinePaths.add(mMap.addPolyline(polylineOptions));
                }
            }

        }

        private ArrayList<LatLng> decodePolyLine(String poly) {
            int len = poly.length();
            int index = 0;
            ArrayList<LatLng> decoded = new ArrayList<LatLng>();
            int lat = 0;
            int lng = 0;

            while (index < len) {
                int b;
                int shift = 0;
                int result = 0;
                do {
                    b = poly.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = poly.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                decoded.add(new LatLng(
                        lat / 100000d, lng / 100000d
                ));
            }

            return decoded;


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected GoogleData doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                GoogleData googleData = new Gson().fromJson(inputStreamReader, GoogleData.class);
                return googleData;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
