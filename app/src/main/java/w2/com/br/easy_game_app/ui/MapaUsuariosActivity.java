package w2.com.br.easy_game_app.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.enuns.TipoPosicao;

public class MapaUsuariosActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_usuarios);

        SupportMapFragment mapaFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_usuarios));
        mapaFragment.getMapAsync(this);

        //spinner posição
//        ArrayAdapter<TipoPosicao> adapter = new ArrayAdapter<TipoPosicao>(this, android.support.design.R.layout.support_simple_spinner_dropdown_item, TipoPosicao.values());
//        adapter.setDropDownViewResource(android.support.design.R.layout.support_simple_spinner_dropdown_item);
//        sp = (Spinner) findViewById(R.id.sp_posicao);
//        sp.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-25.358840, -49.214756);
        mMap.addMarker(new MarkerOptions().position(sydney).title("(41)99221257"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));

//        customMarker(new LatLng(-25.358840, -49.214756), "Marcador 1", "title marcador 1");
//        customMarker(new LatLng(-25.358832, -49.214740), "Marcador 2", "title marcador 2");
        //EVENTS
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.i("Script", "setOnCameraChangeListner");
                if(marker!= null){
                    marker.remove();
                }
                customMarker(new LatLng(cameraPosition.target.latitude,cameraPosition.target.longitude),"1: MAracador ALterado","O markador foi reposicionado");

            }
        });

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                Log.i("Script", "setOnMapClickListener");
//                if (marker != null) {
//                    marker.remove();
//                }
//                customMarker(new LatLng(latLng.latitude, latLng.longitude), "2: MAracador ALterado", "O markador foi reposicionado");
//
//            }
//        });
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Log.i("Script", "3: Marker: " + marker.getTitle());
//
//                return false;
//            }
//        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("Script", "4: Marker: " + marker.getTitle());
                LayoutInflater inflater = getLayoutInflater();

                View dialoglayout = inflater.inflate(R.layout.activity_invite, null);



                AlertDialog.Builder builder = new AlertDialog.Builder(MapaUsuariosActivity.this);
                builder.setView(dialoglayout);
                builder.show();
            }
        });
    }
    public void customMarker(LatLng latLng, String title, String snippet){
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).title(title).snippet(snippet);

        //criar o marker
        marker = mMap.addMarker(options);

    }
}
