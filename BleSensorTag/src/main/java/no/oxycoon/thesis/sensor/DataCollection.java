package no.oxycoon.thesis.sensor;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Elisabeth on 01.09.2016.
 */
public class DataCollection implements Serializable
{
    private ArrayList<Data> _collection;
    private String _name;

    public DataCollection(String name)
    {
        _collection = new ArrayList<Data>();
        _name = name;
    }

    public DataCollection(File file)
    {
        //TODO: read file to create collection
    }

    public void clearCollection()
    {
        _collection.clear();
        _name = "";
    }

    public void addToCollection(Data data)
    {
        _collection.add(data);
    }

    public ArrayList<Data> getAllData()
    {
        return _collection;
    }

    public Data getData(int index)
    {
        return _collection.get(index);
    }

    public String getName()
    {
        return _name;
    }

    public int getSize()
    {
        return _collection.size();
    }
}
