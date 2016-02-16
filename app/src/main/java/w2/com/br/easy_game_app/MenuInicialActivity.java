package w2.com.br.easy_game_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import w2.com.br.easy_game_app.ui.CadEquipeActivity;
import w2.com.br.easy_game_app.ui.CadUsuarioActivity;
import w2.com.br.easy_game_app.ui.MapaUsuariosActivity;
import w2.com.br.easy_game_app.ui.MostraUsuariosActivity;

public class MenuInicialActivity extends AppCompatActivity {

    private Button btnCadUsuario;
    private Button btnCadEquipe;
    private Button btnMostraMapaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        btnCadUsuario = (Button) findViewById(R.id.cad_usuario);

        btnCadUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), CadUsuarioActivity.class);
                startActivity(it);
            }
        });

        btnCadEquipe = (Button) findViewById(R.id.cad_equipe);

        btnCadEquipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), CadEquipeActivity.class);
                startActivity(it);
            }
        });

        btnMostraMapaUsuarios = (Button) findViewById(R.id.mapa_mostra_usuarios);

        btnMostraMapaUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), MapaUsuariosActivity.class);
                startActivity(it);
            }
        });

    }

}
