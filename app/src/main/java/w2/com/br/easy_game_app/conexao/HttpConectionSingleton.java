package w2.com.br.easy_game_app.conexao;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by alexandre on 31/08/15.
 */
public class HttpConectionSingleton {

    private static final int JSON_CONNECTION_TIMEOUT = 300000;
    private static final int JSON_SOCKET_TIMEOUT = 5000;
    private static final String DEBUG_TAG = "STATUS_INTERNET";
    private static HttpConectionSingleton instance = new HttpConectionSingleton();

    public HttpConectionSingleton() {
    }

    public static HttpConectionSingleton gethttpConectionSingletonInstance() {
        if (instance == null) {
            instance = new HttpConectionSingleton();
        }
        return instance;
    }

    public String doGet(URL url) throws IOException {

        InputStream is = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(JSON_CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "Resposta" + response);

            is = conn.getInputStream();

            return readString(is);
        } catch (ProtocolException e) {
        }
        return is.toString();
    }
    public String doPost(URL url, String params) throws IOException, JSONException {
        InputStream is = null;
        JSONObject retorno = new JSONObject().put("retorno","erro");
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(JSON_CONNECTION_TIMEOUT);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");

            conn.connect();

            //setup send
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(params.getBytes());
            //clean up
            os.flush();


            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "Resposta" + response);

            is = conn.getInputStream();

            return readString(is);

        } catch (ProtocolException e) {
        }
        return retorno.toString();
    }

    private byte[] readBytes(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }

            byte[] bytes = bos.toByteArray();
            return bytes;
        } finally {
            bos.close();
        }
    }

    private String readString(InputStream in) throws IOException {
        byte[] bytes = readBytes(in);
        String texto = new String(bytes);
        return texto;
    }

}
