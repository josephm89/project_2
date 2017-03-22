package com.codeclan.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.Collections;

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
    if(convertView == null) {
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
//        //TODO set date to tomorrow
//      Button delay = (Button) convertView.findViewById(R.id.childDelay);
//      delay.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//
//        String nameToDelete = toDoes[groupPosition];
//
//        SharedPreferences sharedPref1 = context.getSharedPreferences(TODAY,Context.MODE_PRIVATE);
//        String toDoString1 = sharedPref1.getString("today", null);
//        SharedPreferences.Editor editor1 = sharedPref1.edit();
//        Gson gson1 = new Gson();
//
//        TypeToken<ArrayList<ToDo>> ToDoArrayList1 = new TypeToken<ArrayList<ToDo>>() {};
//        ArrayList<ToDo> toDoArray1 = gson1.fromJson(toDoString1, ToDoArrayList1.getType());
//        ToDo toDelete = null;
//        for (ToDo todo: toDoArray1){
//          if (todo.getName().equals(nameToDelete)){
//            toDelete = todo;
//          }
//        }
//
//        //TODO CHANGE DATE;
//        editor1.putString("today", gson1.toJson(toDoArray1));
//        editor1.apply();
//        context.startActivity(new Intent(context,MainActivity.class));
//
//      }
//    });
    return convertView;
  }

  ///////////////////////////////////////////////////

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }
}
