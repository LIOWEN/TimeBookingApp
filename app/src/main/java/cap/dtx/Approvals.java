package cap.dtx;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by LIOWEN on 27/05/2016.
 */
public class Approvals extends AppCompatActivity {

    ListView approvalList;
    AssetManager assetManager;
    String testData;
    HashMap<String,String> approvedItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approval_page);

        approvalList = (ListView)findViewById(R.id.list);
        assetManager = getAssets();
        approvedItems = new HashMap<>();

        //approvedItems.put("Test1", "Test2");

        approvalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String word = approvalList.getItemAtPosition(position).toString();

                String[] keyValue = word.split("=");
                String lw = keyValue[0];
                String ld = keyValue[1];
            }
        });
        populateTestDataList();
        showList(approvedItems);
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
        if (id == R.id.action_calendar) {
            Intent intent = new Intent(this, Calendar.class);
            startActivity(intent);
            return true;
        }
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
            Toast.makeText(getBaseContext(), "To be added?", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //##########################################################//
    // makes the listview visible                               //
    // hashmap passed in will contain information from Holidays //
    //##########################################################//

    public void showList(HashMap<String, String> hm) {
        ListAdapter adapter = new ListAdapter(hm);
        approvalList.setAdapter(adapter);
    }

    //#####################################################//
    //                    -TEST DATA-                      //
    // extracts data from approvalTestData in assets file  //
    // populates hashmap approvedItems                     //
    //#####################################################//

    public String createTestData(String file) {
        InputStream input;
        try {
            input = assetManager.open(file);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String text = new String(buffer);
            text.trim();
            text.replace("\n", "");
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void populateTestDataList(){
        testData = createTestData("approvalTestData");
        String[] pairs = testData.split("\\r?\\n");
        for(int i=0;i<pairs.length;i++) {
            String key = pairs[i];
            i++;
            String value = pairs[i];
            approvedItems.put(key, value);
        }
    }



}
