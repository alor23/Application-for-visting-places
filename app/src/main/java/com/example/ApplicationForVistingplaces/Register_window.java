package com.example.ApplicationForVistingplaces;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register_window extends FragmentActivity {

    EditText user;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_window);
        user = (EditText) findViewById(R.id.user);
        pass= (EditText) findViewById(R.id.password);
    }

    public void Register(View v)
    {
        String username = user.getText().toString().trim().toLowerCase();
        String password = pass.getText().toString().trim().toLowerCase();
        register(username, password);
    }

    public void Back(View v)
    {
        Intent intent = new Intent(Register_window.this, Login_window.class);
        startActivity(intent);
    }
    private void register(String username, String password){
        String urlSuffix = "?username=" + username + "&password=" + password;
    class RegisterUser extends AsyncTask<String, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(Register_window.this, getString(R.string.wait), null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
            if(result.equals("Register success"))
            {
                Intent intent = new Intent(Register_window.this, MapsActivity.class);
                Toast.makeText(getApplicationContext(),getString(R.string.register_succes), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            else if(result.equals("Register failure"))
            {
                Toast.makeText(getApplicationContext(),getString(R.string.register_failure), Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("User in db"))
            {
                Toast.makeText(getApplicationContext(),getString(R.string.register_user_in_db), Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("Please fill fields"))
            {
                Toast.makeText(getApplicationContext(),getString(R.string.register_fill), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String parameters = params[0];
            BufferedReader bufferReader=null;
            try {
                URL url=new URL(Values.URL+"register.php"+parameters);
                HttpURLConnection con=(HttpURLConnection)url.openConnection();
                bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                String result;
                result=bufferReader.readLine();
                return  result;

            }catch (Exception e){
                return null;
            }
        }

    }
    RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix);
    }
}
