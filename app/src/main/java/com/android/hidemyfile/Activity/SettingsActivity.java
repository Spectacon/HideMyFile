package com.android.hidemyfile.Activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.hidemyfile.Dialog.DialogPasswordChange;
import com.android.hidemyfile.R;
import com.android.hidemyfile.Support.SharedPreferencesUtils.SharedPreferencesUtils;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    private SharedPreferencesUtils sharedPreferencesUtils;

    private View rootView;

    private Toolbar toolbar;

    private AppCompatCheckBox CHHideAfter;
    private AppCompatCheckBox CHScanHidden;

    private View VSecurityPwdChange;
    private View VSecurityReadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferencesUtils = new SharedPreferencesUtils(this);

        setUpView();
        setUpToolbar();
    }

    private void showDialogFileEncrypt() {
        DialogPasswordChange dialogPasswordChange = new DialogPasswordChange();
        dialogPasswordChange.setPasswordChangeCallbacks(new DialogPasswordChange.PasswordChangeCallbacks() {
            @Override
            public void onSuccess(String password) {
                Log.d(TAG, "onSuccess() -> Called");
                sharedPreferencesUtils.setKey4Password(password);
                showInfo(getString(R.string.dialog_password_change_success));
            }
        });
        dialogPasswordChange.show(getSupportFragmentManager(), "DialogPasswordChange");
    }

    private void showInfo(String info) {
        Snackbar.make(rootView, info, Snackbar.LENGTH_LONG).show();
    }

    private void setUpView() {
        rootView = findViewById(R.id.root_view);

        CHHideAfter = (AppCompatCheckBox) findViewById(R.id.setting_general_hide_after_action);
        CHHideAfter.setChecked(sharedPreferencesUtils.getHideAfterScan());
        CHHideAfter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferencesUtils.setHideAfterScan(isChecked);
            }
        });

        CHScanHidden = (AppCompatCheckBox) findViewById(R.id.setting_general_scan_hidden_action);
        CHScanHidden.setChecked(sharedPreferencesUtils.getScanHidden());
        CHScanHidden.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferencesUtils.setScanHidden(isChecked);
            }
        });

        VSecurityPwdChange = findViewById(R.id.setting_security_password_change);
        VSecurityPwdChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFileEncrypt();
            }
        });

        VSecurityReadMore = findViewById(R.id.setting_security_read_more);
        VSecurityReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "setting_security_read_more_action", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException ex) {
            Log.e(TAG, "setUpToolbar() -> ", ex);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
