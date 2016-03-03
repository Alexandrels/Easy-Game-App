package w2.com.br.easy_game_app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import w2.com.br.easy_game_app.ListAdapter.AdapterListViewMeusTimes;
import w2.com.br.easy_game_app.ListAdapter.ListAdapterJogador;
import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.entity.Equipe;
import w2.com.br.easy_game_app.enuns.Method;

public class AdmTimeUI extends AppCompatActivity implements Atualizavel {
    private final String servico = "equipe";
    private TabHost tabHost;
    private Equipe equipe;
    private EditText nomeEquipe, dataFundacao;
    private ListView listViewJogador;
    private ListAdapterJogador adapterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_time_ui);

        Intent intent = getIntent();
        long idEquipe = intent.getLongExtra("id", -1);

        tabHost = (TabHost) findViewById(R.id.adm_time_ui_tabhost);
        tabHost.setup();

        TabHost.TabSpec tsTime = tabHost.newTabSpec(null);
        tsTime.setIndicator("Time");
        tsTime.setContent(R.id.adm_time_ui);

        TabHost.TabSpec tsAgenda = tabHost.newTabSpec(null);
        tsAgenda.setContent(R.id.adm_time_ui_agenda);
        tsAgenda.setIndicator("Agenda");

        TabHost.TabSpec tsConvidar = tabHost.newTabSpec(null);
        tsConvidar.setIndicator("Convidar");
        tsConvidar.setContent(R.id.adm_time_ui_convidar);

        TabHost.TabSpec tsPendentes = tabHost.newTabSpec(null);
        tsPendentes.setIndicator("Pendentes");
        tsPendentes.setContent(R.id.adm_time_ui_pendentes);

        tabHost.addTab(tsTime);
        tabHost.addTab(tsAgenda);
        tabHost.addTab(tsConvidar);
        tabHost.addTab(tsPendentes);

        nomeEquipe = (EditText) findViewById(R.id.nome_equipe);
        dataFundacao = (EditText) findViewById(R.id.fundacao_equipe);

        listViewJogador = (ListView) findViewById(R.id.listaJogadores);


        new GenericAsyncTask(AdmTimeUI.this, this, Method.GET, String.format("%s/%d", servico,idEquipe)).execute();
    }

    @Override
    public void atualizar(JSONObject jsonObject) throws JSONException {

        equipe = new Equipe();
        JSONObject retorno = null;
        if (jsonObject.has("objeto")) {
            retorno = new JSONObject(jsonObject.getString("objeto"));
            try {
                if (retorno.has("objeto")) {
                    equipe = Equipe.toEquipe(retorno.getJSONObject("objeto"));
                    if(equipe != null){
                        nomeEquipe.setText(equipe.getNome());
                        dataFundacao.setText(equipe.getDataFundacao().toString());
                        adapterListView = new ListAdapterJogador(AdmTimeUI.this,equipe.getListUsuarioEquipe());
                        listViewJogador.setAdapter(adapterListView);
                        //Cor quando a lista é selecionada para ralagem.
                        listViewJogador.setCacheColorHint(Color.TRANSPARENT);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (jsonObject.has("erro")) {
            //TODO Toast;
            Toast.makeText(this, retorno.get("erro").toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
