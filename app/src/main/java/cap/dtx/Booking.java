package cap.dtx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LIOWEN on 27/05/2016.
 */
public class Booking extends AppCompatActivity {
    Button submit;
    EditText projectCode, taskNum, comment, hoursBox;
    CalendarView calendar;
    Spinner monthSelector, typeSelector;
    ArrayList<String> months;
    ArrayList<String> types;
    HashMap<String,String>selectedItems;
    ArrayList<String> days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);

        comment         = (EditText)findViewById(R.id.comment);
        projectCode     = (EditText)findViewById(R.id.projectCode);
        taskNum         = (EditText)findViewById(R.id.taskNum);
        hoursBox        = (EditText)findViewById(R.id.hoursBox);
        calendar        = (CalendarView)findViewById(R.id.calendarView);
        submit          = (Button)findViewById(R.id.submit);
        monthSelector   = (Spinner)findViewById(R.id.monthSelector);
        typeSelector    = (Spinner)findViewById(R.id.typeSelector);
        selectedItems   = new HashMap<>();
        days            = new ArrayList<>();



        //#####################################################//
        // Populates arraylists for spinner monthSelector and  //
        // typeSelector                                        //
        //#####################################################//

        months = new ArrayList<String>();
        months.add("Month");
        months.add("Jan");months.add("Feb");
        months.add("Mar");months.add("Apr");
        months.add("May");months.add("Jun");
        months.add("Jul");months.add("Aug");
        months.add("Sep");months.add("Oct");
        months.add("Nov");months.add("Dec");

        types = new ArrayList<String>();
        types.add("Type");
        types.add("Standard Time in UK");types.add("Overtime in UK");

        //##########################################//
        // Handles what is selected in the spinners //
        //##########################################//

        monthSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItems.put("Month", monthSelector.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItems.put("Type", typeSelector.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //#########################################################//
        // Adds and removes items from the selected days arraylist //
        //#########################################################//

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String h = hoursBox.getText().toString().trim();
                if (h.equals("")) {
                    Toast.makeText(getBaseContext(), "Please enter hours", Toast.LENGTH_SHORT).show();
                }
                else {
                    String date = dayOfMonth + " " + (month + 1) + " " + year + " -"
                            + h + "-";
                    if (days.contains(date)) {
                        Toast.makeText(getBaseContext(), date + " Removed", Toast.LENGTH_SHORT).show();
                        days.remove(date);

                    } else {
                        Toast.makeText(getBaseContext(), date + " Added", Toast.LENGTH_SHORT).show();
                        days.add(date);
                    }
                    monthSelector.setSelection(month + 1);
                }
            }
        });

        //##################################################################//
        // collects selected items together to be pushed into sql database  //
        // (currently just prints out what is selected with a bit of logic) //
        //##################################################################//

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItems.get("Month").equals("Month")){
                    Toast.makeText(getBaseContext(), "Please select a month"
                            ,Toast.LENGTH_SHORT).show();
                }
                else if(selectedItems.get("Type").equals("Type")){
                    Toast.makeText(getBaseContext(), "Please select a type"
                            ,Toast.LENGTH_SHORT).show();
                }
                else if(projectCode.getText().toString().trim().equals("")){
                    Toast.makeText(getBaseContext(), "Please select a project code"
                            ,Toast.LENGTH_SHORT).show();
                }
                else if(taskNum.getText().toString().trim().equals("")){
                    Toast.makeText(getBaseContext(), "Please select a task number"
                            ,Toast.LENGTH_SHORT).show();
                }
                else {

                    String date = "";
                    for(int i = 0; i < days.size(); i++){
                        date += (i+1) + ") "+ days.get(i) + "\n";
                    }
                    selectedItems.put("Date", date);
                    selectedItems.put("Task Number", taskNum.getText().toString());
                    selectedItems.put("Project Code", projectCode.getText().toString());
                    selectedItems.put("Comment", comment.getText().toString());

                    String s = "";
                    Iterator it = selectedItems.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        s += pair.getKey() + ": " + pair.getValue() + "\n";
                    }
                    //will populate sql table instead of making a toast
                    Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();

                    Toast.makeText(getBaseContext(), "Time successfully booked", Toast.LENGTH_SHORT).show();
                    toMainActivity();

                }
            }
        });

        //##################################################################//
        // Creating adapter for spinner with a drop down layout style       //
        // list view with radio button and attaches data adapter to spinner //
        //##################################################################//

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSelector.setAdapter(monthAdapter);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelector.setAdapter(typeAdapter);
    }

    public void toMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //#####################//
    // Creates the toolbar //
    //#####################//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.booking) {
            Intent intent = new Intent(this, Booking.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.holidays) {
            Intent intent = new Intent(this, Holidays.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.approvals) {
            Intent intent = new Intent(this, Approvals.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.settings) {
            Toast.makeText(getBaseContext(), "To be added?" , Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
