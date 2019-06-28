package pt.neverstopthinking.canticosneocat.ui.Cantico;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoViewModel;


public class AddEtiquetaFragment extends DialogFragment {
    private CanticoViewModel canticoViewModel;
    private EditText etiquetaNome;
    private FloatingActionButton saveEtiquetaBtn;
    private FloatingActionButton closeEtiquetaBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_etiqueta_cantico, container);
        etiquetaNome = v.findViewById(R.id.add_etiqueta_nome);
        saveEtiquetaBtn = v.findViewById(R.id.add_etiqueta_save);
        saveEtiquetaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canticoViewModel.addEtiqueta(etiquetaNome.getText().toString().trim());
                closeFragment();
            }
        });
        closeEtiquetaBtn = v.findViewById(R.id.add_etiqueta_close);
        closeEtiquetaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        canticoViewModel = ViewModelProviders.of(getActivity()).get(CanticoViewModel.class);
    }

    private void closeFragment() {
        this.dismiss();
    }
}
