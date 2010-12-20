package net.dara.mlproject;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.dara.mlproject.resources.StartMemoResource;
import net.dara.mlproject.resources.RecordedMemoResource;
import net.dara.mlproject.resources.TranscribedMemoResource;

public class NoteTakerApplication extends Application
{

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot()
    {
        // Create a router Restlet that defines routes.
        Router router = new Router(getContext());

        router.attach("/startmemo.xqy", StartMemoResource.class);
        router.attach("/recordedmemo", RecordedMemoResource.class);
        router.attach("/transcribedmemo", TranscribedMemoResource.class);

        return router;
    }
}
