package com.mythicallogistics.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mythicallogistics.app.api.ApiFactory;
import com.mythicallogistics.app.api.MythicalLogisticsIncShippingAPI;
import com.mythicallogistics.app.util.AsyncApiTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class GetShipmentActivity extends ActionBarActivity {

    private EditText shipmentIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);

        shipmentIdEditText = (EditText) findViewById( R.id.shipmentIdText );
        if( getIntent().getExtras() != null  )
        {
            String shipmentId = getIntent().getExtras().getString( "SHIPMENT_ID" );
            if( shipmentId != null )
                shipmentIdEditText.setText(shipmentId);
        }

        findViewById( R.id.get_shipment_status_button ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               getShipmentStatus();
            }
        });
    }

    private void getShipmentStatus() {
        GetShipmentTask task = new GetShipmentTask();
        task.execute((Void) null);
    }

    public class GetShipmentTask extends AsyncApiTask {

        private String result;

        public GetShipmentTask() {
            super(GetShipmentActivity.this);
        }

        @Override
        public void doTask() throws Exception {

            MythicalLogisticsIncShippingAPI api = ApiFactory.get().getApi();

            Response response = api.getShipmentByShipmentId( shipmentIdEditText.getText().toString(),
                    "1.0");

            InputStream in = response.getBody().in();
            byte[] buf = new byte[(int) response.getBody().length()];
            in.read(buf);

            result = new String( buf );
        }

        @Override
        public void afterTask() {
            try {
                JSONObject object = new JSONObject(result );
                ((TextView) findViewById(R.id.statusText)).setText(object.getString("status"));
                ((TextView) findViewById(R.id.createdAtText)).setText(object.getString("created_at"));
                ((TextView) findViewById(R.id.pickupTimeText)).setText( object.getString( "pickup_time"));
                ((TextView) findViewById(R.id.trackingNumberText)).setText( object.getString( "tracking_number"));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void afterException(Exception e) {
            Log.d(MythicalApp.class.getName(), "GetShipment Error [" +  e.getMessage() + "]");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shipment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity( new Intent( GetShipmentActivity.this, SettingsActivity.class ));
            return true;
        }
        else if (id == R.id.action_main) {
            startActivity( new Intent( GetShipmentActivity.this, MainActivity.class ));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
