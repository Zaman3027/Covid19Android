package com.mahafuz.covid19tracker.ApiInterface;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchStateWise extends AsyncTask<Void, Void, Void> {
    String data = "";
    GetStateJSON stateJSON;

    public FetchStateWise(GetStateJSON stateJSON) {
        this.stateJSON = stateJSON;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.covid19india.org/states_daily.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
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
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("states_daily");
            stateJSON.getData(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
