package w2.com.br.easy_game_app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import w2.com.br.easy_game_app.ListAdapter.AdapterListViewMeusTimes;
import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.entity.Equipe;
import w2.com.br.easy_game_app.enuns.Method;

public class MeusTimesUI extends AppCompatActivity implements Atualizavel {
    private ListView listaEquipes;
    private String servico = "equipe";
    private List<Equipe> equipes;
    private AdapterListViewMeusTimes adapterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_times_ui);

        listaEquipes = (ListView) findViewById(R.id.listaEquipes);
        listaEquipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipe equipe = (Equipe) parent.getAdapter().getItem(position);
                Long equipeId = equipe.getId();
                Intent it = new Intent(view.getContext(), AdmTimeUI.class);
                it.putExtra("id", equipeId);
                startActivity(it);
            }
        });

        new GenericAsyncTask(this,this, Method.GET,servico).execute();
    }

    @Override
    public void atualizar(JSONObject jsonObject) throws JSONException {
        equipes = new ArrayList<>();
        JSONObject retorno = null;
        if (jsonObject.has("objeto")) {
            retorno = new JSONObject(jsonObject.getString("objeto")) ;
            try {
                if(retorno.has("array")){
                    JSONArray array = retorno.getJSONArray("array");
                    for (int i = 0; i < array.length(); i++) {
                        equipes.add(Equipe.toEquipe(array.getJSONObject(i)));
                    }
                     adapterListView = new AdapterListViewMeusTimes(MeusTimesUI.this,equipes);
                    listaEquipes.setAdapter(adapterListView);
                    //Cor quando a lista é selecionada para ralagem.
                    listaEquipes.setCacheColorHint(Color.TRANSPARENT);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(jsonObject.has("erro")) {
            //TODO Toast;
            Toast.makeText(this,retorno.get("erro").toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
