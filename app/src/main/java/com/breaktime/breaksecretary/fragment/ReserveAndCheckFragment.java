package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.adapter.GridAdapter;
import com.breaktime.breaksecretary.model.Section;
import com.breaktime.breaksecretary.model.SectionItem;
import com.breaktime.breaksecretary.model.User;

import java.util.ArrayList;


// In this case, the fragment displays simple text based on the page
public class ReserveAndCheckFragment extends Fragment {
    private static final String TAG = ReserveAndCheckFragment.class.getName();
    private View view;

    private FirebaseUtil mFirebaseUtil;
    private User mUser;

    private GridView gridView;
    private GridAdapter adapter;
    private ArrayList<Section> sectionItems;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        view = inflater.inflate(R.layout.fragment_reserveandcheck, container, false);
        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;

        GridView gridView = (GridView) view.findViewById(R.id.grid_section_info);
        sectionItems = new ArrayList<>();
        adapter = new GridAdapter(((MainActivity)getActivity()).getApplicationContext(), sectionItems);
        gridView.setAdapter(adapter);

        sectionItems.add(new Section("A", "0", "50"));
        sectionItems.add(new Section("B", "0", "50"));
        sectionItems.add(new Section("C", "0", "50"));
        sectionItems.add(new Section("D", "0", "50"));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(((MainActivity)getActivity()), ((SectionItem)view).getTotalNum(), Toast.LENGTH_LONG).show();
            }
        });

        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            // Restore last state for checked position.

        }

        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;

    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
    }
}
