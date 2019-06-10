package alexander.sears.steamnewsdownloader;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // DownloadData will fetch xml in a background thread so we can format and display data later
    // params for AsyncTask (String - a string-based URL, Void - we don't need to track progress, String - the result will be a string of fetched XML)
    private class DownloadData extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
