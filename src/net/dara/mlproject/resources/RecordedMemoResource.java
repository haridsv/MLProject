package net.dara.mlproject.resources;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.Representation;

public class RecordedMemoResource extends ServerResource
{
    public static final String RECORDEDMEMO_RESPONSE_KEY = "voicememo.recordedmemo.response";

    @Post
    public String recordedMemo(Representation repr)
    {
        // return new StringRepresentation(System.getProperty(RECORDEDMEMO_RESPONSE_KEY), 
        //        MediaType.APPLICATION_XML);
        Representation result = new StringRepresentation(("<Response>\n"+
                "   <Say>Your memo has been recorded.</Say>\n"+
                "   <Play>"+getReference().getQueryAsForm().getFirstValue("RecordingUrl")+"</Play>\n"+
                "   <Say>Goodbye.</Say>\n"+
                "</Response>"), MediaType.APPLICATION_XML);
        return result;
    }
}
