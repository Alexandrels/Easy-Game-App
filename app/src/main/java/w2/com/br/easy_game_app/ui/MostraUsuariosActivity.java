package w2.com.br.easy_game_app.ui;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.fragment.MapaFragment;

public class MostraUsuariosActivity extends FragmentActivity {


    private FragmentTransaction tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_mostra_usuario_layout);

        tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.mapa, new MapaFragment());
        tx.commit();
    }

}
