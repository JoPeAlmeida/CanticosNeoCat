package pt.neverstopthinking.canticosneocat.ui.EtiquetasSearch;

import android.app.Activity;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.ui.AZSearch.AZCanticoAdapter;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoListViewModel;

public class EtiquetasAdapter extends RecyclerView.Adapter<EtiquetasAdapter.EtiquetaHolder> implements Filterable {

    private List<CanticoEtiquetaJoin.EtiquetaCanticos> etiquetaCanticosList = new ArrayList<>();
    private List<CanticoEtiquetaJoin.EtiquetaCanticos> etiquetaCanticosListFull;
    private OnEtiquetaClickListener onEtiquetaClickListener;
    private AZCanticoAdapter.OnCanticoClickListener onCanticoClickListener;
    private RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private final AppCompatActivity activity;

    public EtiquetasAdapter(AppCompatActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public EtiquetasAdapter.EtiquetaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.etiquetas_mainitem_layout, parent, false);
        return new EtiquetaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtiquetasAdapter.EtiquetaHolder holder, int position) {
        CanticoEtiquetaJoin.EtiquetaCanticos etiquetaCanticos = etiquetaCanticosList.get(position);
        holder.txtNome.setText(etiquetaCanticos.getEtiqueta().getNome());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        AZCanticoAdapter azCanticoAdapter = new AZCanticoAdapter();
        holder.recyclerView.setAdapter(azCanticoAdapter);
        azCanticoAdapter.setOnCanticoClickListener(onCanticoClickListener);
        CanticoListViewModel canticoListViewModel = ViewModelProviders.of(activity).get(CanticoListViewModel.class);
        canticoListViewModel.getCanticosOfEtiqueta(etiquetaCanticos.getEtiqueta().getNome()).observe(activity, azCanticoAdapter::updateCanticos);
    }

    @Override
    public int getItemCount() {
        return etiquetaCanticosList == null ? 0 : etiquetaCanticosList.size();
    }

    public void updateEtiquetas(List<CanticoEtiquetaJoin.EtiquetaCanticos> etiquetaCanticosList) {
        this.etiquetaCanticosList = etiquetaCanticosList;
        etiquetaCanticosListFull = new ArrayList<>(etiquetaCanticosList);
        notifyDataSetChanged();
    }

    public class EtiquetaHolder extends RecyclerView.ViewHolder {

        public TextView txtNome;
        public RecyclerView recyclerView;

        public EtiquetaHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.etiquetas_mainitem_nome);
            recyclerView = itemView.findViewById(R.id.etiquetas_recycler);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (onEtiquetaClickListener != null) {
                    onEtiquetaClickListener.toggleChildRecyclerView(position);
                }
            });
        }
    }

    public interface OnEtiquetaClickListener {
        void toggleChildRecyclerView(int position);
    }

    public void setOnEtiquetaClickListener(OnEtiquetaClickListener onEtiquetaClickListener) {
        this.onEtiquetaClickListener = onEtiquetaClickListener;
    }

    public void setOnCanticoClickListener(AZCanticoAdapter.OnCanticoClickListener onCanticoClickListener) {
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
            etiquetaCanticosList.clear();
            etiquetaCanticosList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
