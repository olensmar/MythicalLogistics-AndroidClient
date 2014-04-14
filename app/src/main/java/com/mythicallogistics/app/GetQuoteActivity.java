package com.mythicallogistics.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.mythicallogistics.app.api.ApiFactory;
import com.mythicallogistics.app.api.MythicalLogisticsIncShippingAPI;
import com.mythicallogistics.app.util.AsyncApiTask;

import java.io.InputStream;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class GetQuoteActivity extends ActionBarActivity {

    public static final String JSON_RESPONSE = "JSON_RESPONSE";
    public static final String INPUT_WEIGHT = "INPUT_WEIGHT";
    public static final String INPUT_SERVICE = "INPUT_SERVICE";
    public static final String INPUT_ORIGIN = "INPUT_ORIGIN";
    public static final String INPUT_VOLUME = "INPUT_VOLUME";
    public static final String INPUT_OBJECT = "INPUT_OBJECT";
    public static final String INPUT_DESTINATION = "INPUT_DESTINATION";
    private GetQuoteTask getQuoteTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        findViewById( R.id.get_quote_button ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuoteTask = new GetQuoteTask();
                getQuoteTask.execute((Void) null);
            }
        });
    }

    public class GetQuoteTask extends AsyncApiTask {

        private String result;

        public GetQuoteTask() {
            super(GetQuoteActivity.this);
        }

        @Override
        public void doTask() throws Exception {

            MythicalLogisticsIncShippingAPI api = ApiFactory.get().getApi();

            Response response = api.getQuote(
                    ((EditText) findViewById(R.id.weightEditText)).getText().toString(),
                    String.valueOf(((Spinner) findViewById(R.id.serviceSpinner)).getSelectedItem()),
                    ((EditText) findViewById(R.id.originEditText)).getText().toString(),
                    ((EditText) findViewById(R.id.volumeEditText)).getText().toString(),
                    String.valueOf(((Spinner) findViewById(R.id.objectSpinner)).getSelectedItem()),
                   "",
                    ((EditText) findViewById(R.id.destinationEditText)).getText().toString(),
                    "1.0");

            InputStream in = response.getBody().in();
            byte[] buf = new byte[(int) response.getBody().length()];
            in.read(buf);

            result = new String( buf );
        }

        @Override
        public void afterTask() {
            Intent intent = new Intent( GetQuoteActivity.this, ShowQuoteActivity.class );
            intent.putExtra(JSON_RESPONSE, result);
            intent.putExtra(INPUT_WEIGHT,  ((EditText) findViewById(R.id.weightEditText)).getText().toString());
            intent.putExtra(INPUT_SERVICE,  String.valueOf(((Spinner) findViewById(R.id.serviceSpinner)).getSelectedItem()));
            intent.putExtra(INPUT_ORIGIN,  ((EditText) findViewById(R.id.originEditText)).getText().toString());
            intent.putExtra(INPUT_VOLUME,  ((EditText) findViewById(R.id.volumeEditText)).getText().toString());
            intent.putExtra(INPUT_OBJECT,  String.valueOf(((Spinner) findViewById(R.id.objectSpinner)).getSelectedItem()));
            intent.putExtra(INPUT_DESTINATION,  ((EditText) findViewById(R.id.destinationEditText)).getText().toString());
            startActivity(intent);
        }

        @Override
        public void afterException(Exception e) {
            Log.d(MythicalApp.class.getName(), "GetQuote Error [" +  e.getMessage() + "]");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity( new Intent( GetQuoteActivity.this, SettingsActivity.class ));
            return true;
        }
        else if (id == R.id.action_main) {
            startActivity( new Intent( GetQuoteActivity.this, MainActivity.class ));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
