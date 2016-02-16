package w2.com.br.easy_game_app.fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import w2.com.br.easy_game_app.util.Localizador;

/**
 * Created by alexandre on 12/02/16.
 */
public class MapaFragment extends SupportMapFragment {

    private GoogleMap map;


    @Override
    public void onResume() {
        super.onResume();
        Localizador localizador = new Localizador(getActivity());
        LatLng local = localizador.getCoordenada("Rua Ines Canha Machioski, 301");
        centralizaNo(local);

        LatLng coordenada = localizador.getCoordenada("Rua Rui Puppi, 524");
        if (coordenada != null) {
            MarkerOptions marcador = new MarkerOptions().position(coordenada).title("Casa Ale").snippet("(41)99221257");
            getMap().addMarker(marcador);
        }
    }

    public void centralizaNo(LatLng local) {
        GoogleMap mapa = getMap();
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 17));
    }


}
