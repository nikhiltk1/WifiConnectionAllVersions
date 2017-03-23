package suventure.nikhil.com.wificonnectionsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

/**
 * Created by nikhil on 20/12/16.
 */
public class WifiListAdapter extends BaseAdapter {

    private Activity c;
    private List<ScanResult> wifiList;
    public static String wifiSSID;
    private String TAG = WifiListAdapter.class.getSimpleName();
    public static String passwordText;

    private ProgressDialog progressDialog;

    public WifiListAdapter(Activity context, List<ScanResult> wifi) {
        c = context;
        wifiList = wifi;
    }

    @Override
    public int getCount() {
        return wifiList.size();
    }

    @Override
    public Object getItem(int position) {
        return wifiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;


        final ScanResult scanResult = wifiList.get(position);
        if (v == null) {
            try {
                LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.wifi_device_list, parent, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final TextView wifiName = (TextView) v.findViewById(R.id.wifi_device_name);
        final RelativeLayout wifiLayout = (RelativeLayout) v.findViewById(R.id.rl_wifi_name);


        if (wifiList.get(position).SSID.toString() != null && !(wifiList.get(position).SSID.toString().equals(""))) {

            wifiName.setText(wifiList.get(position).SSID.toString());

        }

        wifiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiSSID = wifiName.getText().toString().trim();


                //end
                showPasswordAlert(scanResult);
            }
        });


        return v;
    }


    private void showPasswordAlert(final ScanResult scanResult) {

        final AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogNick = inflater.inflate(R.layout.wifi_password_layout, null);

        final EditText password = (EditText) dialogNick.findViewById(R.id.edit_text_password);
        final TextView wifi_name = (TextView) dialogNick.findViewById(R.id.txt_wifi_name);
        TextView cancel = (TextView) dialogNick.findViewById(R.id.btn_cancel);
        TextView connect = (TextView) dialogNick.findViewById(R.id.btn_connect);

        ImageView imageShowPassword = (ImageView) dialogNick.findViewById(R.id.image_show_old_password);
        wifi_name.setText(wifiSSID);


        imageShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (password.getTransformationMethod() == null) {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    password.setTransformationMethod(null);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passwordText=password.getText().toString();
                connectToWifi1(wifiSSID,passwordText,c);


                alertDialog.cancel();

            }


        });

        alertDialog.setView(dialogNick);
        if (alertDialog.isShowing())
            alertDialog.cancel();
        alertDialog.show();
    }

    private boolean  connectToWifi(String SSID_name,String password,Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wificonfiguration = new WifiConfiguration();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            wificonfiguration.SSID=SSID_name;
        }else {
            wificonfiguration.SSID = String.format("\"%s\"", SSID_name);
        }

        wificonfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wificonfiguration.allowedAuthAlgorithms.set(0);
        wificonfiguration.status = 2;
        wificonfiguration.preSharedKey = "\"" + password + "\"";

        int networkId = wifiManager.addNetwork(wificonfiguration);

        if (networkId > -1) {

            boolean status;
            wifiManager.enableNetwork(networkId, true);

            Log.v(TAG, "loli reconnecting");
            status=wifiManager.reconnect();
            wifiManager.disconnect();
            return status;
        }
        return false;

    }



    public void connectToWifi1(String ssid ,String key,Context ctx) {

        WifiConfiguration wifiConfig = new WifiConfiguration();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            wifiConfig.SSID=ssid;
        }else {
            wifiConfig.SSID = String.format("\"%s\"", ssid);
        }
        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiConfig.preSharedKey = String.format("\"%s\"", key);
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
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
