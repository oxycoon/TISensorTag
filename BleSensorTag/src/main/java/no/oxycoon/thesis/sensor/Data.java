package no.oxycoon.thesis.sensor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Elisabeth on 01.09.2016.
 */

public class Data
{
    private ArrayList<Float> _data;
    private ArrayList<DataType> _dataType;
    private long _timestamp;

    public Data(int timestamp)
    {
        _timestamp = timestamp;
        _data = new ArrayList<Float>();
        _dataType = new ArrayList<DataType>();
    }

    public Data(int timestamp, int size)
    {
        _timestamp = timestamp;
        _data = new ArrayList<Float>();
        _dataType = new ArrayList<DataType>();

        for(int i = 0; i < size; i++)
        {
            _data.add(0.0f);
            _dataType.add(DataType.NO_DATA);
        }
    }

    public Float getData(int index)
    {
        return _data.get(index);
    }

    public long getTimestamp()
    {
        return _timestamp;
    }

    public void addData(float data, DataType type)
    {
        _data.add(data);
        _dataType.add(type);
    }

    public boolean modifyData(int index, float data, DataType type)
    {
        if(_data.size() < index && _dataType.size() < index)
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

    public void insertData(int index, Data data, DataType type)
    {

    }

    public String toString()
    {
        /*Date date = new Date(_timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));

        String result = sdf.format(date);*/
        String result = _timestamp + "";

        for(Float data : _data)
        {
            result += "," + _data ;
        }
        //result += "\n";

        return result;
    }
}
