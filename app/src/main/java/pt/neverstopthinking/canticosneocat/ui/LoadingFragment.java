package pt.neverstopthinking.canticosneocat.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import pt.neverstopthinking.canticosneocat.R;

public class LoadingFragment extends DialogFragment {

    ProgressBar progressBar;
    TextView progressText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container);
        progressBar = view.findViewById(R.id.loading_progress_bar);
        progressBar.setMax(100);
        progressText = view.findViewById(R.id.loading_progress_text);
        return view;
    }

    public void setProgress(int i) {
        progressBar.setProgress(i, true);
        progressText.setText(new StringBuilder().append(i).append("/100").toString());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            //dialog.setContentView(R.layout.fragment_loading);
            Window window = getDialog().getWindow();
            Point size = new Point();
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);
            window.setLayout((int) (size.x*0.75), WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.setTitle("Loading..");
        }
    }
}
