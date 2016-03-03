package w2.com.br.easy_game_app.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import w2.com.br.easy_game_app.conexao.HttpConectionSingleton;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.enuns.Method;


/**
 * Created by alexandre on 31/08/15.
 */
public class GenericAsyncTask extends AsyncTask<String, ProgressDialog, JSONObject> {

    private static final String DESENVOLVIMENTO = "http://192.168.1.13";
    private static final String ONDELINE = "http://52.88.65.93";

    private static final String URL_WS = String.format("%s:8080/easy-game/servicos",DESENVOLVIMENTO);
    private Atualizavel atualizavel;
    private Context context;
    private String servico;
    private String[] parametros;
    private Method method;
    private ProgressDialog progressDialog;

    public GenericAsyncTask(Atualizavel atualizavel, Context context, Method verbo, String servico, String... parametros) {
        this.atualizavel = atualizavel;
        this.context = context;
        this.method = verbo;
        this.servico = servico;
        this.parametros = parametros;
    }

    // protected void onPreExecute(JSONObject jsonObject) {
    //    super.onPostExecute(jsonObject);
    // progressDialog = ProgressDialog.show(context, "Aguarde", "carregando...");
    // }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Aguarde", "carregando...");
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String resposta = null;
        try {
            switch (method) {
                case GET:
                    resposta = HttpConectionSingleton.gethttpConectionSingletonInstance().doGet(new URL(String.format("%s/%s", URL_WS, servico)));
                    if (resposta != null) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("array", new JSONArray(resposta));
                            return jsonObject;

                        } catch (JSONException je) {
                            return new JSONObject(resposta);
                        }
                    }
                    break;
                case POST:
                    resposta = HttpConectionSingleton.gethttpConectionSingletonInstance().doPost(new URL(String.format("%s/%s", URL_WS, servico)), parametros[0]);
                    if (resposta != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(resposta);
                           return jsonObject;

                        } catch (JSONException je) {
                            return new JSONObject();
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown Request.");
            }


        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("erro", String.format("Erro ao acessar %s do %s", method.toString(), servico.toString()));
                return jsonObject;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            atualizavel.atualizar(jsonObject);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
