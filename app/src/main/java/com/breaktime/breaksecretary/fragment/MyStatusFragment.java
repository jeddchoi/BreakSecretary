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

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


// In this case, the fragment displays simple text based on the page
public class MyStatusFragment extends Fragment {
    private static final String TAG = "SettingFragment";
    private View view;
    private FirebaseUtil myFireBase;
    private User mUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            myFireBase = ((MainActivity)getActivity()).myFireBase;
            mUser = ((MainActivity)getActivity()).mUser;
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mystatus, container, false);


        mUser.getLoginRef().child("status").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Integer status = dataSnapshot.getValue(Integer.class);
                if (status == null)
                    status = User.STATUS_NOTHING;
                switch( status ) {
                    case User.STATUS_NOTHING:

                       break;

                    case User.STATUS_SITTING:
                        break;

                    case User.STATUS_BEING_AWAY:
                        break;

                    case User.STATUS_RESERVING:
                        break;

                    case User.STATUS_PENALTY:
                        break;

                    case User.STATUS_BLOCKED:
                        break;

                    default:
                        Log.d(TAG, "Invalid status");
                        break;
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
