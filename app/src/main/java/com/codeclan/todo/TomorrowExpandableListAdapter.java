package com.codeclan.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class TomorrowExpandableListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private String[] toDoes;
  public static final String TOMORROW = "tomorrow";

  public TomorrowExpandableListAdapter(Context context, String[] toDoes) {
    this.context = context;
    this.toDoes = toDoes;
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
          SharedPreferences sharedPref = context.getSharedPreferences(TOMORROW, Context.MODE_PRIVATE);
          String toDoString = sharedPref.getString("tomorrow", null);
          SharedPreferences.Editor editor = sharedPref.edit();
          Gson gson = new Gson();

          TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {
          };
          ArrayList<ToDo> toDoArray = gson.fromJson(toDoString, ToDoArrayList.getType());

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
          SharedPreferences sharedPref = context.getSharedPreferences(TOMORROW, Context.MODE_PRIVATE);
          String toDoString = sharedPref.getString("tomorrow", null);
          SharedPreferences.Editor editor = sharedPref.edit();
          Gson gson = new Gson();

          TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {
          };
          ArrayList<ToDo> toDoArray = gson.fromJson(toDoString, ToDoArrayList.getType());

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

          SharedPreferences sharedPref1 = context.getSharedPreferences(TOMORROW,Context.MODE_PRIVATE);
          String toDoString1 = sharedPref1.getString("tomorrow", null);
          SharedPreferences.Editor editor1 = sharedPref1.edit();
          Gson gson1 = new Gson();

          TypeToken<ArrayList<ToDo>> ToDoArrayList1 = new TypeToken<ArrayList<ToDo>>() {};
          ArrayList<ToDo> toDoArray1 = gson1.fromJson(toDoString1, ToDoArrayList1.getType());
          ToDo toDelete = null;
          for (ToDo todo: toDoArray1){
            if (todo.getName().equals(nameToDelete)){
              toDelete = todo;
            }
          }

          toDoArray1.remove(toDelete);
          editor1.putString("tomorrow", gson1.toJson(toDoArray1));
          editor1.apply();
          context.startActivity(new Intent(context,TomorrowActivity.class));

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
