package com.pixelbot.XentialNotch.Essential;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.pixelbot.XentialNotch.AboutUs;
import com.pixelbot.XentialNotch.GetPro;
import com.pixelbot.XentialNotch.R;

import java.util.List;

/**
 * Created by Akshay on 1/31/2018.
 */

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private WindowManager windowManager;
    private BillingClient mBillingClient;
    Switch aSwitch;
    Typeface tf;
    TextView tx4;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.essential);
        windowManager =  (WindowManager) getSystemService(WINDOW_SERVICE);

        mBillingClient = BillingClient.newBuilder(this).setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

            }
        }).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.
                }
            }
            @Override
            public void onBillingServiceDisconnected() {

            }
        });

        tx4 = findViewById(R.id.textView3);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ABeeZee-Regular.ttf");
        tx4.setTypeface(tf);


        final SharedPreferences sharedPreference = getPreferences(MODE_PRIVATE);
        aSwitch = (Switch) findViewById(R.id.switch_ph1);
        aSwitch.setChecked(sharedPreference.getBoolean("a", false));

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreference.edit().putBoolean("a", b).apply();

                if (b == true) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.canDrawOverlays(getApplicationContext())) {
                            start();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please permit drawing over apps.", Toast.LENGTH_SHORT).show();
                            checkDrawOverlayPermission();
                        }
                    }
                } else {
                    stop();
                }

            }
        });


       /* final Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        Button drawOver = findViewById(R.id.drawover);
        Button donate = findViewById(R.id.donate);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (Settings.canDrawOverlays(getApplicationContext())){
                        start();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please permit drawing over apps.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    start();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });

        drawOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    checkDrawOverlayPermission();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't need it.\nYou run Android lower than 6.0.\nClick \"Start\" and enjoy!", Toast.LENGTH_LONG).show();
                }
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBillingClient.consumeAsync("", new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(int responseCode, String purchaseToken) {

                    }
                });
                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSku("1_dollar_donation")
                        .setType(BillingClient.SkuType.INAPP)
                        .build();
                mBillingClient.launchBillingFlow(MainActivity.this, flowParams);
                Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
                ConsumeResponseListener listener = new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
                        if (responseCode == BillingClient.BillingResponse.OK) {
                            // Handle the success of the consume operation.
                            // For example, increase the number of coins inside the user's basket.
                        }
                    }};
                for (int i = 0; i<purchasesResult.getPurchasesList().size(); i++){
                    mBillingClient.consumeAsync(purchasesResult.getPurchasesList().get(i).getPurchaseToken(), listener);
                }
            }
        });

        */

        mAdView = findViewById(R.id.ad5);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.test_device_id))
                .addTestDevice("654C0A12D3CAECD60B5E45D1B80DA1C4")
                .build();
        mAdView.loadAd(adRequest);
    }

    public void start(){
        if(!isMyServiceRunning(OverlayService.class)){
            startService(new Intent(this, OverlayService.class));
            showFullScreenAd();
        }
    }

    public void stop(){
        if(isMyServiceRunning(OverlayService.class)){
            stopService(new Intent(this, OverlayService.class));
            OverlayService.removeView();
            showFullScreenAd();
        }
    }

    /** code to post/handler request for permission */
    public final static int REQUEST_CODE = 3000;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "Couldn't find overlay premission activity.\nPlease permit drawing over apps manually through the device settings.", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                // continue here - permission was granted
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.about_menu:
                Intent p = new Intent(MainActivity.this, AboutUs.class);
                startActivity(p);
                showFullScreenAd();

                break;
            case R.id.donate_menu:
                Intent i = new Intent(MainActivity.this, GetPro.class);
                startActivity(i);
                showFullScreenAd();

                break;
            case R.id.rate_menu:
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.pixelbot.XentialNotch"));
                startActivity(intent);
                showFullScreenAd();
        }
        return true;
    }

    public void showFullScreenAd(){
        final InterstitialAd mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.test_device_id))
                .addTestDevice("654C0A12D3CAECD60B5E45D1B80DA1C4")
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
