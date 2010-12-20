package net.dara.mlproject.resources;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;

public class StartMemoResource extends ServerResource
{
    public static final String STARTMEMO_RESPONSE_KEY = "voicememo.startmemo.response";

    //@Post("xml")
    //public String startMemo()
    //{
    //    return System.getProperty(STARTMEMO_TWIML_KEY);
    //}

    @Post("xml")
    public String hello(Representation repr)
    {
        return "If this worked, it means the problem is with the XML?";
        //return ("<Response>\n"+
        //        "   <Say>Hello. Please start recording your voice memo after the beep. Press # key when done.</Say>\n"+
        //        "   <Record action=\"voicememo\" maxLength=\"#\" finishOnKey=\"#\"/>\n"+
        //        "</Response>");
    }
}
