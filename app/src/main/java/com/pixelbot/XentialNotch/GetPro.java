package com.pixelbot.XentialNotch;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class GetPro extends AppCompatActivity {

    public Button probtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pro);


        probtn = findViewById(R.id.pro_button);

        probtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.pixelbot.xential_pro"));
                startActivity(intent);
            }
        });


    }
}

/*appCompany.setTypeface(Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        rateUs.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent=new Intent("android.intent.action.VIEW", Uri.parse("appCompany.setTypeface(Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.akshay.gbuattendance"));
                startActivity(intent);
            }"));
        startActivity(intent);
        } */
