package com.codeclan.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  public static final String TODOES = "Todoes";
  ArrayList<ToDo> toDoList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toDoList = new ArrayList<ToDo>();
    toDoList.add(new ToDo("laundry",true,"2017-03-20");

///////////////////////////////////////////////////////

    SharedPreferences sharedPref = getSharedPreferences(TODOES, Context.MODE_PRIVATE);
    String todoes99 = sharedPref.getString("toDoes", "empty");

    Gson gson = new Gson();

    TypeToken<ArrayList<ToDo>> ToDoArrayList = new TypeToken<ArrayList<ToDo>>(){};
    ArrayList<ToDo> ToDoArray11 = gson.fromJson(todoes99, ToDoArrayList.getType());

    TextView list = (TextView)findViewById(R.id.//**todo make layout pls);**//
        ;
    String toDoString = "";
    for(ToDo todo : ToDoArray11) {
      toDoString += todo.getName() + " " + todo.getDueDate() + "\r\n";
    }
    list.setText(toDoString);
  }




  public void addToDoToSharedPreferences(View view){

    SharedPreferences sharedPref = getSharedPreferences(TODOES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();

    Gson gson = new Gson();

    editor.putString("toDoes", gson.toJson(toDoList));
    editor.apply();


   ////// Toast.makeText(MainActivity.this, "stuff added", Toast.LENGTH_LONG).show();
  }


//  @Override
//  public boolean onCreateOptionsMenu(Menu menu){
//    MenuInflater menuInflater = getMenuInflater();
//    menuInflater.inflate(R.menu.activity_main, menu);
//    return true;
//  }
//
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    if (item.getItemId() == R.id.action_favourites){
//      //TODO start a new activity
//      Intent intent = new Intent(this, FavouritesActivity.class);
//      startActivity(intent);
//      return true;
//    }
//    return super.onOptionsItemSelected(item);
//  }

}