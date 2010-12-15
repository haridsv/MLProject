package net.dara.mlproject;

import java.util.Map;
import java.util.Properties;
import java.net.URI;
import java.net.URISyntaxException;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.XccConfigException;

/**
 * A lightweight factory interface for create XCC sessions.
 */
public class XCCSessionFactory
{
    public static final String ML_USER_KEY = "ml.user";
    public static final String ML_PASS_KEY = "ml.password";
    public static final String ML_HOST_KEY = "ml.host";
    public static final String ML_PORT_KEY = "ml.port";
    public static final String ML_DBNAME_KEY = "ml.database";
    public static final String[] ML_URI_KEYS = new String[]{ML_USER_KEY, ML_PASS_KEY, ML_HOST_KEY, ML_PORT_KEY, ML_DBNAME_KEY};
    public static final String ML_URI_FORMAT = "xcc://%1$s:%2$s@%3$s:%4$s/%5$s";

    public static XCCSessionFactory instance = null;
    /**
     * Get the default instance.
     */
    public static synchronized XCCSessionFactory getInstance()
    {
        if (instance == null) {
            instance = new XCCSessionFactory(System.getProperties());
        }
        return instance;
    }

    private Properties props;
    private ContentSource contentSource;

    /**
     * Construct a new XCCSessionFactory with no properties specified. This forces the
     * use of System properties to lookup XDBC connection specific values.
     */
    public XCCSessionFactory()
    {
        this(null);
    }

    /**
     * Construct a new XCCSessionFactory with the specified properties. The connection
     * properties are looked up in this properties object.
     *
     * @param props A properties object for XDBC connection.
     */
    public XCCSessionFactory(Properties props)
    {
        this.props = props;
        String uriStr = null;
        URI uri = null;
        try {
            uriStr = this.makeURI();
            uri = new URI(uriStr);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI: " + uriStr, e);
        }
        try {
            contentSource = ContentSourceFactory.newContentSource(uri);
        }
        catch (XccConfigException e) {
            throw new RuntimeException("Invalid XCC configuration for URI: " + uriStr, e);
        }
    }

    /**
     * Create a new XCC Session object. It is the responsibility of the caller
     * to properly close this session object.
     *
     * @return a new XCC session.
     */
    public Session newSession()
    {
        return contentSource.newSession();
    }

    public String makeURI()
    {
        return makeURI(props == null ? System.getProperties() : props);
    }

    public String makeURI(Properties props)
    {
        String[] uriProps = new String[ML_URI_KEYS.length];
        for (int i = 0; i < ML_URI_KEYS.length; ++i) {
            String key = ML_URI_KEYS[i];
            String value = props.getProperty(key);
            if (value == null || (value = value.trim()).equals("")) {
                throw new RuntimeException("No value specified for property: " + key);
            }
            uriProps[i] = props.getProperty(key);
        }
        return String.format(ML_URI_FORMAT, (Object[]) uriProps);
    }
}
