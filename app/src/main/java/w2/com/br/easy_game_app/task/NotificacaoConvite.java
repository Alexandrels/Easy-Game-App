package w2.com.br.easy_game_app.task;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import w2.com.br.easy_game_app.MenuInicialActivity;
import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.entity.Atualizavel;
import w2.com.br.easy_game_app.ui.NotificacoesUI;

/**
 * Created by alexandre on 13/03/16.
 */
public class NotificacaoConvite implements Atualizavel{

    private Context context;

    public NotificacaoConvite (Context context){

        this.context = context;
    }

    @Override
    public void atualizar(JSONObject jsonObject) throws JSONException {
        JSONObject retorno = null;
        if (jsonObject.has("objeto")) {
            retorno = new JSONObject(jsonObject.getString("objeto")) ;
            try {
                if(retorno.has("array")){
                    JSONArray array = retorno.getJSONArray("array");
                    //criarNotificacaoMaterialMerchandising();
                    if(array.length() > 0){
                        createNotification(context);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(jsonObject.has("erro")) {
            //TODO Toast;
            Toast.makeText(context, retorno.get("erro").toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void createNotification(Context view) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(context, NotificacoesUI.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Há times interessados em você")
                .setContentText("Convites pendentes")
                .setSmallIcon(R.drawable.jogador_linha)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pIntent)
                .addAction(R.drawable.jogador_linha, "Ir para notificações...", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }
}
