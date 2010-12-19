package net.dara.mlproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ResourceException;

public class NoteTakerService
{
    public static void main(String[] args)
        throws Exception
    {
        BasicConfigurator.configure();

        InputStream is = NoteTakerService.class.getClassLoader(
                ).getResourceAsStream("net/dara/mlproject/defaults.properties");
        if (is != null) {
            Properties props = new Properties();
            props.load(is);
            Enumeration e = props.propertyNames();
            while (e.hasMoreElements()) {
                String propName = (String) e.nextElement();
                System.setProperty(propName, props.getProperty(propName));
            }
        }
        Component component = new Component();

        // Add a new HTTP server listening on port 8020.
        component.getServers().add(Protocol.HTTP, 8020);

        // Attach the sample application.
        component.getDefaultHost().attach("/voicememo",
                new NoteTakerApplication());

        // Start the component.
        component.start();
    }
}
