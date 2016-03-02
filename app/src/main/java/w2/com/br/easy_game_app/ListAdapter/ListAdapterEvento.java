package w2.com.br.easy_game_app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.entity.Evento;

/**
 * Created by alexandre on 05/09/15.
 */
public class ListAdapterEvento extends ArrayAdapter<Evento>{


    public ListAdapterEvento(Context context, int resource, List<Evento> listEventos) {
        super(context, resource,listEventos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_row_evento, null);

        }
        Evento p = getItem(position);
        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.rowJogadorId);
            TextView tt2 = (TextView) v.findViewById(R.id.rowJogadorNome);
            TextView tt3 = (TextView) v.findViewById(R.id.rowJogadorPosicao);

            if (tt1 != null) {
                tt1.setText(p.getId().toString());
            }
            if (tt2 != null) {
                tt2.setText(p.getDescricao().toString());
            }
            if (tt3 != null) {
                tt3.setText(p.getLocal().getNomeLocal().toString());
            }
        }
        return v;
    }
}
