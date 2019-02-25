package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.Util.TimerManager;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.MyCallback;
import com.breaktime.breaksecretary.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.view.View.VISIBLE;


// In this case, the fragment displays simple text based on the page
public class MyStatusFragment extends Fragment {
    private static final String TAG = MyStatusFragment.class.getName();
    private View view;

    private FirebaseUtil mFirebaseUtil;
    private User mUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mystatus, container, false);

        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
