package com.example.lollykop.rublogin;

import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.duration;

public class MainActivity extends AppCompatActivity
{

    Button btn;
    TextView username;
    TextView password;
    CheckBox autologin;
    SharedPreferences mySPR;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn_save);
        username = (TextView) findViewById(R.id.t_username);
        password = (TextView) findViewById(R.id.t_password);
        autologin= (CheckBox) findViewById(R.id.cbAutoLogin);


        mySPR = getSharedPreferences("Data",0);

        username.setText(mySPR.getString("username","UserName"));
        password.setText(mySPR.getString("password","Password"));
        autologin.setChecked(mySPR.getBoolean("autologin",false));

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPreferences.Editor editor = mySPR.edit();
                editor.putString("username",username.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.putBoolean("autologin",autologin.isChecked());
                editor.commit();

                Toast toast = Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }
}
