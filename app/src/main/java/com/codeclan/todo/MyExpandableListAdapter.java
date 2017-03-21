package com.codeclan.todo;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private String[] toDoes;

  public MyExpandableListAdapter(Context context, String[] toDoes) {
    this.context = context;
    this.toDoes = toDoes;
  }

  @Override ///pipa
  public int getGroupCount() {
    return toDoes.length;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return 1;
  }

  @Override /////pipa
  public Object getGroup(int groupPosition) {
    return toDoes[groupPosition];
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return null;
  }

  @Override /////pipa
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return 0;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }


  //////////////////////////////////////////////


  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {
    if(convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) this.context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.list_group, null);
    }
    TextView listName = (TextView) convertView.findViewById(R.id.listTitle);
    listName.setText(toDoes[groupPosition]);
    return convertView;
  }

  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {
    if(convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) this.context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.list_item, null);
    }
//    TextView listName2 = (TextView) convertView.findViewById(R.id.childStuff);
//    listName2.setText("eh");
    return convertView;
  }


  ///////////////////////////////////////////////////


  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }
}