package com.example.onexero.customertimesproject.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.onexero.customertimesproject.CustomApplicaton;
import com.example.onexero.customertimesproject.R;
import com.example.onexero.customertimesproject.adapter.CustomAdapter;
import com.example.onexero.customertimesproject.di.component.DaggerActivityComponent;
import com.example.onexero.customertimesproject.di.module.ActivityModule;
import com.example.onexero.customertimesproject.presenter.Presenter;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements IView {

    @Inject
    Presenter mPresenter;
    private CustomAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        checkPermission();
        DaggerActivityComponent.builder().
                applicationComponent(CustomApplicaton.get(this).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);

        mPresenter.getDescribe();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void setAdapterCursor(Cursor cursor) {
        adapter = new CustomAdapter(this,cursor);
        recyclerView.setAdapter(adapter);
    }

    private void checkPermission() {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (!hasPermissions(this, permissions)) {
            String[] str = new String[permissions.size()];
            str = permissions.toArray(str);
            ActivityCompat.requestPermissions(this, str, 10);
        }
    }

    public static Boolean hasPermissions(Context context, ArrayList<String> permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
