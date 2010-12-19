package net.dara.mlproject.resources;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;

public class RecordedMemoResource extends ServerResource
{
    public static final String RECORDEDMEMO_RESPONSE_KEY = "voicememo.recordedmemo.response";

    //@Get("xml")
    @Post("xml")
    public String recordedMemo()
    {
        //return System.getProperty(RECORDEDMEMO_RESPONSE_KEY);
        return ("<Response>\n"+
                "   <Say>Your memo has been recorded.</Say>\n"+
                "   <Play>"+getRequest().getEntityAsForm().getFirstValue("RecordingUrl")+"</Play>\n"+
                "   <Say>Goodbye.</Say>\n"+
                "</Response>");
    }
}
