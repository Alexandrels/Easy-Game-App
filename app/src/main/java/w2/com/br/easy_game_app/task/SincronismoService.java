package w2.com.br.easy_game_app.task;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

import w2.com.br.easy_game_app.async.GenericAsyncTask;
import w2.com.br.easy_game_app.enuns.Method;
import w2.com.br.easy_game_app.ui.NotificacoesUI;



/**
 * Created by alexandre on 13/03/16.
 */
public class SincronismoService extends Service {
    private final String servicoUsuarioEquipe = "usuarioEquipe";
    private Long idUsuario = 4l;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        cancelarProximaExecucao();
        proximaExecucao();
        NotificacaoConvite notificacaoConvite = new NotificacaoConvite(this);
        // aqui eu chamo o async para atualizar meu banco ou qualquer outra coisa
//        new GenericAsyncTask(notificacaoConvite,this, Method.GET,"equipe").execute();
        new GenericAsyncTask(notificacaoConvite, this, Method.GET, String.format("%s/%d", servicoUsuarioEquipe, idUsuario)).execute();

        return START_STICKY;
    }

    private synchronized void proximaExecucao(){
        Intent locatorService = new Intent(this, SincronismoService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, locatorService, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        stopService(locatorService);



    }

    private synchronized void cancelarProximaExecucao(){
        Intent myIntent = new Intent(this, SincronismoService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
}
