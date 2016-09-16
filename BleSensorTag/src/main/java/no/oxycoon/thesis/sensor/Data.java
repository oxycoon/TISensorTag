package no.oxycoon.thesis.sensor;

import java.util.ArrayList;

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
}
