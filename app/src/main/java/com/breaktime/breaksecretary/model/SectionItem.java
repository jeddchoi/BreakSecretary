package com.breaktime.breaksecretary.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.breaktime.breaksecretary.R;

public class SectionItem extends LinearLayout {

    TextView txtSectionName;
    TextView txtReserveNum;
    TextView txtTotalNum;
    ProgressBar pbTotal;
    ProgressBar pbReserve;


    public SectionItem(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_section_item, this);
        txtSectionName = (TextView)findViewById(R.id.txt_section_name);
        txtReserveNum = (TextView)findViewById(R.id.txt_reserve_num);
        txtTotalNum = (TextView)findViewById(R.id.txt_total_num);
        pbTotal = (ProgressBar)findViewById(R.id.pb_total);
        pbReserve = (ProgressBar)findViewById(R.id.pb_reserve);
    }

    public void setData(Section section)
    {
        txtSectionName.setText(section.getSectionNum());
        txtReserveNum.setText(section.getReserveNum());
        txtTotalNum.setText(section.getTotallNum());
        //ToDo: 프로그레스바 조정
    }

    public String getSectionName()
    {
        return txtSectionName.getText().toString();
    }

    public String getReserveNum()
    {
        return txtReserveNum.getText().toString();
    }

    public String getTotalNum()
    {
        return txtTotalNum.getText().toString();
    }


}
