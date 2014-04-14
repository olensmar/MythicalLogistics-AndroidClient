package com.mythicallogistics.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class ShowShipmentActivity extends ActionBarActivity {

    private Bundle extras;
    private String pickupTime;
    private String shipmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shipment);

        extras = getIntent().getExtras();

        try {
            JSONObject object = new JSONObject( extras.getString( GetQuoteActivity.JSON_RESPONSE ));
            pickupTime = object.getString("pickup_time");
            ((TextView) findViewById(R.id.pickupTimeText)).setText(pickupTime);
            shipmentId = object.getString("shipment_id");
            ((TextView) findViewById(R.id.shipmentIdText)).setText(shipmentId);
            ((TextView) findViewById(R.id.trackingNumberText)).setText( object.getString( "tracking_number") );

        } catch (JSONException e) {
            e.printStackTrace();
        }


        findViewById( R.id.get_shipment_status_button ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getShipmentStatus();
            }
        });
    }

    private void getShipmentStatus() {
        Intent intent = new Intent( ShowShipmentActivity.this, GetShipmentActivity.class);
        intent.putExtra( "SHIPMENT_ID", shipmentId );
        startActivity( intent );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_shipment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity( new Intent( ShowShipmentActivity.this, SettingsActivity.class ));
            return true;
        }
        else if (id == R.id.action_main) {
            startActivity( new Intent( ShowShipmentActivity.this, MainActivity.class ));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
