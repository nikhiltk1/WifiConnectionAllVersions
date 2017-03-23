package suventure.nikhil.com.wificonnectionsample;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class MarshMallowPermission {

    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int WIFI_PERMISSION_REQUEST_CODE = 3;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 4;
    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 5;
    Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissionForRecord(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermissionForExternalStorage(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }


    public boolean checkPermissionForCamera(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},0);
            return false;
        }
    }
    public void requestPermissionForCamera(){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);

    }

    public boolean checkPermissionForNotification(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission_group.SMS);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            //ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission_group.SMS},NOTIFICATION_PERMISSION_REQUEST_CODE);
            return false;
        }
    }

   /* if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        Log.v("Location ", "build 23, secuity check ");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
   */
    public boolean checkPermissionForWifi()
    {
        Log.v("MarshMallowPermission","  checkPermissionForWifi");
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE);
        if (result == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(activity, Manifest.permission.CHANGE_WIFI_STATE)==PackageManager.PERMISSION_GRANTED)
            {
                Log.v("MarshMallowPermission","  checkPermissionForWifi1 true");
                return true;
            }else {
                Log.v("MarshMallowPermission","  checkPermissionForWifi2 false");
                return false;
            }

        } else {
            Log.v("MarshMallowPermission","  checkPermissionForWifi3 false");
            //ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission_group.SMS},NOTIFICATION_PERMISSION_REQUEST_CODE);
            return false;
        }

    }

    public void requestPermissionForWifi(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_WIFI_STATE)){
            Toast.makeText(activity, "Wifi access permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_WIFI_STATE},WIFI_PERMISSION_REQUEST_CODE);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CHANGE_WIFI_STATE)){
            Toast.makeText(activity, "Wifi access permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CHANGE_WIFI_STATE},WIFI_PERMISSION_REQUEST_CODE);
        }

    }
    public void requestPermissionForNotification(){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission_group.SMS},NOTIFICATION_PERMISSION_REQUEST_CODE);

    }



    public void requestPermissionForRecord(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)){
           Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.RECORD_AUDIO},RECORD_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForExternalStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(activity, "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }



    /*public void requestPermissionForCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)){
            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
        }
    }*/
}
