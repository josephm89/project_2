package com.codeclan.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collections;

public class RestExpandableListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private String[] toDoes;
  public static final String REST = "rest";
  public SharedPreferences sharedPref;
  String toDoString;
  SharedPreferences.Editor editor;
  Gson gson;
  TypeToken<ArrayList<ToDo>> ToDoArrayList;
  ArrayList<ToDo> toDoArray;

  public RestExpandableListAdapter(Context context, String[] toDoes) {
    this.context = context;
    this.toDoes = toDoes;
    this.sharedPref = context.getSharedPreferences(REST, Context.MODE_PRIVATE);
    this.toDoString = sharedPref.getString("rest", null);
    this.editor= sharedPref.edit();
    this.gson = new Gson();
    this.ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {
    };
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
    ///////////////////////NAME////////////////////////
    TextView listName = (TextView) convertView.findViewById(R.id.listTitle);
    listName.setText(toDoes[groupPosition]);


    ///////////////////////////////////////////
    //ToDO add conditional colouring

//
//    for (ToDo todo: toDoArray){
//      if (Stringarray[groupPosition].equals(todo.getName());
//      {
//        if (todo.getDueDate()<today){
//          listName.setTextColor(Color.parseColor("#f44268"));
//
//        }
//      }
//    }


    //////////////////////////////////////////

    /////////////////////////////DOWN BUTTON////////////////////////
    Button moveDown = (Button) convertView.findViewById(R.id.moveDown);
    moveDown.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (toDoes.length-1>groupPosition) {

          Collections.swap(toDoArray, groupPosition, groupPosition + 1);

          editor.putString("rest", gson.toJson(toDoArray));
          editor.apply();

          context.startActivity(new Intent(context, RestActivity.class));
        }
      }
    });

    /////////////////////////////UP BUTTON////////////////////////
    Button moveUp = (Button) convertView.findViewById(R.id.moveUp);
    moveUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      if (groupPosition!=0) {

        Collections.swap(toDoArray, groupPosition, groupPosition - 1);

        editor.putString("rest", gson.toJson(toDoArray));
        editor.apply();

        context.startActivity(new Intent(context, RestActivity.class));
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

          editor.putString("rest", gson.toJson(toDoArray));
          editor.apply();
          
          context.startActivity(new Intent(context,RestActivity.class));

        }
      });
    return convertView;
  }

  ///////////////////////////////////////////////////

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }
}
