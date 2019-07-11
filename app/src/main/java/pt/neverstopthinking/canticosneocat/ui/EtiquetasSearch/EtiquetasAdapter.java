package pt.neverstopthinking.canticosneocat.ui.EtiquetasSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;

public class EtiquetasAdapter extends ListAdapter<CanticoEtiquetaJoin.EtiquetaCanticos, EtiquetasAdapter.EtiquetaHolder> implements Filterable {

    private Context context;
    private List<CanticoEtiquetaJoin.EtiquetaCanticos> etiquetaCanticosListFull;
    private ECAdapter ecAdapter;
    private OnEtiquetaClickListener onEtiquetaClickListener;
    private ECAdapter.OnCanticoClickListener onCanticoClickListener;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public EtiquetasAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    private static final DiffUtil.ItemCallback<CanticoEtiquetaJoin.EtiquetaCanticos> DIFF_CALLBACK = new DiffUtil.ItemCallback<CanticoEtiquetaJoin.EtiquetaCanticos>() {
        @Override
        public boolean areItemsTheSame(@NonNull CanticoEtiquetaJoin.EtiquetaCanticos oldItem, @NonNull CanticoEtiquetaJoin.EtiquetaCanticos newItem) {
            return oldItem.getEtiqueta().equals(newItem.getEtiqueta());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CanticoEtiquetaJoin.EtiquetaCanticos oldItem, @NonNull CanticoEtiquetaJoin.EtiquetaCanticos newItem) {
            return oldItem.getCanticos().equals(newItem.getCanticos());
        }
    };

    @NonNull
    @Override
    public EtiquetasAdapter.EtiquetaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EtiquetaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.etiquetas_mainitem_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EtiquetasAdapter.EtiquetaHolder holder, int position) {
        CanticoEtiquetaJoin.EtiquetaCanticos etiquetaCanticos = getItem(position);
        holder.txtNome.setText(etiquetaCanticos.getEtiqueta().getNome());
        ecAdapter = new ECAdapter(etiquetaCanticos.getCanticos(), context);
        ecAdapter.setOnCanticoClickListener(onCanticoClickListener);
        holder.recyclerView.setAdapter(ecAdapter);
        holder.recyclerView.setRecycledViewPool(recycledViewPool);
        boolean expanded = etiquetaCanticos.isExpanded();
        holder.recyclerView.setVisibility(expanded ? View.VISIBLE : View.GONE);
    }

    public void updateEtiquetas(List<CanticoEtiquetaJoin.EtiquetaCanticos> etiquetaCanticosList) {
        submitList(etiquetaCanticosList);
        etiquetaCanticosListFull = new ArrayList<>(etiquetaCanticosList);
        notifyDataSetChanged();
    }

    public class EtiquetaHolder extends RecyclerView.ViewHolder {

        public TextView txtNome;
        public RecyclerView recyclerView;
        private LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);

        public EtiquetaHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.etiquetas_mainitem_nome);
            recyclerView = itemView.findViewById(R.id.etiquetas_recycler);
            recyclerView.setLayoutManager(layoutManager);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();

                CanticoEtiquetaJoin.EtiquetaCanticos etiquetaCanticos = getItem(position);
                boolean expanded = etiquetaCanticos.isExpanded();
                etiquetaCanticos.setExpanded(!expanded);
                notifyItemChanged(position);
                if (onEtiquetaClickListener != null) {
                    onEtiquetaClickListener.scrollToPosiion(position);
                }
            });
        }
    }

    public interface OnEtiquetaClickListener {
        void scrollToPosiion(int position);
    }

    public void setOnEtiquetaClickListener(OnEtiquetaClickListener onEtiquetaClickListener) {
        this.onEtiquetaClickListener = onEtiquetaClickListener;
    }

    public void setOnCanticoClickListener(ECAdapter.OnCanticoClickListener onCanticoClickListener) {
        this.onCanticoClickListener = onCanticoClickListener;
    }

    @Override
    public Filter getFilter() {
        return etiquetasFilter;
    }

    private Filter etiquetasFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CanticoEtiquetaJoin.EtiquetaCanticos> filteredEtiquetaCanticos = new ArrayList<>();

            if (etiquetaCanticosListFull != null) {
                if (constraint == null || constraint.length() == 0) {
                    filteredEtiquetaCanticos.addAll(etiquetaCanticosListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CanticoEtiquetaJoin.EtiquetaCanticos etiquetaCanticos : etiquetaCanticosListFull) {
                        String canticoNomeNormalized = Normalizer.normalize(etiquetaCanticos.getEtiqueta().getNome(), Normalizer.Form.NFD).replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+","");
                        if (canticoNomeNormalized.toLowerCase().contains(filterPattern)) {
                            filteredEtiquetaCanticos.add(etiquetaCanticos);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredEtiquetaCanticos;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            //etiquetaCanticosList.clear();
            //etiquetaCanticosList.addAll((List) filterResults.values);
            submitList((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
