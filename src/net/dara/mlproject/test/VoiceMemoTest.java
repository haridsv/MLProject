package net.dara.mlproject.test;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.StringWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

import org.junit.Assert;
import junit.framework.TestCase;

import net.dara.mlproject.VoiceMemo;
import net.dara.mlproject.ISODateConverter;

public class VoiceMemoTest extends TestCase
{
    private XStream xstream;
    private Date refDt = new Date(1292616571000L);
    private String refDtStr = "2010-12-17T12:09:31-08:00";

    protected void setUp()
    {
        this.xstream = new XStream();
        this.xstream.processAnnotations(VoiceMemo.class);
    }

    public void testReadAndWriteRecording() throws Exception
    {
        // Just the first few bytes grabbed from an mp3 file using od.
        byte[] bs = new byte[] {(byte) 0xfb, (byte) 0xff, (byte) 0xc0, 0x50, 0x0, 0x0, 0x2c, 0x01, 0x0, 0x0, 0x0};
        ByteArrayInputStream is = new ByteArrayInputStream(bs);
        VoiceMemo memo = new VoiceMemo(new Date(), 10);
        memo.readRecordedVoiceDataFromStream(is);
        assertEquals("+//AUAAALAEAAAA=", memo.getRecordedVoiceData());

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        memo.writeRecordedVoiceDataToStream(os);
        Assert.assertArrayEquals(bs, os.toByteArray());
    }

    public void testDateConversion() throws Exception
    {
        ISODateConverter conv = new ISODateConverter();
        assertTrue(conv.canConvert(Date.class));
        assertEquals(this.refDtStr, conv.toString(this.refDt));
        assertEquals(this.refDt, conv.fromString(this.refDtStr));
    }

    public void testSerialization() throws Exception
    {
        VoiceMemo memo = new VoiceMemo(this.refDt, 10);
        String xml = this.xstream.toXML(memo);
        assertEquals("<voicememo><recordedAt>"+this.refDtStr+
                "</recordedAt><recordedDuration>10</recordedDuration></voicememo>",
                toXML(memo));
    }

    private String toXML(Object obj) throws Exception
    {
        StringWriter sw = new StringWriter();
        CompactWriter cw = new CompactWriter(sw);
        this.xstream.marshal(obj, cw);
        return sw.toString();
    }
}
