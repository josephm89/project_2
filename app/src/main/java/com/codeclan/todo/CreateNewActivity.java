package com.codeclan.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class CreateNewActivity extends AppCompatActivity {

  private DatePicker datePicker;
  private Calendar calendar;
  private TextView dateView;
  private int year, month, day;
  private String newDate;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_new);


    dateView = (TextView) findViewById(R.id.textView3);
    calendar = Calendar.getInstance();
    year = calendar.get(Calendar.YEAR);

    month = calendar.get(Calendar.MONTH);
    day = calendar.get(Calendar.DAY_OF_MONTH);
    showDate(year, month+1, day);
  }
  // \/ \/ \/ \/ \/ Fancy stuff for date picker \/ \/ \/ \/
  @SuppressWarnings("deprecation")
  public void setDate(View view) {
    showDialog(999);
    Toast.makeText(getApplicationContext(), "ca",
        Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    if (id == 999) {
      return new DatePickerDialog(this,
          myDateListener, year, month, day);
    }
    return null;
  }

  private DatePickerDialog.OnDateSetListener myDateListener = new
      DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0,
            int arg1, int arg2, int arg3) {
          showDate(arg1, arg2+1, arg3);

          StringBuilder newString;

          int newMonth = (arg2 + 1);

          if (newMonth < 10){
            newString = new StringBuilder().append(arg1).append("/").append("0")
                .append(arg2+1).append("/").append(arg3);
          }

          else {
            newString = new StringBuilder().append(arg1).append("/")
                .append(arg2).append("/").append(arg3);
          }


          newDate = newString.toString();
          Log.d("new date: ",newDate);
        }
      };

  private void showDate(int year, int month, int day) {
    dateView.setText(new StringBuilder().append(day).append("/")
        .append(month).append("/").append(year));
  }

}