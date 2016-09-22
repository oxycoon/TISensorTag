package no.oxycoon.thesis.sensor;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import com.example.ti.ble.common.BluetoothLeService;
import com.example.ti.ble.common.GenericBluetoothProfile;

/**
 * Created by Elisabeth on 22.09.2016.
 */
public class FileManagerProfile extends GenericBluetoothProfile
{
    static FileManagerProfile mThis;
    String sessionName;

    public FileManagerProfile(final Context con,BluetoothDevice device,BluetoothGattService service,BluetoothLeService controller)
    {
        super(con, device, service, controller);
        this.tRow =  new FileManagerTableRow(con);
        this.tRow.setOnClickListener(null);


    }
}
