package com.mahafuz.covid19tracker.ApiInterface;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<Void, Void, Void> {
    String data = "";
    GetJSONString getJSONString;

    public FetchData(GetJSONString getJSONString) {
        this.getJSONString = getJSONString;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.covid19india.org/data.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        getJSONString.getData(this.data);
    }
}
