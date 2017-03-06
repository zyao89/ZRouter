package com.zyao89.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zyao89.zrouter.annotation.ZRouter;

@ZRouter(name = "HAHA1")
public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
