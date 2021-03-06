package com.codeclan.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private String[] toDoes;
  public static final String TODAY = "today";
  public SharedPreferences sharedPref;
  String toDoString;
  SharedPreferences.Editor editor;
  Gson gson;
  TypeToken<ArrayList<ToDo>> ToDoArrayList;
  ArrayList<ToDo> toDoArray;

  public MyExpandableListAdapter(Context context, String[] toDoes) {
    this.context = context;
    this.toDoes = toDoes;
    this.sharedPref = context.getSharedPreferences(TODAY, Context.MODE_PRIVATE);
    this.toDoString = sharedPref.getString("today", null);
    this.editor= sharedPref.edit();
    this.gson = new Gson();
    this.ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {};
    this.toDoArray = gson.fromJson(toDoString, ToDoArrayList.getType());
  }

//  public void onGroupExpanded (int groupPosition){
//    Log.d("expandedd","yeee");
//
//    LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    View root = layoutInflater.inflate(R.layout.list_group, null);
//// from your example
//    LinearLayout b = (LinearLayout) root.findViewById(R.id.completeholder);
//    b.setVisibility(View.GONE);
//
//  }



  @Override
  public int getGroupCount() {
    return toDoes.length;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return 1;
  }

  @Override
  public Object getGroup(int groupPosition) {
    return toDoes[groupPosition];
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return null;
  }

  @Override
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
  public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {
    if (convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) this.context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.list_group, null);
    }
    TextView listName = (TextView) convertView.findViewById(R.id.listTitle);
    listName.setText(toDoes[groupPosition]);

    Button moveDown = (Button) convertView.findViewById(R.id.moveDown);
    moveDown.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (toDoes.length-1>groupPosition) {

          Collections.swap(toDoArray, groupPosition, groupPosition + 1);

          editor.putString("today", gson.toJson(toDoArray));
          editor.apply();

          context.startActivity(new Intent(context, MainActivity.class));
        }
      }
    });

    Button moveUp = (Button) convertView.findViewById(R.id.moveUp);
    moveUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (groupPosition!=0) {

          Collections.swap(toDoArray, groupPosition, groupPosition - 1);

          editor.putString("today", gson.toJson(toDoArray));
          editor.apply();

          context.startActivity(new Intent(context, MainActivity.class));
        }
      }
    });



    return convertView;
  }




  @Override
  public View getChildView(final int groupPosition, int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {
    if(convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) this.context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.list_item, null);

      }
      //////////////////////////COMPLETE BUTTON///////////////////////////
      Button complete = (Button) convertView.findViewById(R.id.childComplete);

      complete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          String nameToDelete = toDoes[groupPosition];

          ToDo toDelete = null;
          for (ToDo todo: toDoArray){
            if (todo.getName().equals(nameToDelete)){
              toDelete = todo;
            }
          }

          toDoArray.remove(toDelete);
          editor.putString("today", gson.toJson(toDoArray));
          editor.apply();

          ///TODO GIVE XP
          context.startActivity(new Intent(context,MainActivity.class));

        }
      });
      ////////////////////////DELAY BUTTON ////////////////////////////
      Button delay = (Button) convertView.findViewById(R.id.childResced);
      delay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String nameToCompare = toDoes[groupPosition];
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String newDate = format1.format(cal.getTime());

        for (ToDo todo: toDoArray){
          if (todo.getName().equals(nameToCompare)){
            todo.setDueDate(newDate);
          }
        }

        editor.putString("today", gson.toJson(toDoArray));
        editor.apply();
        context.startActivity(new Intent(context,MainActivity.class));

      }
    });
      /////////////////////////DELETE BUTTON//////////////////////////////
      Button delete = (Button) convertView.findViewById(R.id.childDelete);
      delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          String nameToDelete = toDoes[groupPosition];
          ToDo toDelete = null;

          for (ToDo todo: toDoArray){
            if (todo.getName().equals(nameToDelete)){
              toDelete = todo;
            }
          }

          toDoArray.remove(toDelete);

          editor.putString("today", gson.toJson(toDoArray));
          editor.apply();
          context.startActivity(new Intent(context,MainActivity.class));

        }
      });
    return convertView;
  }

  ///////////////////////////////////////////////////

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }
//  public void onGroupExpanded (int groupPosition){
//    Log.d("expanded","yeee");
//    LayoutInflater layoutInflater = (LayoutInflater) this.context
//        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    View view = layoutInflater.inflate(R.layout.list_item, null);
//    LinearLayout v = (LinearLayout) view.findViewById(R.id.list);
//    v.setVisibility(View.GONE);
//  }
//  public void onGroupCollapsed (int groupPosition){
//    Log.d("collapsed","yeee");
//    LayoutInflater layoutInflater = (LayoutInflater) this.context
//        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    View view = layoutInflater.inflate(R.layout.list_item, null);
//    LinearLayout v = (LinearLayout) view.findViewById(R.id.list);
//    v.setVisibility(View.GONE);
//  }
//  public void myFunction(int i){
//    LinearLayout labelView = (LinearLayout) MainActivity.findViewById(R.id.list);
//    labelView.setVisibility(View.GONE);
//  }
}
