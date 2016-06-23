package me.hda.urlhda;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.andreabaccega.widget.FormEditText;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button shorten;
    private FormEditText original;
    private ToggleButton strong;
    private ClipboardManager copy;
    private ClipData copyData;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        shorten = (Button) findViewById(R.id.post);
        original = (FormEditText) findViewById(R.id.url);
        strong = (ToggleButton) findViewById(R.id.strong);
        copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        View.OnClickListener bottomClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.clear:
                        original.setText("");
                        break;
                    case R.id.copy:
                        String text = original.getText().toString();
                        copyData = ClipData.newPlainText("text", text);
                        copy.setPrimaryClip(copyData);
                        Toast.makeText(getApplicationContext(), "URL Copied",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.share:
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

                        sharingIntent.setType("text/plain");
                        String shareBody = original.getText().toString();
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        break;
                    case R.id.settings:
                        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(i);
                        break;
                }
            }
        };

        findViewById(R.id.clear).setOnClickListener(bottomClickListener);
        findViewById(R.id.copy).setOnClickListener(bottomClickListener);
        findViewById(R.id.share).setOnClickListener(bottomClickListener);
        findViewById(R.id.settings).setOnClickListener(bottomClickListener);

        final CompoundButton.OnCheckedChangeListener toggleButtonChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (strong.isChecked()) {
                    original.setHint("Long URL -> hda.me/x15");
                } else {
                    original.setHint("Long URL -> hda.me/xxxxx");
                }

            }
        };
        strong.setOnCheckedChangeListener(toggleButtonChangeListener);

        shorten.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FormEditText[] allFields = {original};


                boolean allValid = true;
                for (FormEditText field : allFields) {
                    allValid = field.testValidity() && allValid;
                }

                if (allValid) {
                    String orgurl = original.getText().toString();
                    tryPost(orgurl);
                } else {
                    // FormEditText are going to appear with an exclamation mark and an explicative message.
                }
            }
        });
    }

    private void tryPost(String orgurl) {
        HttpURLConnection connection;
        OutputStreamWriter request = null;

        URL url = null;
        String response = null;
        SharedPreferences  preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String host = preferences.getString("hostname", "default_value");
        String shorturl = "/add?url=";
        String longurl = "/s/add?url=";

        if (strong.isChecked()) {
            shorturl = longurl;
        } else {
        }

        try {
            url = new URL(host + shorturl + orgurl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            request = new OutputStreamWriter(connection.getOutputStream());
            request.flush();
            request.close();
            String line = "";
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString().replace("[{\"uid\":\"", "").replace("\"}]", "");
            isr.close();
            reader.close();
            original.setText(host + '/' + response);

        } catch (IOException e) {
            // Error
        }
    }
}