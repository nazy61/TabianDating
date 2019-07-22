package com.nazycodes.tabiandating;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nazycodes.tabiandating.util.Resources;

public class AgreementFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AgreementFragment";

    //constants

    //widgets
    private TextView mFragmentHeading;
    private RelativeLayout mBackArrow;

    //vars
    private IMainActivity mInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agreement, container, false);
        Log.d(TAG, "onCreateView: started");
        mBackArrow = view.findViewById(R.id.back_arrow);
        mFragmentHeading = view.findViewById(R.id.fragment_heading);

        initToolbar();
        setBackgroundImage(view);
        mBackArrow.setOnClickListener(this);

        return view;
    }

    public void setBackgroundImage(View view){
        ImageView backgroundView = view.findViewById(R.id.background);
        Glide.with(getActivity()).load(Resources.BACKGROUND_HEARTS).into(backgroundView);
    }

    private void initToolbar(){
        Log.d(TAG, "initToolbar: initializing toolbar");
        mBackArrow.setOnClickListener(this);
        mFragmentHeading.setText(getString(R.string.tag_fragment_agreement));
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: clicked");

        if(view.getId() == R.id.back_arrow){
            Log.d(TAG, "onClick: navigating back");
            mInterface.onBackPressed();

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity) getActivity();
    }
}
