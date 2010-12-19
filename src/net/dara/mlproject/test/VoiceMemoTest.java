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
        // Just the first few bytes grabbed from an mp3 file using od.
    private byte[] refBytes = new byte[] {(byte) 0xfb, (byte) 0xff, (byte) 0xc0, 0x50, 0x0, 0x0, 0x2c, 0x01, 0x0, 0x0, 0x0};
    private String refBytesBase64 = "+//AUAAALAEAAAA="; // Converted using sun.misc.BASE64Encoder.

    protected void setUp()
    {
        this.xstream = new XStream();
        this.xstream.processAnnotations(VoiceMemo.class);
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
        int duration = 10;
        VoiceMemo memo = new VoiceMemo(this.refDt, duration);
        String xml = toXML(memo);
        assertEquals("<voicememo><recordedAt>"+this.refDtStr+
                "</recordedAt><recordedDuration>"+duration+
                "</recordedDuration>"+
                "<transcriptionStatus>unavailable</transcriptionStatus></voicememo>",
                xml);

        VoiceMemo memo2 = (VoiceMemo) this.xstream.fromXML(xml);
        assertEquals(this.refDt, memo2.getRecordedAt());
        assertEquals(10, memo2.getRecordedDuration());
        assertNull(memo2.getRecordedVoiceData());
        assertNull(memo2.getTranscriptionText());
        assertEquals("unavailable", memo2.getTranscriptionStatus());

        memo.setRecordedVoiceData(this.refBytes);
        xml = toXML(memo);
        assertEquals("<voicememo><recordedAt>"+this.refDtStr+
                "</recordedAt><recordedDuration>"+duration+
                "</recordedDuration><recordedVoiceData>"+
                this.refBytesBase64+"</recordedVoiceData>"+
                "<transcriptionStatus>unavailable</transcriptionStatus></voicememo>",
                xml);

        memo2 = (VoiceMemo) this.xstream.fromXML(xml);
        assertEquals(this.refDt, memo2.getRecordedAt());
        assertEquals(10, memo2.getRecordedDuration());
        Assert.assertArrayEquals(this.refBytes, memo2.getRecordedVoiceData());
        assertNull(memo2.getTranscriptionText());
        assertEquals("unavailable", memo2.getTranscriptionStatus());

        String text = "some text";
        memo.setTranscription("completed", text);
        xml = toXML(memo);
        assertEquals("<voicememo><recordedAt>"+this.refDtStr+
                "</recordedAt><recordedDuration>"+duration+
                "</recordedDuration><recordedVoiceData>"+
                this.refBytesBase64+"</recordedVoiceData>"+
                "<transcriptionText>"+text+"</transcriptionText>"+
                "<transcriptionStatus>completed</transcriptionStatus></voicememo>",
                xml);

        memo2 = (VoiceMemo) this.xstream.fromXML(xml);
        assertEquals(this.refDt, memo2.getRecordedAt());
        assertEquals(10, memo2.getRecordedDuration());
        Assert.assertArrayEquals(this.refBytes, memo2.getRecordedVoiceData());
        assertEquals(text, memo2.getTranscriptionText());
        assertEquals("completed", memo2.getTranscriptionStatus());

        text = "some text containing > a < b & c special chars";
        memo.setTranscription("completed", text);
        xml = toXML(memo);
        memo2 = (VoiceMemo) this.xstream.fromXML(xml);
        assertEquals(text, memo2.getTranscriptionText());

        memo.setTranscription("failed", null);
        xml = this.xstream.toXML(memo);
        assertEquals("<voicememo><recordedAt>"+this.refDtStr+
                "</recordedAt><recordedDuration>"+duration+
                "</recordedDuration><recordedVoiceData>"+
                this.refBytesBase64+"</recordedVoiceData>"+
                "<transcriptionStatus>failed</transcriptionStatus></voicememo>",
                toXML(memo));

        try {
            memo.setTranscription("error", null);
            fail("Expected exception not raised");
        }
        catch (IllegalArgumentException e) {
            // Pass, expected.
        }
    }

    private String toXML(Object obj) throws Exception
    {
        StringWriter sw = new StringWriter();
        CompactWriter cw = new CompactWriter(sw);
        this.xstream.marshal(obj, cw);
        return sw.toString();
    }
}
