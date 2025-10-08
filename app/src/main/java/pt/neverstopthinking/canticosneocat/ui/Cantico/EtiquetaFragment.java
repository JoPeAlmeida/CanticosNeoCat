package pt.neverstopthinking.canticosneocat.ui.Cantico;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoViewModel;


public class EtiquetaFragment extends DialogFragment {
    private CanticoViewModel canticoViewModel;
    private String oldEtiquetaNome;
    private EditText etiquetaNome;
    private FloatingActionButton saveEtiquetaBtn;
    private FloatingActionButton closeEtiquetaBtn;
    private String mode;

    public static EtiquetaFragment newInstance(final String mode, String etiquetaNome) {
        EtiquetaFragment etiquetaFragment = new EtiquetaFragment();
        etiquetaFragment.mode = mode;
        if (mode.equals("edit")) {
            etiquetaFragment.oldEtiquetaNome = etiquetaNome;
        }
        return etiquetaFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_etiqueta_cantico, container);
        etiquetaNome = v.findViewById(R.id.add_etiqueta_nome);
        if (mode.equals("edit")) {
            etiquetaNome.setText(oldEtiquetaNome);
        }
        saveEtiquetaBtn = v.findViewById(R.id.add_etiqueta_save);
        saveEtiquetaBtn.setOnClickListener(view -> {
            switch (mode) {
                case "edit":
                    canticoViewModel.updateEtiqueta(oldEtiquetaNome, etiquetaNome.getText().toString().trim());
                    break;
                case "add":
                    canticoViewModel.addEtiqueta(etiquetaNome.getText().toString().trim());
                    break;
                default: break;
            }
            closeFragment();
        });
        closeEtiquetaBtn = v.findViewById(R.id.add_etiqueta_close);
        closeEtiquetaBtn.setOnClickListener(view -> closeFragment());
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
        canticoViewModel = new ViewModelProvider(getActivity()).get(CanticoViewModel.class);
        etiquetaNome.requestFocus();
        etiquetaNome.postDelayed(() -> {
            InputMethodManager keyboard = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(etiquetaNome, 0);
        }, 200);
    }

    private void closeFragment() {
        this.dismiss();
    }
}
