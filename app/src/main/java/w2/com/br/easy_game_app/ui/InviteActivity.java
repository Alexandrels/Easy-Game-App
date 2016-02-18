package w2.com.br.easy_game_app.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.enuns.TipoPosicao;

public class InviteActivity extends AppCompatActivity {

    private Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        //spinner posição
//        ArrayAdapter<TipoPosicao> adapter = new ArrayAdapter<TipoPosicao>(this, android.support.design.R.layout.support_simple_spinner_dropdown_item, TipoPosicao.values());
//        adapter.setDropDownViewResource(android.support.design.R.layout.support_simple_spinner_dropdown_item);
//        sp = (Spinner) findViewById(R.id.sp_posicao);
//        sp.setAdapter(adapter);

        Button btnSendMail = (Button) findViewById(R.id.btnConvite);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final EditText etDe = (EditText) getView().findViewById(R.id.et_EmailDe);
                final EditText etAsunto = (EditText) findViewById(R.id.nome_usuario);
                final EditText etMensaje = (EditText) findViewById(R.id.apelido_usuario);

                //String to = etDe.getText().toString();
                String subject = etAsunto.getText().toString();
                String message = etMensaje.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, "micorreo@gmail.com");
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                // need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Seleciona un cliente de correo"));
            }
        });

    }
}
