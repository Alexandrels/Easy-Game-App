package w2.com.br.easy_game_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import w2.com.br.easy_game_app.MenuInicialActivity;
import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.enuns.Method;
import w2.com.br.easy_game_app.task.SincronismoService;

public class LoginUI extends AppCompatActivity implements Atualizavel {

    private Button btLogar;
    private Button btCancelar;
    private EditText ed1;
    private EditText ed2;
    private final String servico = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);

        btLogar = (Button) findViewById(R.id.btnLogar);
        ed1 = (EditText) findViewById(R.id.edLogin);
        ed2 = (EditText) findViewById(R.id.editSenha);
        TextView cadastrar = (TextView) findViewById(R.id.cadastra_se);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cad = new Intent(LoginUI.this, CadUsuarioActivity.class);
                startActivity(cad);
            }
        });

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1 != null && ed2 != null) {
                    try {
                        JSONObject credenciais = new JSONObject();

                        credenciais.put("login", ed1.getText().toString());
                        credenciais.put("senha", ed2.getText().toString());
                        acessaDashboard();
                        //ja esta acessando a web
//                        new GenericAsyncTask(LoginUI.this, LoginUI.this, Method.POST, String.format("%s", servico), credenciais.toString()).execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
//                if (ed1.getText().toString().equals("admin") && ed2.getText().toString().equals("admin")) {
//                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
//                    Intent menu = new Intent(LoginUI.this, MenuInicialActivity.class);
//                    startActivity(menu);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Credenciais incorretas!", Toast.LENGTH_SHORT).show();
//
//                }
            }
        });
        startService(new Intent(LoginUI.this, SincronismoService.class));

    }

    @Override
    public void atualizar(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("objeto")) {
            String retorno = jsonObject.getString("objeto");
            Log.i("RETORNO_LOGIN", retorno.toString());
            if ("ok".equals(retorno)) {
                acessaDashboard();
            }
        } else if (jsonObject.has("erro")) {
            //TODO Toast;
            Toast.makeText(this, "Credênciais incorretas...", Toast.LENGTH_SHORT).show();

        }

    }

    public void acessaDashboard() {
        Intent menu = new Intent(LoginUI.this, MenuInicialActivity.class);
        startActivity(menu);
    }
}
