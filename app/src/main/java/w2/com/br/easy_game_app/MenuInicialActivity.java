package w2.com.br.easy_game_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        //dialog
        Button btnAlertDialog = (Button) findViewById(R.id.dlgConvite);
        btnAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                View dialoglayout = inflater.inflate(R.layout.activity_convite, null);

                final EditText etAsunto = (EditText) dialoglayout.findViewById(R.id.et_EmailAsunto);
                final EditText etMensaje = (EditText) dialoglayout.findViewById(R.id.et_EmailMensaje);

                Button btnEnviarMail = (Button) dialoglayout.findViewById(R.id.btnEnviarMail);
                btnEnviarMail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String subject = etAsunto.getText().toString();
                        String message = etMensaje.getText().toString();

                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[] { "micorre@gmail.com"});
                        email.putExtra(Intent.EXTRA_SUBJECT, subject);
                        email.putExtra(Intent.EXTRA_TEXT, " mensaje " + message);

                        // need this to prompts email client only
                        email.setType("message/rfc822");
                        startActivity(Intent.createChooser(email, "Seleciona un cliente de correo"));

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(MenuInicialActivity.this);
                builder.setView(dialoglayout);
                builder.show();
            }
        });

    }

}
