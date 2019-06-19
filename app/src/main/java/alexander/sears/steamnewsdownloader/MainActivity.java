package alexander.sears.steamnewsdownloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: starting Asynctask");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://store.steampowered.com/feeds/news.xml");
        Log.d(TAG, "onCreate: done");
    }

    // DownloadData will fetch xml in a background thread so we can format and display data later
    // params for AsyncTask (String - a string-based URL, Void - we don't need to track progress, String - the result will be a string of fetched XML)
    private static class DownloadData extends AsyncTask<String, Void, String>{

        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d(TAG, "OnPostExecute: parameter is " +s);
            ParseNewsArticles parseNewsArticles = new ParseNewsArticles();
            parseNewsArticles.parse(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with "+strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if(rssFeed == null){
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath){
            StringBuilder xmlResult = new StringBuilder();

            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was "+response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsRead;
                char[] inputBuffer = new char[500];
                while(true){
                    charsRead = reader.read(inputBuffer);
                    if(charsRead < 0){
                        break;
                    }
                    if(charsRead > 0){
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();
                return xmlResult.toString();
            }catch(MalformedURLException e){
                Log.e(TAG, "downloadXML: Invalid URL "+ e.getMessage());
            }catch(IOException e){
                Log.e(TAG, "downloadXML: IO Exception reading data: "+e.getMessage());
            }catch(SecurityException e){
                Log.e(TAG, "downloadXML: Security Exception: "+e.getMessage());
            }
            return null;
        }
    }
}
