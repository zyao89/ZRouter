package com.zyao89.zrouter.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zyao89.zrouter.annotation.ZRouter;
import com.zyao89.zrouter.auto.ZRouter$$Group$$undefined$$Zyao89;

@ZRouter(name = "HAHA")
public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
