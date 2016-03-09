package w2.com.br.easy_game_app.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import w2.com.br.easy_game_app.ListAdapter.AdapterListViewMeusTimes;
import w2.com.br.easy_game_app.ListAdapter.ListAdapterJogador;
import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.entity.Equipe;
import w2.com.br.easy_game_app.entity.Usuario;
import w2.com.br.easy_game_app.enuns.Method;
import w2.com.br.easy_game_app.enuns.TipoPosicao;

public class AdmTimeUI extends AppCompatActivity implements Atualizavel, OnMapReadyCallback {
    private final String servico = "equipe";
    private final String servicoMapa = "coordenadas/1";
    private TabHost tabHost;
    private Equipe equipe;
    private TextView nomeEquipe, dataFundacao;
    private ListView listViewJogador;
    private ListAdapterJogador adapterListView;
    private Button btnMostraMapaUsuarios;
    private String strNomeEquipe;

    //efrente ao mapa
    private List<Usuario> jogadores;
    private GoogleMap mMap;
    private Marker marker;
    private Spinner sp;
    ArrayAdapter<TipoPosicao> adapter;
    private long idEquipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_time_ui);

        Intent intent = getIntent();
        idEquipe = intent.getLongExtra("idEquipe", -1);

        tabHost = (TabHost) findViewById(R.id.adm_time_ui_tabhost);
        tabHost.setup();

        TabHost.TabSpec tsTime = tabHost.newTabSpec(null);
        tsTime.setIndicator("Time");
        tsTime.setContent(R.id.adm_time_ui);

        TabHost.TabSpec tsAgenda = tabHost.newTabSpec(null);
        tsAgenda.setContent(R.id.adm_time_ui_agenda);
        tsAgenda.setIndicator("Agenda");

        TabHost.TabSpec tsPendentes = tabHost.newTabSpec(null);
        tsPendentes.setIndicator("Pendentes");
        tsPendentes.setContent(R.id.adm_time_ui_pendentes);

        tabHost.addTab(tsTime);
        tabHost.addTab(tsAgenda);
        tabHost.addTab(tsPendentes);

        nomeEquipe = (TextView) findViewById(R.id.nome_equipe);
        nomeEquipe.setText(strNomeEquipe);

        listViewJogador = (ListView) findViewById(R.id.listaJogadores);

        btnMostraMapaUsuarios = (Button) findViewById(R.id.mapa_mostra_usuarios);

        btnMostraMapaUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), MapaConviteUI.class);
                it.putExtra("idEquipe", idEquipe);
                it.putExtra("nomeEquipe", equipe.getNome());
                startActivity(it);
            }
        });


        //spinner posição
        adapter = new ArrayAdapter<TipoPosicao>(this, android.support.design.R.layout.support_simple_spinner_dropdown_item, TipoPosicao.values());
        adapter.setDropDownViewResource(android.support.design.R.layout.support_simple_spinner_dropdown_item);

        new GenericAsyncTask(AdmTimeUI.this, this, Method.GET, String.format("%s/%d", servico, idEquipe)).execute();
    }

    @Override
    public void atualizar(JSONObject jsonObject) throws JSONException {
        jogadores = new ArrayList<>();
        equipe = new Equipe();
        JSONObject retorno = null;
        if (jsonObject.has("objeto")) {
            retorno = new JSONObject(jsonObject.getString("objeto"));
            try {
                if (retorno.has("objeto")) {
                    if ("coordenadas".equals(retorno.getString("objeto"))) {
                        JSONArray array = retorno.getJSONArray("coordenadas");
                        for (int i = 0; i < array.length(); i++) {
                            jogadores.add(Usuario.toUsuario(array.getJSONObject(i)));
                        }
                    } else {
                        equipe = Equipe.toEquipe(retorno.getJSONObject("objeto"));
                        if (equipe != null) {
                            nomeEquipe.setText(equipe.getNome());
                            adapterListView = new ListAdapterJogador(AdmTimeUI.this, equipe.getListUsuarioEquipe());
                            listViewJogador.setAdapter(adapterListView);
                            //Cor quando a lista é selecionada para ralagem.
                            listViewJogador.setCacheColorHint(Color.TRANSPARENT);
                        }

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (jogadores != null) {
            LatLng sydney = new LatLng(-25.358840, -49.214756);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("(41)99221257"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
            for (Usuario usuario : jogadores) {
                sydney = new LatLng(usuario.getLatitude(), usuario.getLongitude());
                String dados = String.format("%s %s %s", usuario.getApelido(), usuario.getTelefone(), usuario.getTipoPosicao().getDescricao());
                mMap.addMarker(new MarkerOptions().position(sydney).title(dados));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
            mMap.setMapType(0);

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Log.i("Script", "4: Marker: " + marker.getTitle());
                    LayoutInflater inflater = getLayoutInflater();

                    final View dialoglayout = inflater.inflate(R.layout.activity_invite, null);

                    sp = (Spinner) dialoglayout.findViewById(R.id.sp_posicao_invite);
                    sp.setAdapter(adapter);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AdmTimeUI.this);
                    builder.setView(dialoglayout);
                    builder.setPositiveButton("Convidar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText ttNome = (EditText) dialoglayout.findViewById(R.id.nome_usuario);
                            EditText ttApelido = (EditText) dialoglayout.findViewById(R.id.apelido_usuario);
                            TipoPosicao tipoPosicao = (TipoPosicao) sp.getSelectedItem();
                            Usuario usuario = new Usuario();
                            usuario.setTipoPosicao(tipoPosicao);
                            if (ttNome != null) {
                                String nome = ttNome.getText().toString();
                                usuario.setNome(nome);
                            }
                            if (ttApelido != null) {
                                String apelido = ttApelido.getText().toString();
                                usuario.setApelido(apelido);
                            }
                            if (usuario.getTipoPosicao() != null) {
                                Toast.makeText(getApplicationContext(), "Você é fodão! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();
                }
            });

        }

    }

    public void customMarker(LatLng latLng, String title, String snippet) {
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).title(title).snippet(snippet);

        //criar o marker
        marker = mMap.addMarker(options);

    }
}
