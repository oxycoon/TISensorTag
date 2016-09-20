package no.oxycoon.thesis.sensor;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Elisabeth on 17.09.2016.
 */
public class FileManager
{
    private final String LOG_TAG = "SensorTagFileManager";

    private Context _context;

    public FileManager(Context context)
    {
        _context = context;
    }

    public boolean writeFile(String filename, ArrayList<Data> data)
    {



        //TODO: check if external storage is available
        //      if not - save internally.

        return true;
    }


    private boolean writeInternalFile(String name, ArrayList<Data> data)
    {
        //File file = new File(_context.getFilesDir(), name);
        FileOutputStream outputStream;

        name += ".csv";

        String string = "";

        for(Data d : data)
        {
            string += data.toString() + "\n";
        }

        try
        {
            outputStream = _context.openFileOutput(name, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
            return true;
        }
        catch (IOException e)
        {
            Log.d("SensorTagFileMangaer", "File writing failed: " + e.getMessage());
            return false;
        }
    }

    private boolean writeExternalFile(String name, ArrayList<Data> data)
    {
        if(name.equals(""))
        {
            name = "SensorTag_" + data.get(0).getTimestamp();
        }
        name += ".csv";

        if(isExternalStorageWritable())
        {
            if(!externalDirectoryExists())
            {
                return false;
            }
            File output = new File(_context.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS + "/SensorTag"), name);

            FileOutputStream outputStream = new FileOutputStream(output);



            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean externalDirectoryExists()
    {
        File dir = new File(_context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), "SensorTag");
        if(!dir.exists())
        {
            if(!dir.mkdirs())
            {
                Log.e(LOG_TAG, "Directory not created");
                return false;
            }
        }
        return true;
    }

    private boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }
        return false;
    }

    private boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) ||
           Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return true;
        }
        return false;
    }
}
