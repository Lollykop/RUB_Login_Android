package com.example.lollykop.rublogin;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lollykop on 7/6/2017.
 */

public class WidgetProverder_Login extends AppWidgetProvider
{
    final String LOGIN ="LoginButtonWidget_Key";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);

        remoteViews.setOnClickPendingIntent(R.id.btn_widget_Login,onClickPendingInten(context,LOGIN));
        appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().toString()==LOGIN)
        {
            Login(context);
        }

        super.onReceive(context, intent);
    }

    public PendingIntent onClickPendingInten(Context context,String stringAction)
    {
        Intent onClickIntent = new Intent(context,WidgetProverder_Login.class);
        onClickIntent.setAction(stringAction);
        return PendingIntent.getBroadcast(context,0,onClickIntent,0);
    }

    public void Login(final Context context)
    {
        System.out.println("START TO LOGIN");
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        //this is the url where you want to send the request
        String url = "https://login.rz.ruhr-uni-bochum.de/cgi-bin/laklogin";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        System.out.println(response);
                        Toast toast;
                        if(response.contains("Authentisierung gelungen"))
                            toast = Toast.makeText(context, "Logged in!", Toast.LENGTH_SHORT);
                        else if(response.contains("loginID oder Passwort falsch"))
                            toast = Toast.makeText(context, "loginID oder Passwort falsch!", Toast.LENGTH_LONG);
                        else
                            toast = Toast.makeText(context, "unknown Error?!", Toast.LENGTH_LONG);

                        toast.show();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context, "unknown Error?!", Toast.LENGTH_LONG);
                toast.show();
           }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();

                SharedPreferences mySPR = context.getSharedPreferences("Data",0);
                String username =mySPR.getString("username","UserName");
                String password =mySPR.getString("password","Password");

                params.put("code", "1&");
                params.put("loginid", username);
                params.put("password",password);
                params.put("ipaddr", "");
                params.put("action", "Login");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}

