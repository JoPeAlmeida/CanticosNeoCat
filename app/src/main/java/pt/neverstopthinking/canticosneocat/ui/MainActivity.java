package pt.neverstopthinking.canticosneocat.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.ui.AZSearch.AZActivity;
import pt.neverstopthinking.canticosneocat.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {


    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    public void openAZSearch(View view) {
        Intent intent = new Intent(this, AZActivity.class);
        startActivity(intent);
    }
}
