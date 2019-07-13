package com.nazycodes.tabiandating;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nazycodes.tabiandating.models.User;
import com.nazycodes.tabiandating.util.Users;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    //constants
    private static final int NUM_COLUMNS = 2;

    //widgets
    private RecyclerView mRecyclerView;

    //vars
    private ArrayList<User> mMatches = new ArrayList<>();
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private MainRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(TAG, "onCreateView: started.");
        mRecyclerView = view.findViewById(R.id.recycler_view);

        findMatches();
        return view;
    }

    private void findMatches() {
        Users users = new Users();
        if(mMatches != null){
            mMatches.clear();
        }

        for(User user : users.USERS){
            mMatches.add(user);
        }

        if(mRecyclerViewAdapter == null){
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerView");
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), mMatches);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
