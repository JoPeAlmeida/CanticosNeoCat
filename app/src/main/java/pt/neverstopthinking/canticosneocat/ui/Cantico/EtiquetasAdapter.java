package pt.neverstopthinking.canticosneocat.ui.Cantico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;

public class EtiquetasAdapter extends RecyclerView.Adapter<EtiquetasAdapter.EtiquetaHolder> {

    private List<CanticoEtiquetaJoin> etiquetas = new ArrayList<>();
    private LongClickListener longClickListener;

    public EtiquetasAdapter(LongClickListener longClickListener) { this.longClickListener = longClickListener;}

    @NonNull
    @Override
    public EtiquetaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cantico_item_layout, parent, false);
        return new EtiquetaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtiquetaHolder holder, int position) {
        CanticoEtiquetaJoin etiqueta = etiquetas.get(position);
        holder.txtNome.setText(etiqueta.getEtiquetaNome());
    }

    @Override
    public int getItemCount() {
        return etiquetas == null ? 0 : etiquetas.size();
    }

    public void updateEtiquetas(List<CanticoEtiquetaJoin> etiquetas) {
        this.etiquetas = etiquetas;
        notifyDataSetChanged();
    }

    public CanticoEtiquetaJoin getEtiquetaAt(int posiiton) {
        return etiquetas.get(posiiton);
    }

    public class EtiquetaHolder extends RecyclerView.ViewHolder {
        public TextView txtNome;

        public EtiquetaHolder(View view) {
            super(view);
            txtNome = view.findViewById(R.id.cantico_item_etiqueta);
            view.setOnLongClickListener(view1 -> {
                longClickListener.showEditEtiquetaDialog(txtNome.getText().toString());
                return true;
            });
        }
    }

    public interface LongClickListener {
        void showEditEtiquetaDialog(String etiquetaNome);
    }
}
