package net.dara.mlproject.test;

import java.util.Properties;

import com.marklogic.xcc.Session;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultSequence;

import net.dara.mlproject.XCCSessionFactory;

import junit.framework.TestCase;

public class XCCSessionFactoryTest extends TestCase
{
    private Properties defProps;

    protected void setUp()
    {
        Properties props = new Properties();
        props.setProperty(XCCSessionFactory.ML_USER_KEY, "projectdemo");
        props.setProperty(XCCSessionFactory.ML_PASS_KEY, "markl0g1c");
        props.setProperty(XCCSessionFactory.ML_HOST_KEY, "ec2-184-73-91-241.compute-1.amazonaws.com");
        props.setProperty(XCCSessionFactory.ML_PORT_KEY, "8003");
        props.setProperty(XCCSessionFactory.ML_DBNAME_KEY, "Documents");
        defProps = props;
    }

    public void testURIconstruction() throws Exception
    {
        XCCSessionFactory sessionFactory = new XCCSessionFactory(this.defProps);
        assertEquals("xcc://projectdemo:markl0g1c@ec2-184-73-91-241.compute-1.amazonaws.com:8003/Documents",
                sessionFactory.makeURI());
    }

    public void testSessionCreation() throws Exception
    {
        XCCSessionFactory sessionFactory = new XCCSessionFactory(this.defProps);
        Session session = sessionFactory.newSession();
        session.close();
    }

    public void testDummyQuery() throws Exception
    {
        XCCSessionFactory sessionFactory = new XCCSessionFactory(this.defProps);
        Session session = sessionFactory.newSession();
        try {
            Request request = session.newAdhocQuery("\"Hello World\"");
            ResultSequence rs = session.submitRequest(request);
            assertEquals("Hello World", rs.asString());
        }
        finally {
            session.close();
        }
    }
}
