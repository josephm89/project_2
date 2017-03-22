package com.codeclan.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class TomorrowExpandableListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private String[] toDoes;
  public static final String TOMORROW = "tomorrow";
  public SharedPreferences sharedPref;
  String toDoString;
  SharedPreferences.Editor editor;
  Gson gson;
  TypeToken<ArrayList<ToDo>> ToDoArrayList;
  ArrayList<ToDo> toDoArray;

  public TomorrowExpandableListAdapter(Context context, String[] toDoes) {
    this.context = context;
    this.toDoes = toDoes;
    this.sharedPref = context.getSharedPreferences(TOMORROW, Context.MODE_PRIVATE);
    this.toDoString = sharedPref.getString("tomorrow", null);
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

          editor.putString("tomorrow", gson.toJson(toDoArray));
          editor.apply();

          context.startActivity(new Intent(context, TomorrowActivity.class));
        }
      }
    });

    Button moveUp = (Button) convertView.findViewById(R.id.moveUp);
    moveUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (groupPosition!=0) {

          Collections.swap(toDoArray, groupPosition, groupPosition - 1);

          editor.putString("tomorrow", gson.toJson(toDoArray));
          editor.apply();

          context.startActivity(new Intent(context, TomorrowActivity.class));
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
          editor.putString("tomorrow", gson.toJson(toDoArray));
          editor.apply();
          context.startActivity(new Intent(context,TomorrowActivity.class));

        }
      });
      //todo BUTTON FINISH
      //todo BUTTON RESCHEDULE
    ////////////////////////DELAY BUTTON ////////////////////////////
    Button delay = (Button) convertView.findViewById(R.id.childResced);
    delay.setText("Do it Today");
    delay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String nameToCompare = toDoes[groupPosition];
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String newDate = format1.format(cal.getTime());
        Log.d("NAAAAAA",newDate);

        for (ToDo todo: toDoArray){
          if (todo.getName().equals(nameToCompare)){
            todo.setDueDate(newDate);
          }
        }

        editor.putString("tomorrow", gson.toJson(toDoArray));
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
}
