package w2.com.br.easy_game_app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import w2.com.br.easy_game_app.R;
import w2.com.br.easy_game_app.entity.Evento;
import w2.com.br.easy_game_app.entity.UsuarioEquipe;
import w2.com.br.easy_game_app.util.DataUtils;

/**
 * Created by alexandre on 05/09/15.
 */
public class ListAdapterConvitePendente extends BaseAdapter {


    private LayoutInflater mInflater;
    private List<UsuarioEquipe> itens;

    public ListAdapterConvitePendente(Context context, List<UsuarioEquipe> itens) {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount() {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public UsuarioEquipe getItem(int position) {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        UsuarioEquipe item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item_listview_convite_pendente, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.nome_time_convite)).setText(item.getEquipe().getNome());
        ((TextView) view.findViewById(R.id.posicao_convite)).setText(item.getRetornaPosicao());
        ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(R.drawable.ic_convite);

        return view;
    }
}
