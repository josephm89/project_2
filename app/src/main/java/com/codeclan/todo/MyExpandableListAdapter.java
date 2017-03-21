package com.codeclan.todo;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private String[] toDoes;
  public static final String TODOES = "Todoes";

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
  public View getChildView(final int groupPosition, int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {
    if(convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) this.context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.list_item, null);
    }
//    TextView listName2 = (TextView) convertView.findViewById(R.id.childStuff);
//    listName2.setText("eh");

      Button delete = (Button) convertView.findViewById(R.id.childDelete);
      delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //todo delete from sharedprefs
              Log.d("clicked","yeaah");
              Log.d("clicked",Integer.toString((groupPosition)));



          String nameToDelete = toDoes[groupPosition];

          Log.d("name to del",nameToDelete);

          SharedPreferences sharedPref = context.getSharedPreferences(TODOES,Context.MODE_PRIVATE);
          String toDoString = sharedPref.getString("toDoes", null);
          SharedPreferences.Editor editor = sharedPref.edit();
          Gson gson = new Gson();

            TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {};

            ArrayList<ToDo> toDoArray = gson.fromJson(toDoString, ToDoArrayList.getType());

          //Log.d("name to delbefore",toDoArray.toString());
          //Log.d("name to del",Integer.toString(toDoArray.size()));

          for (ToDo todo: toDoArray){
            if (todo.getName().equals(nameToDelete)){
              toDoArray.remove(todo);
              editor.putString("toDoes", gson.toJson(toDoArray));
              editor.apply();
            }
          }
          context.startActivity(new Intent(context,MainActivity.class));




          //Log.d("name to del",Integer.toString(toDoArray.size()));



//          String toDoName = toDoArrayList.getName();
//          for (String items:toDoes){
//              if(items.equals(toDoName)) {
//              int pos = items.indexOf();
//            }
//          }

//          for (int i = 0; i < Types.length; i++) {
//            if(TYPES[i].equals(userString)){
//              return i;
//            }
//          }



          ///_listDataChild.get(_listDataHeader.get(groupPosition)).remove(childPosition);

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
