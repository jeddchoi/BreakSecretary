package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.app.BreakSecretary;
import com.breaktime.breaksecretary.model.TestUser;


// In this case, the fragment displays simple text based on the page
public class MyStatusFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();

    }

    private int mPage;
    // TODO : state 정리
    private static final int EMPTY = 0;
    private static final int RESERVING = 2;
    private static final int USING = 1;
    public static MyStatusFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MyStatusFragment fragment = new MyStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 1) empty 2) 예약  3) 이용중  by User status ---
        BreakSecretary.Log("MyStatusFrag Called");


        return LoadMatchingView(inflater, container);


    }


    // onSavedInstance 로부터 activity의 데이터를 저장할 수 있다.
    private View LoadMatchingView(LayoutInflater inflater, ViewGroup container){
        int state = TestUser.getStatus();
        View view;
        switch(state){

            case EMPTY :
                view = inflater.inflate(R.layout.fragment_mystatus_empty, container, false);
                break;

            case RESERVING :
                view = inflater.inflate(R.layout.fragment_mystatus_reserving, container, false);
                TextView tvTitle2 = (TextView) view.findViewById(R.id.tvTitle2);
                tvTitle2.setText("예약중.");
                Button btn = (Button)view.findViewById(R.id.trigger);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TestUser.setStatus(1);
                        activity.onMyStatusFragmentChange();
                    }
                });
                break;

            case USING :
                view = inflater.inflate(R.layout.fragment_mystatus_using, container, false);
                TextView tvTitle3 = (TextView) view.findViewById(R.id.tvTitle3);
                tvTitle3.setText("사용중.");

                break;

                default:
                    view = inflater.inflate(R.layout.fragment_mystatus_empty, container, false);
                    break;

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Tee", "EEE");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BreakSecretary.Log("awefawef");


    }
}
