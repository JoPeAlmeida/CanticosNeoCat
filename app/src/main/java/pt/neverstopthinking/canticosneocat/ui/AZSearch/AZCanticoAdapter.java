package pt.neverstopthinking.canticosneocat.ui.AZSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;

public class AZCanticoAdapter extends RecyclerView.Adapter<AZCanticoAdapter.CanticoHolder> implements Filterable {

    private List<Cantico> canticos = new ArrayList<>();
    private List<Cantico> canticosFull;
    private OnCanticoClickListener onCanticoClickListener;

    @NonNull
    @Override
    public CanticoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.az_item_layout, parent, false);
        return new CanticoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanticoHolder holder, int position) {
        Cantico cantico = canticos.get(position);
        holder.txtNome.setText(cantico.getNome());
        holder.txtTempoLiturgico.setText(cantico.getTempoLiturgico());
    }

    @Override
    public int getItemCount() {
        return canticos == null ? 0 : canticos.size();
    }

    @Override
    public Filter getFilter() {
        return canticosFilter;
    }

    private Filter canticosFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cantico> filteredCanticos = new ArrayList<>();

            if (canticosFull != null) {
                if (constraint == null || constraint.length() == 0) {
                    filteredCanticos.addAll(canticosFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Cantico cantico : canticosFull) {
                        String canticoNomeNormalized = Normalizer.normalize(cantico.getNome(), Normalizer.Form.NFD).replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+","");
                        if (canticoNomeNormalized.toLowerCase().contains(filterPattern)) {
                            filteredCanticos.add(cantico);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredCanticos;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            canticos.clear();
            canticos.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void updateCanticos(List<Cantico> canticos) {
        this.canticos = canticos;
        canticosFull = new ArrayList<>(canticos);
        notifyDataSetChanged();
    }

    public class CanticoHolder extends RecyclerView.ViewHolder {

        public TextView txtNome;
        public TextView txtTempoLiturgico;

        public CanticoHolder(View view) {
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
