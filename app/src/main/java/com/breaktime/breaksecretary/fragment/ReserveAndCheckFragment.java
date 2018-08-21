package com.breaktime.breaksecretary.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.RevealTransition;
import com.breaktime.breaksecretary.activity.ShowingMapActivity;
import com.breaktime.breaksecretary.app.BreakScretApp;

import org.w3c.dom.Text;

import static com.breaktime.breaksecretary.activity.ShowingMapActivity.EXTRA_EPICENTER;


// In this case, the fragment displays simple text based on the page
public class ReserveAndCheckFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static ReserveAndCheckFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ReserveAndCheckFragment fragment = new ReserveAndCheckFragment();
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
        View view = inflater.inflate(R.layout.fragment_reserveandcheck, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final ImageView btn = (ImageView)view.findViewById(R.id.imgAlbum);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getContext(), ShowingMapActivity.class);
                //TODO : 남은 좌석 수 넘기기
                //startActivityForResult(intent, 1001);
                Intent intent = new Intent(getContext(), ShowingMapActivity.class);
                intent.putExtra("Sample", R.drawable.ic_test);
                int[] location = new int[2];
                view.findViewById(R.id.imgAlbum).getLocationInWindow(location);
                Point epicenter = new Point(location[0] + btn.getMeasuredWidth() / 2,
                        location[1] + btn.getMeasuredHeight() / 2);
                intent.putExtra(EXTRA_EPICENTER, epicenter);



                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        btn, btn.getTransitionName());
                startActivity(intent, options.toBundle());

            }
        });


        //============================
        // for test
        // TODO : 좌석 정보 받아오기
        int seatNumA = 12;
        int seatNumB = 42;
        int seatNumC = 32;
        int seatNumD = 39;

        LoadProgressStatus(view, seatNumA, seatNumB, seatNumC, seatNumD);


        //============================


        return view;
    }



    public void LoadProgressStatus(View view, int A, int B, int C, int D){
        ProgressBar pbA =(ProgressBar)view.findViewById(R.id.frag_reserve_progressbar_A);
        ProgressBar pbB =(ProgressBar)view.findViewById(R.id.frag_reserve_progressbar_B);
        ProgressBar pbC =(ProgressBar)view.findViewById(R.id.frag_reserve_progressbar_C);
        ProgressBar pbD =(ProgressBar)view.findViewById(R.id.frag_reserve_progressbar_D);

        pbA.setProgress(A);
        pbB.setProgress(B);
        pbC.setProgress(C);
        pbD.setProgress(D);

        TextView TpbA = (TextView)view.findViewById(R.id.frag_reserve_text_A);
        TextView TpbB = (TextView)view.findViewById(R.id.frag_reserve_text_B);
        TextView TpbC = (TextView)view.findViewById(R.id.frag_reserve_text_C);
        TextView TpbD = (TextView)view.findViewById(R.id.frag_reserve_text_D);

        TpbA.setText(String.valueOf(A));
        TpbB.setText(String.valueOf(B));
        TpbC.setText(String.valueOf(C));
        TpbD.setText(String.valueOf(D));


    }



}
