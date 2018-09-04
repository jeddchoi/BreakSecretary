package com.breaktime.breaksecretary.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breaktime.breaksecretary.R;


// In this case, the fragment displays simple text based on the page
public class ReserveAndCheckFragment extends Fragment {
    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserveandcheck, container, false);
        return view;
    }

}
