package pt.neverstopthinking.canticosneocat.ui.EtiquetasSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;

public class ECAdapter extends RecyclerView.Adapter<ECAdapter.ECHolder> {

    private List<Cantico> canticos;
    private Context context;

    private OnCanticoClickListener onCanticoClickListener;

    public ECAdapter(List<Cantico> canticos, Context context) {
        this.canticos = canticos;
        this.context = context;
    }

    @NonNull
    @Override
    public ECHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ECHolder(LayoutInflater.from(context).inflate(R.layout.az_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ECHolder holder, int position) {
        Cantico cantico = canticos.get(position);
        holder.txtNome.setText(cantico.getNome());
        holder.txtTempoLiturgico.setText(cantico.getTempoLiturgico());
    }

    @Override
    public int getItemCount() {
        return canticos == null ? 0 : canticos.size();
    }


    public class ECHolder extends RecyclerView.ViewHolder {
        public TextView txtNome;
        public TextView txtTempoLiturgico;

        public ECHolder(View view) {
            super(view);
            txtNome = view.findViewById(R.id.az_item_nome);
            txtTempoLiturgico = view.findViewById(R.id.az_item_tl);
            view.setOnClickListener(v -> onCanticoClickListener.launchCantico(canticos.get(getAdapterPosition()).getNome()));
        }
    }

    public interface OnCanticoClickListener {
        void launchCantico(String nome);
    }

    public void setOnCanticoClickListener(OnCanticoClickListener onCanticoClickListener) {
        this.onCanticoClickListener = onCanticoClickListener;
    }
}
