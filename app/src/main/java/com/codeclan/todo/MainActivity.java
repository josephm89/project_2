package com.codeclan.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

  public static final String TODAY = "today";
  public static final String TOMORROW = "tomorrow";
  public static final String REST = "rest";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    SharedPreferences delete = getSharedPreferences(TODOES, Context.MODE_PRIVATE);
//    delete.edit().clear().commit();  ///leave here to clear

    //////////////////////////////
    ///////SORT STUFF////////////
    SharedPreferences sharedPrefToday = getSharedPreferences(TODAY, Context.MODE_PRIVATE);
    SharedPreferences sharedPrefTomorrow = getSharedPreferences(TOMORROW, Context.MODE_PRIVATE);
    SharedPreferences sharedPrefRest = getSharedPreferences(REST, Context.MODE_PRIVATE);
//    sharedPrefToday.edit().clear().commit();
//    sharedPrefTomorrow.edit().clear().commit();     //KEEEP IT
//    sharedPrefRest.edit().clear().commit();
    String toDoStringToday = sharedPrefToday.getString("today",null);
    String toDoStringTomorrow = sharedPrefTomorrow.getString("tomorrow",null);
    String toDoStringRest = sharedPrefRest.getString("rest",null);
    SharedPreferences.Editor editorToday = sharedPrefToday.edit();
    SharedPreferences.Editor editorTomorrow = sharedPrefTomorrow.edit();
    SharedPreferences.Editor editorRest = sharedPrefRest.edit();
    Gson gsonToday = new Gson();
    Gson gsonTomorrow = new Gson();
    Gson gsonRest = new Gson();
    ArrayList<ToDo> newTodayToDoArray = new ArrayList<>();
    ArrayList<ToDo> newTomorrowToDoArray = new ArrayList<>();
    ArrayList<ToDo> newRestToDoArray = new ArrayList<>();


    //////////////Check today's stuff////////////////////////////////////
    if (toDoStringToday != null) {
      TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {};
      ArrayList<ToDo> todayToDoArray = gsonToday.fromJson(toDoStringToday, ToDoArrayList.getType());

//      editorAll.putString("allToDo", gsonAll.toJson(allToDoArray));
//      editorAll.apply();

      for(ToDo todo:todayToDoArray){

          Log.d("Something","here");


          Calendar calendar = Calendar.getInstance();
          calendar.set(Calendar.HOUR, 0);
          calendar.set(Calendar.MINUTE, 0);
          calendar.set(Calendar.SECOND, 0);
          calendar.set(Calendar.MILLISECOND, 0);
          calendar.set(Calendar.HOUR_OF_DAY, 0);
          Date today = calendar.getTime();

        Log.d("Something",today.toString());




          calendar.add(Calendar.DAY_OF_YEAR, 1);
          Date tomorrow = calendar.getTime();
        Log.d("Something",tomorrow.toString());


          Date date = null;
        Log.d("Something","after date");
          try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            date = dateFormat.parse(todo.getDueDate());
            Log.d("Something",todo.getDueDate());

            Log.d("whats date",date.toString());
          } catch (ParseException e) {
            e.printStackTrace();
          }

          if (date.equals(today)){
            newTodayToDoArray.add(todo);
          }
          else if (date.equals(tomorrow)){
            newTomorrowToDoArray.add(todo);
          }
          else{
            Log.d("whats in else",todo.toString());
            Log.d("whats in else",todo.getDueDate());
            newRestToDoArray.add(todo);
          }
      }

    }

    if (toDoStringTomorrow != null) {
      TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {};
      ArrayList<ToDo> tomorrowToDoArray = gsonTomorrow.fromJson(toDoStringTomorrow, ToDoArrayList.getType());

//      editorAll.putString("allToDo", gsonAll.toJson(allToDoArray));
//      editorAll.apply();

      for(ToDo todo:tomorrowToDoArray){

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        Date date = null;

        try {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
          date = dateFormat.parse(todo.getDueDate());

        } catch (ParseException e) {
          e.printStackTrace();
        }

        if (date.equals(today)){
          newTodayToDoArray.add(todo);
        }
        else if (date.equals(tomorrow)){
          newTomorrowToDoArray.add(todo);
        }
        else{
          newRestToDoArray.add(todo);
        }
      }

    }

    if (toDoStringRest != null) {
      TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {};
      ArrayList<ToDo> restToDoArray = gsonRest.fromJson(toDoStringRest, ToDoArrayList.getType());

//      editorAll.putString("allToDo", gsonAll.toJson(allToDoArray));
//      editorAll.apply();

      for(ToDo todo:restToDoArray){

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        Date date = null;

        try {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
          date = dateFormat.parse(todo.getDueDate());

        } catch (ParseException e) {
          e.printStackTrace();
        }

        if (date.equals(today)){
          newTodayToDoArray.add(todo);
        }
        else if (date.equals(tomorrow)){
          newTomorrowToDoArray.add(todo);
        }
        else{
          newRestToDoArray.add(todo);
        }
      }

    }


    ////////////back to the prefs/////////////
    if (newTodayToDoArray != null) {
      editorToday.putString("today", gsonToday.toJson(newTodayToDoArray));
      editorToday.apply();
    }

    if (newTomorrowToDoArray != null) {
      editorTomorrow.putString("tomorrow", gsonTomorrow.toJson(newTomorrowToDoArray));
      editorTomorrow.apply();
    }

    if (newRestToDoArray != null) {
      editorRest.putString("rest", gsonRest.toJson(newRestToDoArray));
      editorRest.apply();
    }


    /////////////////////////////
    SharedPreferences sharedPref = getSharedPreferences(TODAY, Context.MODE_PRIVATE);
    String toDoString = sharedPref.getString("today", null);
    SharedPreferences.Editor editor = sharedPref.edit();
    Gson gson = new Gson();

      if (toDoString != null) {

        TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>() {};

        ArrayList<ToDo> toDoArray = gson.fromJson(toDoString, ToDoArrayList.getType());

        editor.putString("toDoes", gson.toJson(toDoArray));
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
        ExpandableListAdapter expandableListAdapter = new MyExpandableListAdapter(this, stringArray);

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