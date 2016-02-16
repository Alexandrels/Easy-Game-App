package w2.com.br.easy_game_app.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexandre on 26/09/15.
 */
public class CoordenadasService extends Service implements LocationListener {

    private final Context context;
    double latitude;
    double longitude;
    //pode guardar altitude, latitude e logitude entre outras
    Location location;
    boolean gpsAtivo;
    TextView coordenadas;
    LocationManager locationManager;
    private JSONObject resultado;

    public CoordenadasService() {
        super();
        this.context = this.getApplicationContext();
    }

    public CoordenadasService(Context c) {
        super();
        this.context = c;
        getLocalizacao();
    }

    public void setView(View v) {
        coordenadas = (TextView) v;
        JSONObject latLon = new JSONObject();
        try {
            latLon.put("latitude",latitude);
            latLon.put("longitude",longitude);
            coordenadas.setText(latLon.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //no exemplo do cara eu enviava aqui o resultado da tela
//        coordenadas.setText(String.format("Coordenadas capturadas: Latitude %f - Longitude %f", latitude, longitude));
    }
    //criei para retornar o resultado
    public JSONObject getCoordenadas(){
        try {
            JSONObject latLon = new JSONObject();
            latLon.put("latitude",latitude);
            latLon.put("longitude",longitude);
            resultado = latLon;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public JSONObject getLocalizacao() {
        try {
            locationManager = (LocationManager) this.context.getSystemService(LOCATION_SERVICE);
            gpsAtivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
        }

        if (gpsAtivo) {
            try{
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER
                        , 1000 * 60
                        , 10
                        , this);

                location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                JSONObject latLon = new JSONObject();
                latLon.put("latitude",latitude);
                latLon.put("longitude",longitude);
                resultado = latLon;
                return  resultado;
            }catch (SecurityException s){
                Log.d("ERRO SECURITY",s.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return  resultado;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
