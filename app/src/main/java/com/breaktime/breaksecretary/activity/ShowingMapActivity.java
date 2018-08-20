package com.breaktime.breaksecretary.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.breaktime.breaksecretary.R;

import pl.polidea.view.ZoomView;

public class ShowingMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_map);

        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.showing_map_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(3000, 3000);
        ZoomViewLoad(v, layoutParams);

        Button btn01 = (Button)v.findViewById(R.id.a00);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShowingMapActivity.this, "A00 selected", Toast.LENGTH_SHORT).show();
            }
        });


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
}
