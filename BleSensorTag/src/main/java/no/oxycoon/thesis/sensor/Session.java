package no.oxycoon.thesis.sensor;

/**
 * Created by Elisabeth on 23.09.2016.
 */
public class Session
{
    private FileManager     _fm;
    private boolean         _isRecording;
    private String          _name;

    public Session()
    {

    }

    public boolean isRecording()
    {
        return _isRecording;
    }

    public void toggleRecording()
    {
        _isRecording = _isRecording ? true: false;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }
}
