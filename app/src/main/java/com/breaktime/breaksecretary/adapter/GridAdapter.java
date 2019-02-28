package com.breaktime.breaksecretary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.breaktime.breaksecretary.model.Section;
import com.breaktime.breaksecretary.model.SectionItem;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<Section> sectionItems = new ArrayList<>();

    public GridAdapter(Context context, ArrayList<Section> sectionItems) {
        this.context = context;
        this.sectionItems = sectionItems;
    }

    @Override
    public int getCount() {     return sectionItems.size();   }

    @Override
    public Object getItem(int i) {  return sectionItems.get(i);    }

    @Override
    public long getItemId(int i) {  return i;   }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = new SectionItem(context);

        ((SectionItem)view).setData(sectionItems.get(i));

        return view;
    }
}
