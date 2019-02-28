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
import android.widget.EditText;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.MyCallback;
import com.breaktime.breaksecretary.model.User;


// In this case, the fragment displays simple text based on the page
public class TimeLineFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = TimeLineFragment.class.getName();
    private View view;

    private FirebaseUtil mFirebaseUtil;
    private User mUser;

    private EditText et_num_section, et_num_seat;

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
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;

        et_num_section = view.findViewById(R.id.et_num_section);
        et_num_seat = view.findViewById(R.id.et_num_seat);
        view.findViewById(R.id.btn_reserve).setOnClickListener(this);
        view.findViewById(R.id.btn_fast_reserve).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel_reservation).setOnClickListener(this);
        view.findViewById(R.id.btn_occupy).setOnClickListener(this);
        view.findViewById(R.id.btn_stop).setOnClickListener(this);
        view.findViewById(R.id.btn_step_out).setOnClickListener(this);
        view.findViewById(R.id.btn_return_to_seat).setOnClickListener(this);
        view.findViewById(R.id.btn_get_penalty).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel_penalty).setOnClickListener(this);
        view.findViewById(R.id.btn_get_block).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel_block).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reserve:

                if (et_num_seat.getText().toString().length() == 0 || et_num_section.getText().toString().length() == 0) {
                    ((MainActivity)getActivity()).show_toast_msg("You should fill out number first!", true);
                    return;
                }
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {
                            case ONLINE:
                            case SUBSCRIBING:
                                mUser.user_reserve(Integer.parseInt(et_num_section.getText().toString()), Integer.parseInt(et_num_seat.getText().toString()));
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] reserved on " + et_num_seat.getText().toString() + " seat of " + et_num_section.getText().toString() + " section.", true);
                                break;

                            case RESERVING:
                            case OCCUPYING:
                            case STEPPING_OUT:
                            case PAYING_PENALTY:
                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't reserve on" + et_num_seat.getText().toString() + " seat of " + et_num_section.getText().toString() + " section.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });

                break;

            case R.id.btn_fast_reserve:
                break;

            case R.id.btn_cancel_reservation:
                if (et_num_seat.getText().toString().length() == 0 || et_num_section.getText().toString().length() == 0) {
                    ((MainActivity)getActivity()).show_toast_msg("You should fill out number first!", false);
                    return;
                }
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {
                            case RESERVING:
                                mUser.user_cancel_reservation(Integer.parseInt(et_num_section.getText().toString()), Integer.parseInt(et_num_seat.getText().toString()));
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You canceled " + et_num_seat.getText().toString() + " seat of " + et_num_section.getText().toString() + " section.", false);
                                break;

                            case ONLINE:
                            case SUBSCRIBING:
                            case OCCUPYING:
                            case STEPPING_OUT:
                            case PAYING_PENALTY:
                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't cancel reservation on " + et_num_seat.getText().toString() + " seat of " + et_num_section.getText().toString() + " section.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });
                break;

            case R.id.btn_occupy:
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {
                            case RESERVING:
                                mUser.user_occupy();
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You started to use " + et_num_seat.getText().toString() + " seat of " + et_num_section.getText().toString() + " section.", false);
                                break;

                            case ONLINE:
                            case SUBSCRIBING:
                            case OCCUPYING:
                            case STEPPING_OUT:
                            case PAYING_PENALTY:
                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't start to use " + et_num_seat.getText().toString() + " seat of " + et_num_section.getText().toString() + " section.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });

                break;

            case R.id.btn_stop:
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {
                            case OCCUPYING:
                            case STEPPING_OUT:
                                mUser.user_stop();
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You stopped.", false);
                                break;


                            case ONLINE:
                            case SUBSCRIBING:
                            case RESERVING:
                            case PAYING_PENALTY:
                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't stop.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });
                break;

            case R.id.btn_step_out:
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {
                            case OCCUPYING:
                                mUser.user_step_out();
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You stepped out.", false);
                                break;


                            case ONLINE:
                            case SUBSCRIBING:
                            case RESERVING:
                            case STEPPING_OUT:
                            case PAYING_PENALTY:
                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't step out.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });
                break;

            case R.id.btn_return_to_seat:
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {

                            case STEPPING_OUT:
                                mUser.user_return_to_seat();
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You returned to seat.", false);
                                break;


                            case ONLINE:
                            case SUBSCRIBING:
                            case RESERVING:
                            case OCCUPYING:
                            case PAYING_PENALTY:
                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't return to seat.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });
                break;

            case R.id.btn_get_penalty:
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {
                            case RESERVING:
                                mUser.user_get_penalty();
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You got penalty.", false);
                                break;


                            case ONLINE:
                            case SUBSCRIBING:
                            case OCCUPYING:
                            case STEPPING_OUT:
                            case PAYING_PENALTY:
                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't get penalty.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });
                break;

            case R.id.btn_cancel_penalty:
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {

                            case PAYING_PENALTY:
                                mUser.user_cancel_penalty();
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You ended penalty.", false);
                                break;


                            case ONLINE:
                            case SUBSCRIBING:
                            case RESERVING:
                            case OCCUPYING:
                            case STEPPING_OUT:
                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't end penalty.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });
                break;

            case R.id.btn_get_block:
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {
                            case ONLINE:
                            case SUBSCRIBING:
                            case RESERVING:
                            case PAYING_PENALTY:
                            case OCCUPYING:
                            case STEPPING_OUT:
                                mUser.user_get_block();
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You got blocked.", false);
                                break;


                            case BEING_BLOCKED:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't get blocked.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });

                break;

            case R.id.btn_cancel_block:
                mUser.getStatusForSingleEvent(new MyCallback<User.Status_user>() {
                    @Override
                    public void onCallback(User.Status_user value) {
                        switch (value) {
                            case BEING_BLOCKED:
                                mUser.user_cancel_block();
                                ((MainActivity)getActivity()).show_snackbar_msg("[SUCC] You are free.", false);
                                break;

                            case ONLINE:
                            case SUBSCRIBING:
                            case RESERVING:
                            case PAYING_PENALTY:
                            case OCCUPYING:
                            case STEPPING_OUT:
                                ((MainActivity)getActivity()).show_snackbar_msg("[FAIL] Can't be free.", true);
                                break;

                            default:
                                Log.e(TAG, "undefined user status");
                        }
                    }
                });
                break;

            default:
                break;
        }
    }
}
