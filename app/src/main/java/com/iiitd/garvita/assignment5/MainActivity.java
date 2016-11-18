package com.iiitd.garvita.assignment5;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "DATA RETRIEVED";
    private TextView mtextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtextView = (TextView) findViewById(R.id.data);
        mtextView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void myClickHandler(View view) {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute();
        } else {
            mtextView.setText("No network connection available.");
        }
    }

    private class DownloadWebpageTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... urls) {
// params comes from the execute() call: params[0] is the url.
            try {
                //return downloadUrl(urls[0]);
                Document doc = Jsoup.connect("https://www.iiitd.ac.in/about").get();
                Elements element = doc.select("p");
                Log.d(DEBUG_TAG,doc.text());

                return(element.text());
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            mtextView.setText(result);

        }


    }
}
