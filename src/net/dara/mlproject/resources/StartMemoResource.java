package net.dara.mlproject.resources;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class StartMemoResource extends ServerResource
{
    public static final String STARTMEMO_RESPONSE_KEY = "voicememo.startmemo.response";

    @Post
    public Representation startMemo(Representation repr)
    {
        //return new StringRepresentation(System.getProperty(STARTMEMO_TWIML_KEY),
        //        MediaType.APPLICATION_XML);
        Representation result = new StringRepresentation(("<Response>\n"+
                "   <Say>Hello. Please start recording your voice memo after the beep. Press # key when done.</Say>\n"+
                "   <Record action=\"voicememo\" maxLength=\"#\" finishOnKey=\"#\"/>\n"+
                "</Response>"), MediaType.APPLICATION_XML);
        return result;
    }
}
