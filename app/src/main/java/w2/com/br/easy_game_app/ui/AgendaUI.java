package w2.com.br.easy_game_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import w2.com.br.easy_game_app.ListAdapter.ListAdapterEvento;
import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.entity.Evento;
import w2.com.br.easy_game_app.enuns.Method;

public class AgendaUI extends AppCompatActivity implements Atualizavel{

    private final String servico = "agenda";
    private List<Evento> eventos;
    private TabHost tabHost;
    private ListView lvEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_ui);

        Intent intent = getIntent();
        Long idEquipe = intent.getLongExtra("id",-1l);

        tabHost = (TabHost) findViewById(R.id.agenda_ui_tabhost );
        tabHost.setup();

        TabHost.TabSpec tsNovosSolicitados = tabHost.newTabSpec(null);
        tsNovosSolicitados.setIndicator("Jogos");
        tsNovosSolicitados.setContent(R.id.agenda_ui_jogos);

        TabHost.TabSpec tsMateriais = tabHost.newTabSpec(null);
        tsMateriais.setContent(R.id.agenda_ui_churrasco);
        tsMateriais.setIndicator("Churrascos");

        TabHost.TabSpec tsSolicitados = tabHost.newTabSpec(null);
        tsSolicitados.setIndicator("Diversos");
        tsSolicitados.setContent(R.id.agenda_ui_diversos);

        tabHost.addTab(tsNovosSolicitados);
        tabHost.addTab(tsMateriais);
        tabHost.addTab(tsSolicitados);

        lvEventos = (ListView) findViewById(R.id.agenda_ui_lv_jogos);

        new GenericAsyncTask(AgendaUI.this, AgendaUI.this, Method.GET, String.format("%s/%f", servico,idEquipe)).execute();
    }

    @Override
    public void atualizar(JSONObject jsonObject) {
        Gson gson = new Gson();
        eventos = new ArrayList<>();
        if (jsonObject.has("eventos")) {
            try {
                JSONArray array = jsonObject.getJSONArray("eventos");
                for (int i = 0; i < array.length(); i++) {
                    eventos.add(gson.fromJson(array.getJSONObject(i).toString(), Evento.class));
                }
//                ListAdapter adapter = new ListAdapterEvento(this,R.layout.agenda_ui,eventos);
//                lvEventos.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(jsonObject.has("erro")) {
            //TODO Toast;
        }
    }
}
