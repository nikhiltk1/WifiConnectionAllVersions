package suventure.nikhil.com.wificonnectionsample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView wifiListView;
    WifiListAdapter wifiadapterlist;
    WifiManager wifiManager;
    public static List<ScanResult> wifiScanListCheck;
    public static AppCompatActivity context;
    Button buttonConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;



             /*For Marshmallow.*/
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.v("Location ", "build 23, secuity check ");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.v("Location ", "build 23, secuity check ");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        }

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.v("Location ", "build 23, secuity check ");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET}, 0);

        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v("Location ", "build 23, secuity check ");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;

        }



        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
        if (!marshMallowPermission.checkPermissionForWifi()) {
            marshMallowPermission.requestPermissionForWifi();
            Log.v("Home","  requesting marshmallow");
        }


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        wifiListView=(ListView)findViewById(R.id.wifi_devices_list);
        buttonConnect=(Button)findViewById(R.id.butn_connect);


        wifiManager.startScan();
        wifiScanListCheck = wifiManager.getScanResults();
        wifiadapterlist = new WifiListAdapter(context, wifiScanListCheck);
        wifiListView.setAdapter(wifiadapterlist);



    }





    static public void ConnectToWiFi(String ssid ,String key,Context ctx) {

        WifiConfiguration wifiConfig = new WifiConfiguration();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            wifiConfig.SSID=ssid;
        }else {
            wifiConfig.SSID = String.format("\"%s\"", ssid);
        }
        wifiConfig.preSharedKey = String.format("\"%s\"", key);
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(ctx.WIFI_SERVICE);
        int networkId = wifiManager.getConnectionInfo().getNetworkId();
        wifiManager.removeNetwork(networkId);
        wifiManager.saveConfiguration();
        //remember id
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }





}
