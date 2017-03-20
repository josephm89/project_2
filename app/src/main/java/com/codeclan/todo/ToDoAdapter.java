package com.codeclan.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class ToDoAdapter extends ArrayAdapter<ToDo> {

  public ToDoAdapter(Context context, ArrayList<ToDo> todoes) {
    super(context, 0, todoes);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent){

    View listItemView = convertView;
    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext()).inflate(R.layout.main_item,parent, false);
    }

    ToDo currentToDo = getItem(position);

    TextView name = (TextView) listItemView.findViewById(R.id.item1);
    name.setText(currentToDo.getName().toString());


    return listItemView;

  }


}
