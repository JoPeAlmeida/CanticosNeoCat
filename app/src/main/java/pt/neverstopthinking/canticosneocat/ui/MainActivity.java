package pt.neverstopthinking.canticosneocat.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.ui.AZSearch.AZActivity;
import pt.neverstopthinking.canticosneocat.ui.EtiquetasSearch.EtiquetasActivity;
import pt.neverstopthinking.canticosneocat.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firstStart", true);
        if (firstStart) new LoadingAsyncTask().execute();;
    }

    public void openAZSearch(View view) {
        Intent intent = new Intent(this, AZActivity.class);
        startActivity(intent);
    }

    public void openEtiquetasSearch(View view) {
        startActivity(new Intent(this, EtiquetasActivity.class));
    }

    private class LoadingAsyncTask extends AsyncTask<Void, Integer, Void> {
        LoadingFragment loadingFragment;

        @Override
        protected void onPreExecute() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            loadingFragment = new LoadingFragment();
            loadingFragment.setCancelable(false);
            loadingFragment.show(fragmentManager, null);
        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = 0; i <= 100; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);
            loadingFragment.progressBar.setProgress(values[0], true);
            loadingFragment.progressText.setText(new StringBuilder().append(values[0]).append("/100").toString());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loadingFragment.dismiss();
            SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
        }
    }
}
