package w2.com.br.easy_game_app.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import w2.com.br.easy_game_app.R;

public class MapaUsuariosActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_usuarios);

        SupportMapFragment mapaFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_usuarios));
        mapaFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-25.358840, -49.214756);
        mMap.addMarker(new MarkerOptions().position(sydney).title("(41)99221257"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
    }
}
