package com.nazycodes.tabiandating;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nazycodes.tabiandating.models.User;
import com.nazycodes.tabiandating.util.PreferenceKeys;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFirstLogin();
        init();
    }

    private void init(){
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home));
        transaction.addToBackStack(getString(R.string.tag_fragment_home));
        transaction.commit();
    }

    private void isFirstLogin(){
        Log.d(TAG, "isFirstLogin: checking if this is the first login.");

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);

        if(isFirstLogin){
            Log.d(TAG, "isFirstLogin: launching alert dialog");

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getString(R.string.first_time_user_message));
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "onClick: closing dialog");

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                    editor.commit();
                    dialog.dismiss();
                }
            });

            alertDialogBuilder.setIcon(R.drawable.tabian_dating);
            alertDialogBuilder.setTitle("");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }

    @Override
    public void inflateViewProfileFragment(User user) {
        ViewProfileFragment fragment = new ViewProfileFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_user), user);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_frame, fragment, getString(R.string.tag_fragment_view_profile));
        transaction.addToBackStack(getString(R.string.tag_fragment_view_profile));
        transaction.commit();
    }
}
