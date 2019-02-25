package com.breaktime.breaksecretary.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.User;


// In this case, the fragment displays simple text based on the page
public class QuickReserveFragment extends Fragment {
    private static final String TAG = QuickReserveFragment.class.getName();
    private View view;

    private FirebaseUtil mFirebaseUtil;
    private User mUser;
    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quickreserve, container, false);

        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;

        return view;
    }

}
