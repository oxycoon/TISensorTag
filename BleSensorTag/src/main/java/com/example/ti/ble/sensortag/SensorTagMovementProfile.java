/**************************************************************************************************
 Filename:       SensorTagMovementProfile.java

 Copyright (c) 2013 - 2015 Texas Instruments Incorporated

 All rights reserved not granted herein.
 Limited License.

 Texas Instruments Incorporated grants a world-wide, royalty-free,
 non-exclusive license under copyrights and patents it now or hereafter
 owns or controls to make, have made, use, import, offer to sell and sell ("Utilize")
 this software subject to the terms herein.  With respect to the foregoing patent
 license, such license is granted  solely to the extent that any such patent is necessary
 to Utilize the software alone.  The patent license shall not apply to any combinations which
 include this software, other than combinations with devices manufactured by or for TI ('TI Devices').
 No hardware patent is licensed hereunder.

 Redistributions must preserve existing copyright notices and reproduce this license (including the
 above copyright notice and the disclaimer and (if applicable) source code license limitations below)
 in the documentation and/or other materials provided with the distribution

 Redistribution and use in binary form, without modification, are permitted provided that the following
 conditions are met:

 * No reverse engineering, decompilation, or disassembly of this software is permitted with respect to any
 software provided in binary form.
 * any redistribution and use are licensed by TI for use only with TI Devices.
 * Nothing shall obligate TI to provide you with source code for the software licensed and provided to you in object code.

 If software source code is provided to you, modification and redistribution of the source code are permitted
 provided that the following conditions are met:

 * any redistribution and use of the source code, including any resulting derivative works, are licensed by
 TI for use only with TI Devices.
 * any redistribution and use of any object code compiled from the source code and any resulting derivative
 works, are licensed by TI for use only with TI Devices.

 Neither the name of Texas Instruments Incorporated nor the names of its suppliers may be used to endorse or
 promote products derived from this software without specific prior written permission.

 DISCLAIMER.

 THIS SOFTWARE IS PROVIDED BY TI AND TI'S LICENSORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL TI AND TI'S LICENSORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.


 **************************************************************************************************/
package com.example.ti.ble.sensortag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.CompoundButton;

import com.example.ti.ble.common.BluetoothLeService;
import com.example.ti.ble.common.GattInfo;
import com.example.ti.ble.common.GenericBluetoothProfile;
import com.example.ti.util.Point3D;

import no.oxycoon.thesis.sensor.Data;
import no.oxycoon.thesis.sensor.DataType;

public class SensorTagMovementProfile extends GenericBluetoothProfile {
	public static final String SENSORTAG_MOVEMENT = "com.example.ti.ble.sensortag.sensortagmovementprofile.SENSORTAG_MOVEMENT";
	
	public SensorTagMovementProfile(Context con,BluetoothDevice device,BluetoothGattService service,BluetoothLeService controller) {
		super(con,device,service,controller);
		this.tRow =  new SensorTagMovementTableRow(con);
		
		List<BluetoothGattCharacteristic> characteristics = this.mBTService.getCharacteristics();
		
		for (BluetoothGattCharacteristic c : characteristics) {
			if (c.getUuid().toString().equals(SensorTagGatt.UUID_MOV_DATA.toString())) {
				this.dataC = c;
			}
			if (c.getUuid().toString().equals(SensorTagGatt.UUID_MOV_CONF.toString())) {
				this.configC = c;
			}
			if (c.getUuid().toString().equals(SensorTagGatt.UUID_MOV_PERI.toString())) {
				this.periodC = c;
			}
		}
		
		
		this.tRow.setIcon(this.getIconPrefix(), this.dataC.getUuid().toString());
		
		this.tRow.title.setText(GattInfo.uuidToName(UUID.fromString(this.dataC.getUuid().toString())));
		this.tRow.uuidLabel.setText(this.dataC.getUuid().toString());
		this.tRow.value.setText("X:0.00G, Y:0.00G, Z:0.00G");
		SensorTagMovementTableRow row = (SensorTagMovementTableRow)this.tRow;
		
		row.gyroValue.setText("X:0.00'/s, Y:0.00'/s, Z:0.00'/s");
		row.magValue.setText("X:0.00mT, Y:0.00mT, Z:0.00mT");
        row.WOS.setChecked(false); // MUST BE SET TO FALSE FOR THIS TO MODULE TO WORK ~~OXYCOON
        row.WOS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                byte b[] = new byte[] {0x7F,0x00};
                if (isChecked) {
                    b[0] = (byte)0xFF;
                }
                int error = mBTLeService.writeCharacteristic(configC, b);
                if (error != 0) {
                    if (configC != null)
                        Log.d("SensorTagMovementProfil","Sensor config failed: " + configC.getUuid().toString() + " Error: " + error);
                }
            }
        });
		this.tRow.periodBar.setProgress(100);
	}
	
	public static boolean isCorrectService(BluetoothGattService service) {
		if ((service.getUuid().toString().compareTo(SensorTagGatt.UUID_MOV_SERV.toString())) == 0) {
			return true;
		}
		else return false;
	}
	@Override 
	public void enableService() {
        byte b[] = new byte[] {0x7F,0x00};
        SensorTagMovementTableRow row = (SensorTagMovementTableRow)this.tRow;
        if (row.WOS.isChecked()) b[0] = (byte)0xFF;
        int error = mBTLeService.writeCharacteristic(this.configC, b);
        if (error != 0) {
            if (this.configC != null)
            Log.d("SensorTagMovementProfil","Sensor config failed: " + this.configC.getUuid().toString() + " Error: " + error);
        }
        error = this.mBTLeService.setCharacteristicNotification(this.dataC, true);
        if (error != 0) {
            if (this.dataC != null)
            Log.d("SensorTagMovementProfil","Sensor notification enable failed: " + this.configC.getUuid().toString() + " Error: " + error);
        }

		this.periodWasUpdated(100);
        this.isEnabled = true;
	}
	@Override 
	public void disableService() {
        int error = mBTLeService.writeCharacteristic(this.configC, new byte[] {0x00,0x00});
        if (error != 0) {
            if (this.configC != null)
            Log.d("SensorTagMovementProfil","Sensor config failed: " + this.configC.getUuid().toString() + " Error: " + error);
        }
        error = this.mBTLeService.setCharacteristicNotification(this.dataC, false);
        if (error != 0) {
            if (this.dataC != null)
            Log.d("SensorTagMovementProfil","Sensor notification disable failed: " + this.configC.getUuid().toString() + " Error: " + error);
        }
        this.isEnabled = false;
	}
	public void didWriteValueForCharacteristic(BluetoothGattCharacteristic c) {
		
	}
	public void didReadValueForCharacteristic(BluetoothGattCharacteristic c) {
		
	}
	@Override
    public void didUpdateValueForCharacteristic(BluetoothGattCharacteristic c) {
        byte[] value = c.getValue();
			if (c.equals(this.dataC)){
				Point3D v;
				v = Sensor.MOVEMENT_ACC.convert(value);
				if (this.tRow.config == false) this.tRow.value.setText(Html.fromHtml(String.format("<font color=#FF0000>X:%.2fG</font>, <font color=#00967D>Y:%.2fG</font>, <font color=#00000>Z:%.2fG</font>", v.x, v.y, v.z)));
				this.tRow.sl1.addValue((float)v.x);
				this.tRow.sl2.addValue((float)v.y);
				this.tRow.sl3.addValue((float)v.z);
				v = Sensor.MOVEMENT_GYRO.convert(value);
				SensorTagMovementTableRow row = (SensorTagMovementTableRow)this.tRow;
				row.gyroValue.setText(Html.fromHtml(String.format("<font color=#FF0000>X:%.2f°/s</font>, <font color=#00967D>Y:%.2f°/s</font>, <font color=#00000>Z:%.2f°/s</font>", v.x, v.y, v.z)));
				row.sl4.addValue((float)v.x);
				row.sl5.addValue((float)v.y);
				row.sl6.addValue((float)v.z);
				v = Sensor.MOVEMENT_MAG.convert(value);
				row.magValue.setText(Html.fromHtml(String.format("<font color=#FF0000>X:%.2fuT</font>, <font color=#00967D>Y:%.2fuT</font>, <font color=#00000>Z:%.2fuT</font>", v.x, v.y, v.z)));
				row.sl7.addValue((float)v.x);
				row.sl8.addValue((float)v.y);
				row.sl9.addValue((float)v.z);
			}
	}
    @Override
    public Map<String,String> getMQTTMap() {
        Point3D v = Sensor.MOVEMENT_ACC.convert(this.dataC.getValue());
        Map<String,String> map = new HashMap<String, String>();
        map.put("acc_x",String.format("%.2f",v.x));
        map.put("acc_y",String.format("%.2f",v.y));
        map.put("acc_z",String.format("%.2f",v.z));
        v = Sensor.MOVEMENT_GYRO.convert(this.dataC.getValue());
        map.put("gyro_x",String.format("%.2f",v.x));
        map.put("gyro_y",String.format("%.2f",v.y));
        map.put("gyro_z",String.format("%.2f",v.z));
        v = Sensor.MOVEMENT_MAG.convert(this.dataC.getValue());
        map.put("compass_x",String.format("%.2f",v.x));
        map.put("compass_y",String.format("%.2f",v.y));
        map.put("compass_z",String.format("%.2f",v.z));
        return map;
    }

	@Override
	public Data getData()
	{
		java.util.Date date = new java.util.Date();
		long time = date.getTime();

		Data d = new Data(time, 10);
		Point3D v = Sensor.MOVEMENT_ACC.convert(this.dataC.getValue());
		d.modifyData(0, v.x, DataType.ACCEL_X);
		d.modifyData(1, v.y, DataType.ACCEL_Y);
		d.modifyData(2, v.z, DataType.ACCEL_Z);

		v = Sensor.MOVEMENT_GYRO.convert(this.dataC.getValue());
		d.modifyData(3, v.x, DataType.GYR_X);
		d.modifyData(4, v.y, DataType.GYR_Y);
		d.modifyData(5, v.z, DataType.GYR_Z);

		v = Sensor.MOVEMENT_MAG.convert(this.dataC.getValue());
		d.modifyData(6, v.x, DataType.COM_X);
		d.modifyData(7, v.y, DataType.COM_Y);
		d.modifyData(8, v.z, DataType.COM_Z);


		return d;
	}
}
