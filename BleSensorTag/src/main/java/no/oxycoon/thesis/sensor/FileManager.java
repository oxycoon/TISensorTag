package no.oxycoon.thesis.sensor;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Elisabeth on 17.09.2016.
 */
public class FileManager implements Serializable
{
    private final String LOG_TAG = "SensorTagFileManager";

    private Context _context;

    public FileManager(Context context)
    {
        _context = context;
    }

    public boolean writeFile(DataCollection collection)
    {
        return writeFile(collection, false);
    }

    public boolean writeFile(DataCollection collection, boolean append)
    {
        return writeFile(collection.getName(), collection.getAllData(), append);
    }

    public boolean writeFile(String name, ArrayList<Data> data, boolean append)
    {
        if (name.equals(""))
        {
            name = "SensorTag_";
        }
        if(data.size() > 0)
            name += "_" + data.get(0).getTimestamp() + ".csv";
        else
            name += "_TEST.csv";

        if(writeExternalFile(name, data, append))
        {
            return true;
        }
        else
        {
            return writeInternalFile(name, data, append);
        }
    }


    private boolean writeInternalFile(String name, ArrayList<Data> data)
    {
        return writeInternalFile(name, data, false);
    }

    private boolean writeInternalFile(String name, ArrayList<Data> data, boolean append)
    {
        FileOutputStream outputStream;

        name += ".csv";

        String string = "";

        if(data.size() > 0)
        {
            for (Data d : data)
            {
                string += data.toString() + "\n";
            }
        }
        else
        {
            string += "THIS IS A TEST";
        }

        try
        {
            if(append)
            {
                outputStream = _context.openFileOutput(name, Context.MODE_PRIVATE | Context.MODE_APPEND);
            }
            else
            {
                outputStream = _context.openFileOutput(name, Context.MODE_PRIVATE);
            }
            outputStream.write(string.getBytes());
            outputStream.flush();
            outputStream.close();
            Log.d(LOG_TAG, "File written internally.");
            return true;
        }
        catch (IOException e)
        {
            Log.d(LOG_TAG, "File writing failed: " + e.getMessage());
            return false;
        }
    }

    private boolean writeExternalFile(String name, ArrayList<Data> data)
    {
        return writeExternalFile(name, data, false);
    }

    private boolean writeExternalFile(String name, ArrayList<Data> data, boolean append)
    {
        if (isExternalStorageWritable())
        {
            if (!externalDirectoryExists())
            {
                Log.e(LOG_TAG, "External storage folder does not exist.");
                return false;
            }
            File output = new File(_context.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS + "/SensorTag"), name);

            String string = "";

            if(data.size() > 0)
            {
                for (Data d : data)
                {
                    string += data.toString() + "\n";
                }
            }
            else
            {
                string += "THIS IS A TEST";
            }

            try
            {
                //output.createNewFile();
                FileWriter fw = new FileWriter(output, append);
                if(append) fw.append(string);
                else fw.write(string);
                fw.flush();
                fw.close();
                Log.d(LOG_TAG, "File written externally.");
                return true;
            }
            catch (IOException e)
            {
                Log.d(LOG_TAG, "File writing failed: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        else
        {
            Log.e(LOG_TAG, "External storage is not writable.");
            return false;
        }
    }

    private boolean externalDirectoryExists()
    {
        File dir = new File(_context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), "SensorTag");
        if (!dir.exists())
        {
            if (!dir.mkdirs())
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
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }
        return false;
    }

    private boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return true;
        }
        return false;
    }
}
