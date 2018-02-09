package com.pixelbot.XentialNotch.IPhoneX;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.pixelbot.XentialNotch.R;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * Created by Akshay on 2/1/2018.
 */

public class OverlayServiceX extends Service {

    static WindowManager windowManager;
    static View view;
    final double wParam = 2;
    final double hParam = 0.24;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager =  (WindowManager) getSystemService(WINDOW_SERVICE);
        drawPortait();
    }

    public void drawPortait(){
        int overlayType = 0;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            overlayType = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        } else {
            overlayType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        view = View.inflate(getApplicationContext(), R.layout.xbump, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                (int)(wParam*DPIGetter.getYDPI()),
                (int)(hParam*DPIGetter.getXDPI()),
                // Allows the view to be on top of the StatusBar
                overlayType,
                // Keeps the button presses from going to the background window
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        // Enables the notification to recieve touch events
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

                        // Draws over status bar
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);

        params.gravity =  Gravity.TOP| Gravity.CENTER;
        windowManager.addView(view , params);
    }

    public void drawLandscape(){
        int overlayType = 0;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            overlayType = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        } else {
            overlayType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        view = View.inflate(getApplicationContext(), R.layout.xbump_l, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                (int)(hParam*DPIGetter.getYDPI()),
                (int)(wParam*DPIGetter.getXDPI()),
                // Allows the view to be on top of the StatusBar
                overlayType,
                // Keeps the button presses from going to the background window
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity =  Gravity.START;
        windowManager.addView(view , params);
    }

    public static void removeView(){
        if(view!=null && windowManager!=null){
            windowManager.removeView(view);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == ORIENTATION_LANDSCAPE){
            removeView();
            drawLandscape();
        } else if (newConfig.orientation == ORIENTATION_PORTRAIT){
            removeView();
            drawPortait();
        }
    }
}
