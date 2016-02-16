package w2.com.br.easy_game_app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.entity.Usuario;
import w2.com.br.easy_game_app.enuns.Method;
import w2.com.br.easy_game_app.enuns.TipoPosicao;
import w2.com.br.easy_game_app.enuns.TipoUsuario;

public class CadUsuarioActivity extends AppCompatActivity implements Atualizavel {

    private String servico = "usuario";
    private Spinner sp;
    private CheckBox chkJogador;
    private CheckBox chkTecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);

        //spinner posição
        ArrayAdapter<TipoPosicao> adapter = new ArrayAdapter<TipoPosicao>(this, android.support.design.R.layout.support_simple_spinner_dropdown_item, TipoPosicao.values());
        adapter.setDropDownViewResource(android.support.design.R.layout.support_simple_spinner_dropdown_item);
        sp = (Spinner) findViewById(R.id.sp_posicao);
        sp.setAdapter(adapter);


        Button salvar = (Button) findViewById(R.id.salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText ttLogin = (EditText) findViewById(R.id.login_usuario);
                    EditText ttSenha = (EditText) findViewById(R.id.senha_usuario);
                    EditText ttNome = (EditText) findViewById(R.id.nome_usuario);
                    EditText ttApelido = (EditText) findViewById(R.id.apelido_usuario);
                    EditText ttTelefone = (EditText) findViewById(R.id.telefone_usuario);
                    TipoPosicao tipoPosicao = (TipoPosicao) sp.getSelectedItem();
                    Usuario usuario = new Usuario();
                    usuario.setTipoPosicao(tipoPosicao);
                    if (!tipoUsuarioSelecionado()) {
                        Toast.makeText(getApplicationContext(), "Selecionar um Tipo de Usuário", Toast.LENGTH_SHORT).show();
                    } else {
                        if (ttNome != null) {
                            String nome = ttNome.getText().toString();
                            usuario.setNome(nome);
                        }
                        if (ttApelido != null) {
                            String apelido = ttApelido.getText().toString();
                            usuario.setApelido(apelido);
                        }
                        if (ttLogin != null) {
                            String login = ttLogin.getText().toString();
                            usuario.setLogin(login);
                        }
                        if (ttSenha != null) {
                            String senha = ttSenha.getText().toString();
                            usuario.setSenha(senha);
                        }

                        if (ttTelefone != null) {
                            String telefone = ttSenha.getText().toString();
                            usuario.setTelefone(telefone);
                        }

                        int tipo = chkJogador.isChecked() ? 0 : 1;
                        usuario.salvarTipoUsuario(Arrays.asList(TipoUsuario.values()[tipo]));

                        JSONObject jsonObject = usuario.toJSON();
                        new GenericAsyncTask(CadUsuarioActivity.this, CadUsuarioActivity.this, Method.POST, String.format("%s", servico), jsonObject.toString()).execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });
    }

    public boolean tipoUsuarioSelecionado() {
        chkJogador = (CheckBox) findViewById(R.id.checkbox_tipo_jogador);
        chkTecnico = (CheckBox) findViewById(R.id.checkbox_tipo_tecnico);
        if (chkTecnico.isChecked() || chkJogador.isChecked()) {
            return true;
        }
        return false;
    }

    public void clear(ViewGroup group) {

        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof CheckBox) {
                chkJogador.setChecked(true);
                chkTecnico.setChecked(false);
            }
        }
    }

    @Override
    public void atualizar(JSONObject jsonObject) {
        Gson gson = new Gson();
        if (jsonObject.has("objeto")) {
            Toast.makeText(this, "Usuário Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();
            clear((ViewGroup) findViewById(R.id.linearLayout));
        }
//        } else if (jsonObject.has("erro")) {
//            //TODO Toast;
//            Toast.makeText(this, "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
//        }
    }
}
