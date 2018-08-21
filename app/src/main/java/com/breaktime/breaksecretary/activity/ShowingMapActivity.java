package com.breaktime.breaksecretary.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.breaktime.breaksecretary.CustomDialog;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.RevealTransition;

import java.util.HashMap;

import pl.polidea.view.ZoomView;

public class ShowingMapActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomDialog mCustomDialog;
    private Button[] btnList = new Button[200];
    private String selectedSeatNum;
    public static String EXTRA_EPICENTER = "EXTRA_EPICENTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_map);



        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.showing_map_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(3000, 3000);
        ZoomViewLoad(v, layoutParams);

        Intent intent = getIntent();
        int img = intent.getIntExtra("sample",0);
        ImageView imgV = (ImageView)findViewById(R.id.imgAlbumArt);
        imgV.setImageResource(img);
        initTransitions();

        LoadButton(v);
        for(int i = 0;i<7;i++){
            btnList[i].setOnClickListener(this);
        }

    }


    public void ZoomViewLoad(View v, LinearLayout.LayoutParams layoutParams){
        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("Mini Map Test"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        zoomView.setMiniMapHeight(400);

        zoomView.zoomTo(3f,500f,500f);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(zoomView);
    }

    public void LoadButton(View v){
        btnList[0] = (Button)v.findViewById(R.id.a00);
        btnList[0].setTag("a00");
        btnList[1] = (Button)v.findViewById(R.id.a01);
        btnList[1].setTag("a01");
        btnList[2] = (Button)v.findViewById(R.id.a02);
        btnList[2].setTag("a02");
        btnList[3] = (Button)v.findViewById(R.id.a03);
        btnList[3].setTag("a03");
        btnList[4] = (Button)v.findViewById(R.id.a04);
        btnList[4].setTag("a04");
        btnList[5] = (Button)v.findViewById(R.id.a05);
        btnList[5].setTag("a05");
        btnList[6] = (Button)v.findViewById(R.id.a06);
        btnList[6].setTag("a06");
        // TODO: for문으로 더 간결하게 how?
    }

    @Override
    public void onClick(View v)
    {
        // 클릭된 뷰를 버튼으로 받아옴
        Button newButton = (Button) v;

        // 향상된 for문을 사용, 클릭된 버튼을 찾아냄
        for(Button tempButton : btnList)
        {
            // 클릭된 버튼을 찾았으면
            if(tempButton == newButton)
            {
                // 위에서 저장한 버튼의 포지션을 태그로 가져옴
                selectedSeatNum = (String)v.getTag();
                // 태그로 가져온 포지션을 이용해 리스트에서 출력할 데이터를 꺼내서 토스트 메시지 출력
                mCustomDialog = new CustomDialog(ShowingMapActivity.this,
                        "예약 확인", // 제목
                        "[ "+selectedSeatNum+" ] 자리를 예약하시겠습니까?", // 내용
                        leftListener, // 왼쪽 버튼 이벤트
                        rightListener); // 오른쪽 버튼 이벤트
                mCustomDialog.show();
            }
        }
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), selectedSeatNum,
                    Toast.LENGTH_SHORT).show();
            // FLAG_ACTIVITY_CLEAR_TOP !
            Intent intent = new Intent();
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소",
                    Toast.LENGTH_SHORT).show();
            mCustomDialog.dismiss();
        }
    };

    /**
     * 화면 전환 애니메이션 초기화
     */
    private void initTransitions() {

        Window window = getWindow();
        RevealTransition reveal = createRevealTransition();
        window.setEnterTransition(reveal);

    }

    /**
     * 원형 만드는 기능
     * @return
     */
    private RevealTransition createRevealTransition() {
        Point epicenter = getIntent().getParcelableExtra(EXTRA_EPICENTER);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int bigRadius = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        RevealTransition reveal = new RevealTransition(epicenter, 0, bigRadius, 1500);
        return reveal;
    }



}
