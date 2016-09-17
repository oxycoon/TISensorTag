package no.oxycoon.thesis.sensor;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Oxycoon on 17.09.2016.
 */
public class FileManager
{
    public FileManager()
    {

    }

    public boolean writeFile(String filename, ArrayList<Data> data)
    {



        //TODO: check if external storage is available
        //      if not - save internally.

        return true;
    }


    private boolean writeInternalFile(String name, ArrayList<Data> data)
    {
        File file;
        FileOutputStream outputStream;

        String string = "";

        for(Data d : data)
        {
            string += data.toString() + "\n";
        }

        try
        {
            outputStream = openFileOutput(name, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        }
        catch (IOException e)
        {
            Log.d("SensorTagFileMangaer", "File writing failed: " + e.getMessage());
        }
    }
}
