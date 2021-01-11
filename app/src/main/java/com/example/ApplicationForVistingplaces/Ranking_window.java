package com.example.ApplicationForVistingplaces;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Ranking_window extends FragmentActivity {

    private String jsonResult;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_window);
        listView = (ListView) findViewById(R.id.listView);
        accessWebService();
    }



    public void accessWebService(){
        JsonReadTask task = new JsonReadTask();
        task.execute(Values.URL+"getpoints.php");
    }

    public void ListDrwaer (){
        try{
            JSONObject jsonResonse = new JSONObject(jsonResult.substring(jsonResult.indexOf("{"), jsonResult.lastIndexOf("}")+1));
            JSONArray jsonMainNode = jsonResonse.optJSONArray("users");

            final ArrayList<HashMap<String,String>> RankingList = new ArrayList<>();

            HashMap<String,String> map;

            for(int i=0; i<jsonMainNode.length(); i++){
                JSONObject c = jsonMainNode.getJSONObject(i);

                map = new HashMap<>();
                map.put("place",c.getString("place"));
                map.put("user_name", c.getString("user_name"));
                map.put("points", c.getString("points"));

                RankingList.add(map);

                SimpleAdapter Adapter;
                Adapter = new SimpleAdapter(this, RankingList, R.layout.ranking_row, new String[]{"place","user_name","points"}, new int[]{R.id.Place, R.id.User_name, R.id.Points});

                listView.setAdapter(Adapter);
            }
        }catch (JSONException e){
            Toast.makeText(getApplicationContext(),"error " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    private class JsonReadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try{
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();

            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is){
            String Line;
            StringBuilder answer = new StringBuilder();
            BufferedReader RdLine = new BufferedReader(new InputStreamReader(is));
            try{
                while((Line = RdLine.readLine()) != null){
                    answer.append(Line);
                }
            }catch(IOException e){
                Toast.makeText(getApplicationContext(),"error" + e.toString(), Toast.LENGTH_LONG).show();
            }
            return  answer;
        }

        @Override
        protected void onPostExecute(String result){
            ListDrwaer();
        }
    }

}