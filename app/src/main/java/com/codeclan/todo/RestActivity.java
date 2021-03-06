package com.codeclan.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class RestActivity extends AppCompatActivity {

  public static final String REST = "rest";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("Later");
//    SharedPreferences delete = getSharedPreferences(TODOES, Context.MODE_PRIVATE);
//    delete.edit().clear().commit();  ///leave here to clear

    SharedPreferences sharedPref = getSharedPreferences(REST, Context.MODE_PRIVATE);
    String toDoString = sharedPref.getString("rest", null);
    SharedPreferences.Editor editor = sharedPref.edit();
    Gson gson = new Gson();

      if (toDoString != null) {

        TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {};

        ArrayList<ToDo> toDoArray = gson.fromJson(toDoString, ToDoArrayList.getType());

        editor.putString("rest", gson.toJson(toDoArray));
        editor.apply();
        ////////////////////////create string array for the adapter/////////////////////////
        int length = toDoArray.size();
        String[] stringArray = new String[length];
        int count = 0;
          while (count < length) {
              for(ToDo item : toDoArray) {
              stringArray[count] = item.getName();
          count++;
            }
          }

        ////////////////////// custom adapter////////////////////////////////////////
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        ExpandableListAdapter expandableListAdapter = new RestExpandableListAdapter(this, stringArray);

        expandableListView.setAdapter(expandableListAdapter);


        /////////////////////backup for normal list view adapter//////////////////////////////////
//        ToDoAdapter toDoAdapter = new ToDoAdapter(this, toDoArray);
//
//        ListView listView = (ListView) findViewById(R.id.myListView);
//
//        listView.setAdapter(toDoAdapter);

        // /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\
      }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.activity_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case R.id.action_create_new:
        Intent intent = new Intent(this, CreateNewActivity.class);
        startActivity(intent);
        break;
      case R.id.action_tomorrow:
        intent = new Intent(this, TomorrowActivity.class);
        startActivity(intent);
        break;
      case R.id.action_today:
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        break;
      case R.id.action_rest:
        intent = new Intent(this, RestActivity.class);
        startActivity(intent);
        break;
      default:
        return super.onOptionsItemSelected(item);
    }

    return true;
  }
}