package net.dara.mlproject.resources;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class StartMemoResource extends ServerResource
{
    public static final String STARTMEMO_RESPONSE_KEY = "voicememo.startmemo.response";

    //@Post("xml")
    //public String startMemo()
    //{
    //    return System.getProperty(STARTMEMO_TWIML_KEY);
    //}

    @Get("xml")
    public String hello()
    {
        return ("<Response>\n"+
                "   <Say>Hello. Please start recording your voice memo after the beep. Press # key when done.</Say>\n"+
                "   <Record action=\"voicememo\" maxLength=\"#\" finishOnKey=\"#\"/>\n"+
                "</Response>");
    }
}
