package no.oxycoon.thesis.sensor;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Elisabeth on 01.09.2016.
 */

public class Data
{
    private static final String LOG_TAG = "no.oxycoon.thesis.data";

    private ArrayList<Double> _data;
    private ArrayList<DataType> _dataType;
    private long _timestamp;

    public Data()
    {
        _timestamp = -1;
        _data = new ArrayList<Double>();
        _dataType = new ArrayList<DataType>();
    }

    public Data(long timestamp)
    {
        _timestamp = timestamp;
        _data = new ArrayList<Double>();
        _dataType = new ArrayList<DataType>();
    }

    public Data(long timestamp, int size)
    {
        _timestamp = timestamp;
        _data = new ArrayList<Double>();
        _dataType = new ArrayList<DataType>();

        for(int i = 0; i < size; i++)
        {
            _data.add(0.0);
            _dataType.add(DataType.NO_DATA);
        }
    }

    public Double getData(int index)
    {
        return _data.get(index);
    }
    public DataType getDataType(int index) { return _dataType.get(index);}
    public long getTimestamp()
    {
        return this._timestamp;
    }
    public void setTimestamp(long timestamp) { this._timestamp = timestamp;}

    public ArrayList<DataType> getDataTypes(){return _dataType;}

    public void setDataSize(int size)
    {
        Log.d(LOG_TAG, "Request new size: " + size + " from old size: " + _data.size());
        if(_data.size() < size)
        {
            for(int i = (size - _data.size()); i < size; i++)
            {
                _data.add(0.0);
                _dataType.add(DataType.NO_DATA);
            }
        }
        else if(_data.size() > size)
        {
            for(int i = (_data.size() - size); i > size; i--)
            {
                _data.remove(i);
                _dataType.remove(i);
            }
        }
        Log.d(LOG_TAG, "Resize complete, new size: " + _data.size());
    }

    public void addData(double data, DataType type)
    {
        _data.add(data);
        _dataType.add(type);
    }

    public boolean modifyData(int index, double data, DataType type)
    {
        if(_data.size() > index && _dataType.size() > index)
        {
            _data.set(index, data);
            _dataType.set(index, type);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void insertData(Data data)
    {
        this.insertData(data, true);
    }

    public void insertData(Data data, boolean overwrite)
    {
        boolean checker = true;
        int index = 0;

        for(int i = 0; i < data.getDataTypes().size(); i++)
        {
            for(int j = 0; j < this._dataType.size(); j++)
            {
                if(data.getDataType(i) == this.getDataType(j))
                {
                    checker = false;
                    index = j;
                    break;
                }
            }
            if(checker)
            {
                this.addData(data.getData(i), data.getDataType(i));
            }
            else if(!checker && overwrite)
            {
                this.modifyData(index, data.getData(i), data.getDataType(i));
            }
            else
            {
                Log.d("Oxycoon_DATA", "Duplicate file type entry detected and overwrite not enabled!");
            }
            index = 0;
            checker = true;
        }
    }

    private void restructureData()
    {
        
    }

    public String toString()
    {
        /*Date date = new Date(_timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));

        String result = sdf.format(date);*/
        String result = _timestamp + "";
        /*result += "," + _data + "\n";*/

        for(double data : _data)
        {
            result += "," + data ;
        }
        //result += "\n";

        return result;
    }
}
