package com.mythicallogistics.app;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mythicallogistics.app.api.ApiFactory;


public class SettingsActivity extends ActionBarActivity {

    private EditText apiHostEditText;
    private EditText accountNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = MythicalApp.getInstance().getPrefs();

        apiHostEditText = ((EditText)findViewById( R.id.apiHostEditText ));
        apiHostEditText.setText(prefs.getString( MythicalApp.PREF_API_HOST, ApiFactory.DEFAULT_ENDPOINT ));

        accountNumberText = ((EditText)findViewById( R.id.accountNumberText ));
        accountNumberText.setText( prefs.getString( MythicalApp.PREF_ACCOUNT_NUMBER, "" ));

        findViewById( R.id.save_settings_button ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = MythicalApp.getInstance().getPrefs().edit();
                String value = apiHostEditText.getText().toString();
                if( value == null || value.trim().length() == 0 )
                    value = ApiFactory.DEFAULT_ENDPOINT;

                edit.putString(MythicalApp.PREF_API_HOST, value);
                edit.putString(MythicalApp.PREF_ACCOUNT_NUMBER, accountNumberText.getText().toString());
                edit.commit();
                finish();
            }
        });

        findViewById( R.id.cancel_settings_button ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.settings, menu);
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

}
