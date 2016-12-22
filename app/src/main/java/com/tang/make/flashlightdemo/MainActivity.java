package com.tang.make.flashlightdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FlashLightUtils.hasFlash(MainActivity.this))
                {
                    Toast.makeText(MainActivity.this,"not flashlight",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,FBService.class);
                startService(intent);
            }
        });
    }
}
