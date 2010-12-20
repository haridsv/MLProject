package net.dara.mlproject.resources;

import org.restlet.resource.Get;  
import org.restlet.resource.Post;  
import org.restlet.resource.ServerResource;  
import org.restlet.data.Form;  
import org.restlet.data.Parameter;  
import org.restlet.representation.Representation;  

public class TranscribedMemoResource extends ServerResource
{
    @Post
    public void transcribedMemo(Representation repr)
    {
        Form form = getReference().getQueryAsForm();
        for (Parameter parameter : form) {
            System.out.print("parameter " + parameter.getName());
            System.out.println("/" + parameter.getValue());
        }
    }
}
