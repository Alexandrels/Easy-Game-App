package w2.com.br.easy_game_app.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.entity.Usuario;
import w2.com.br.easy_game_app.enuns.TipoPosicao;

public class InviteActivity extends AppCompatActivity {
    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

    }
}
