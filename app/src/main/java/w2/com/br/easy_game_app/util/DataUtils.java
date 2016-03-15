package w2.com.br.easy_game_app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alexandre on 10/03/16.
 */
public class DataUtils {

    private Locale locale;

    public static synchronized Date parseDate(String data, String pattern) {
        if (data == null) {
            return null;
        }
        try {
            dateFormat.applyPattern(pattern);
            return dateFormat.parse(data);
        } catch (Exception e) {
            return null;
        }
    }
    public static synchronized String formatarDate(Date data, String pattern) {
        if (data == null) {
            return null;
        }
        dateFormat.applyPattern(pattern);
        return dateFormat.format(data);
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


}
