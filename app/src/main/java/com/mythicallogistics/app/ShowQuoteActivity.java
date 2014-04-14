package com.mythicallogistics.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mythicallogistics.app.api.ApiFactory;
import com.mythicallogistics.app.api.MythicalLogisticsIncShippingAPI;
import com.mythicallogistics.app.util.AsyncApiTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedString;


public class ShowQuoteActivity extends ActionBarActivity {

    private Bundle extras;
    private String createdAt;
    private String quoteId;
    private CreateShipmentTask createShipmentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_quote);

        extras = getIntent().getExtras();

        try {
            JSONObject object = new JSONObject( extras.getString( GetQuoteActivity.JSON_RESPONSE ));
            createdAt =  object.getString("created_at");
            ((TextView) findViewById(R.id.dateText)).setText(createdAt);
            quoteId = object.getString("quote_id");
            ((TextView) findViewById(R.id.amountText)).setText( object.getString( "amount") + " " + object.getString( "currency") );

        } catch (JSONException e) {
            e.printStackTrace();
        }


        findViewById( R.id.accept_quote_button ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            acceptQuote();
            }
        });

        findViewById( R.id.cancel_quote_button ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void acceptQuote() {

        createShipmentTask = new CreateShipmentTask();
        createShipmentTask.execute((Void) null);
    }

    public class CreateShipmentTask extends AsyncApiTask {

        private String result;

        public CreateShipmentTask() {
            super(ShowQuoteActivity.this);
        }

        @Override
        public void doTask() throws Exception {

            MythicalLogisticsIncShippingAPI api = ApiFactory.get().getApi();
            SharedPreferences prefs = MythicalApp.getInstance().getPrefs();

            JSONObject body = new JSONObject();
            body.put( "account_number", prefs.getString( MythicalApp.PREF_ACCOUNT_NUMBER, "" ) );
            body.put( "quote_id", quoteId );
            body.put( "object", extras.getString( GetQuoteActivity.INPUT_OBJECT ));
            body.put( "weight", extras.getString( GetQuoteActivity.INPUT_WEIGHT ));
            body.put( "destination", extras.getString( GetQuoteActivity.INPUT_DESTINATION ));
            body.put( "origin", extras.getString( GetQuoteActivity.INPUT_ORIGIN ));
            body.put( "service", extras.getString( GetQuoteActivity.INPUT_SERVICE ));

            Response response = api.postShipment("1.0", new TypedByteArray( "application/json", body.toString().getBytes( "UTF-8") ));

            InputStream in = response.getBody().in();
            byte[] buf = new byte[(int) response.getBody().length()];
            in.read(buf);

            result = new String( buf );
        }

        @Override
        public void afterTask() {
            Intent intent = new Intent( ShowQuoteActivity.this, ShowShipmentActivity.class );
            intent.putExtra(GetQuoteActivity.JSON_RESPONSE, result);
            startActivity(intent);
        }

        @Override
        public void afterException(Exception e) {
            Log.d(MythicalApp.class.getName(), "PostShipment Error: [" + e.getMessage() + "]");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity( new Intent( ShowQuoteActivity.this, SettingsActivity.class ));
            return true;
        }
        else if (id == R.id.action_main) {
            startActivity( new Intent( ShowQuoteActivity.this, MainActivity.class ));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
