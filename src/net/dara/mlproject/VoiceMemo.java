package net.dara.mlproject;

import java.util.Date;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * A representation of a voice memo.
 */
@XStreamAlias("voicememo")
public class VoiceMemo
{
    /**
     * The timestamp at which the recording was made.
     */
    @XStreamConverter(ISODateConverter.class)
    private Date recordedAt;
    /**
     * Duration of the recording in number of seconds.
     */
    private int recordedDuration;
    /**
     * BASE64 encoding of the voice memo.
     */
    private String recordedVoiceData;
    /**
     * Transcription of the voice memo. Empty when transcriptionStatus is no "completed".
     */
    private String transcriptionText;
    /**
     * The status of the transcription, "unavailable", "completed" or "failed".
     */
    private String transcriptionStatus;

    public VoiceMemo(Date recordedAt, int recordedDuration)
    {
        this.recordedAt = recordedAt;
        this.recordedDuration = recordedDuration;
    }

    /**
     * Read the voice recording from the specified stream and initialize
     * recordedVoiceData field after encoding it to BASE64.
     */
    public void readRecordedVoiceDataFromStream(InputStream is)
        throws IOException
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BASE64Encoder enc = new BASE64Encoder();
        enc.encode(is, os);
        this.recordedVoiceData = os.toString();
    }

    /**
     * Write the voice recording to the specified stream after decoding
     * recordedVoiceData field as BASE64.
     */
    public void writeRecordedVoiceDataToStream(OutputStream os)
        throws IOException
    {
        if (this.recordedVoiceData == null || this.recordedVoiceData.equals("")) {
            return;
        }
        // StringBufferInputStream is deprecated, so using ByteArrayInputStream,
        // but it adds an extra copy, may be we can avoid it if we implement a
        // custom InputStream that convert char's to byte's on demand.
        ByteArrayInputStream is = new ByteArrayInputStream(this.recordedVoiceData.getBytes());
        BASE64Decoder dec = new BASE64Decoder();
        dec.decodeBuffer(is, os);
    }

    public int getRecordedDuration()
    {
        return this.recordedDuration;
    }

    public Date getRecordedAt()
    {
        return this.recordedAt;
    }

    public String getRecordedVoiceData()
    {
        return this.recordedVoiceData;
    }

    public void setTranscription(String transcriptionStatus, String transcriptionText)
    {
        this.transcriptionStatus = transcriptionStatus;
        this.transcriptionText = transcriptionText;
    }
}
