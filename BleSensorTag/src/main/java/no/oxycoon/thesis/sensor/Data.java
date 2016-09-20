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
    private long _timestamp;

    public Data(int timestamp)
    {
        _timestamp = timestamp;
    }

    public Float getData(int index)
    {
        return _data.get(index);
    }

    public long getTimestamp()
    {
        return _timestamp;
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
