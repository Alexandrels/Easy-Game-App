package w2.com.br.easy_game_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.entity.Equipe;
import w2.com.br.easy_game_app.enuns.Method;

public class MeusTimesUI extends AppCompatActivity implements Atualizavel {
    private ListView listaEquipes;
    private String servico = "equipe";
    private List<Equipe> equipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_times_ui);

        listaEquipes = (ListView) findViewById(R.id.listaEquipes);
        listaEquipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), AdmTimeUI.class);
                startActivity(it);
            }
        });

        new GenericAsyncTask(this,this, Method.GET,servico).execute();
    }

    @Override
    public void atualizar(JSONObject jsonObject) throws JSONException {
        equipes = new ArrayList<>();
        if (jsonObject.has("objeto")) {
            try {
                JSONArray array = jsonObject.getJSONArray("array");
                for (int i = 0; i < array.length(); i++) {
                    //adiono as equipes que vieram da web
//                    equipes.add(gson.fromJson(array.getJSONObject(i).toString(), Jogador.class));
                }
                ListAdapter adapter = new ArrayAdapter<Equipe>(this,android.R.layout.simple_list_item_1,equipes);
                listaEquipes.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(jsonObject.has("erro")) {
            //TODO Toast;
        }
    }
}
