package pt.neverstopthinking.canticosneocat.ui.Cantico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

public class EtiquetasAdapter extends ListAdapter<CanticoEtiquetaJoin, EtiquetasAdapter.EtiquetaHolder> {

    private LongClickListener longClickListener;

    protected EtiquetasAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<CanticoEtiquetaJoin> DIFF_CALLBACK = new DiffUtil.ItemCallback<CanticoEtiquetaJoin>() {
        @Override
        public boolean areItemsTheSame(@NonNull CanticoEtiquetaJoin oldItem, @NonNull CanticoEtiquetaJoin newItem) {
            return oldItem.getCanticoNome().equals(newItem.getCanticoNome()) &&
                    oldItem.getEtiquetaNome().equals(newItem.getEtiquetaNome());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CanticoEtiquetaJoin oldItem, @NonNull CanticoEtiquetaJoin newItem) {
            return oldItem.getCanticoNome().equals(newItem.getCanticoNome()) &&
                    oldItem.getEtiquetaNome().equals(newItem.getEtiquetaNome());
        }
    };

    @NonNull
    @Override
    public EtiquetaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cantico_item_layout, parent, false);
        return new EtiquetaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtiquetaHolder holder, int position) {
        CanticoEtiquetaJoin etiqueta = getItem(position);
        holder.txtNome.setText(etiqueta.getEtiquetaNome());
    }

    public CanticoEtiquetaJoin getEtiquetaAt(int posiiton) {
        return getItem(posiiton);
    }

    public class EtiquetaHolder extends RecyclerView.ViewHolder {
        public TextView txtNome;

        public EtiquetaHolder(View view) {
            super(view);
            txtNome = view.findViewById(R.id.cantico_item_etiqueta);
            view.setOnLongClickListener(view1 -> {
                int position = getAdapterPosition();
                if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                    longClickListener.showEditEtiquetaDialog(getItem(position).getEtiquetaNome());
                    return true;
                }
                return false;
            });
        }
    }

    public interface LongClickListener {
        void showEditEtiquetaDialog(String etiquetaNome);
    }

    public void setLongClickListener(LongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
