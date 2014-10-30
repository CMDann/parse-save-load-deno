package ca.cmdann.parse.parsesaveloaddemo;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.PushService;


public class MyActivity extends Activity {

    // You need some keys yo!
    private final String KEY1 = "";
    private final String KEY2 = "";

    private Button save_button;
    private EditText name_field;
    private EditText age_field;
    private ParseObject testObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Get the elements
        save_button = (Button) findViewById(R.id.save_button);
        name_field  = (EditText) findViewById(R.id.name_input);
        age_field   = (EditText) findViewById(R.id.age_input);

        // Set the button handler
        SaveHandler sh = new SaveHandler();
        save_button.setOnClickListener(sh);

        // Get your keys from parse.com
        Parse.initialize(this, KEY1, KEY2);

        // Set the default callback
        PushService.setDefaultPushCallback(this, MyActivity.class);

        // Create the test object
        testObject = new ParseObject("ExampleObject");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void save_name(String name){
        testObject.put("Name", name);
        testObject.saveInBackground();
    }

    public void save_age(int age){
        testObject.put("Age", age);
        testObject.saveInBackground();

    }

    public class SaveHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String name = name_field.getText().toString();
            int age = Integer.parseInt(age_field.getText().toString());

            // Check if the internet is available
            if (isNetworkAvailable()){
                Toast.makeText(MyActivity.this, "Saving to parse!", Toast.LENGTH_SHORT).show();
                save_name(name);
                save_age(age);
            } else {
                Toast.makeText(MyActivity.this, "No internet!", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
