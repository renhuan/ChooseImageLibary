package com.example.administrator.chooseimagelibary;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rhchooseimagelibary.UploadImage;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements UploadImage.DeleteCallBack {

    private UploadImage upImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upImage = findViewById(R.id.upImage);
        upImage.setAdapter(4, new ArrayList<>(), this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> list = upImage.onActivityResult(requestCode, resultCode, data);
        System.out.println("----" + list.toString());
    }

    @Override
    public void delete(List<String> list) {
        System.out.println("----" + list.toString());
    }
}
