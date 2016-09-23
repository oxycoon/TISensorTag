package no.oxycoon.thesis.sensor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

enum DataType
{
    ACCELEROMETER_X,
    ACCELEROMETER_Y,
    ACCELEROMETER_Z,
    GYRO_X,
    GYRO_Y,
    GYRO_Z,
    BAROMETER,
    UNKNOWN
}

/**
 * Created by Elisabeth on 01.09.2016.
 *
 * Denotes a data node collected at a time by a sensor. Data can be from barometer,
 * acceleromter and/or gyro.
 */
public class Data
{
    /**
     *  Data values
     */
    private ArrayList<Float> _data;

    /**
     *  Data type at index in _data
     */
    private ArrayList<DataType> _dataType;

    /**
     *  Timestamp at which the data is recorded
     */
    private long _timestamp;

    /**
     * Constructor where only timestamp is known.
     *
     * @param timestamp
     *  Time at which the data is recorded
     */
    public Data(int timestamp)
    {
        _timestamp = timestamp;
        _data = new ArrayList<Float>();
    }

    /**
     * Constructor where the amount of data points is known.
     *
     * @param timestamp
     *  Time at which the data is recorded
     * @param size
     *  How many data points is in the record
     */
    public Data(int timestamp, int size)
    {
        _timestamp = timestamp;
        _data = new ArrayList<Float>();

        for(int i = 0; i < size; i++)
        {
            _data.add(0.0f);
            _dataType.add(DataType.UNKNOWN);
        }
    }

    /**
     * Gets data at index.
     *
     * @param index
     *  Index for the data
     * @return
     *  Value of data at index
     */
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

    public void modifyData(int index, float data, DataType type)
    {
        _data.set(index, data);
        _dataType.set(index, type);
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
