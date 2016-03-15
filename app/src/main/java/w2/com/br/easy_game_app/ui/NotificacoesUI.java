package w2.com.br.easy_game_app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import w2.com.br.easy_game_app.ListAdapter.ListAdapterConvitePendente;
import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.entity.UsuarioEquipe;
import w2.com.br.easy_game_app.enuns.Method;

public class NotificacoesUI extends AppCompatActivity implements Atualizavel {

    private final String servicoUsuarioEquipe = "usuarioEquipe";
    private List<UsuarioEquipe> convitesPendentes;
    private ListView listViewConvite;
    private ListAdapterConvitePendente adapterConvite;
    private Long idUsuario = 4l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes_ui);
        listViewConvite = (ListView) findViewById(R.id.listaConvites);

//        Intent intent = getIntent();
//        idUsuario = intent.getLongExtra("idUsuario", -1);

        new GenericAsyncTask(NotificacoesUI.this, this, Method.GET, String.format("%s/%d", servicoUsuarioEquipe, idUsuario)).execute();
    }

    @Override
    public void atualizar(JSONObject jsonObject) throws JSONException {
        convitesPendentes = new ArrayList<>();
        JSONObject retorno = null;
        if (jsonObject.has("objeto")) {
            retorno = new JSONObject(jsonObject.getString("objeto")) ;
            try {
                if(retorno.has("array")){
                    JSONArray array = retorno.getJSONArray("array");
                    for (int i = 0; i < array.length(); i++) {
                        convitesPendentes.add(UsuarioEquipe.toUsuarioEquipe(array.getJSONObject(i)));
                    }
                    adapterConvite = new ListAdapterConvitePendente(NotificacoesUI.this,convitesPendentes);
                    listViewConvite.setAdapter(adapterConvite);
                    //Cor quando a lista é selecionada para ralagem.
                    listViewConvite.setCacheColorHint(Color.TRANSPARENT);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(jsonObject.has("erro")) {
            //TODO Toast;
            Toast.makeText(this, retorno.get("erro").toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
