package no.oxycoon.thesis.sensor;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Elisabeth on 01.09.2016.
 */
public class DataCollection
{
    ArrayList<Data> _collection;


    DataCollection()
    {
        _collection = new ArrayList<Data>();
    }

    DataCollection(File file)
    {
        //TODO: read file to create collection
    }

}
