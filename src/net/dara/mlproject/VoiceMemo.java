package net.dara.mlproject;

import java.util.Date;
import java.net.URL;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;

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
    @XStreamConverter(EncodedByteArrayConverter.class)
    private byte[] recordedVoiceData;
    /**
     * Transcription of the voice memo. Empty when transcriptionStatus is no "completed".
     */
    private String transcriptionText;
    /**
     * The status of the transcription, "unavailable", "completed" or "failed".
     */
    private String transcriptionStatus = "unavailable";

    public VoiceMemo(Date recordedAt, int recordedDuration)
    {
        this.recordedAt = recordedAt;
        this.recordedDuration = recordedDuration;
    }

    public int getRecordedDuration()
    {
        return this.recordedDuration;
    }

    public Date getRecordedAt()
    {
        return this.recordedAt;
    }

    public void setRecordedVoiceData(byte[] data)
    {
        this.recordedVoiceData = data;
    }

    public byte[] getRecordedVoiceData()
    {
        return this.recordedVoiceData;
    }

    /**
     * Set transcription status ("completed" or "failed") and the text (can be null or empty)
     */
    public void setTranscription(String transcriptionStatus, String transcriptionText)
    {
        if (!transcriptionStatus.equals("completed") && !transcriptionStatus.equals("failed")) {
            throw new IllegalArgumentException("transcriptionStatus can only be completed or failed");
        }
        this.transcriptionStatus = transcriptionStatus;
        this.transcriptionText = transcriptionText;
    }

    public String getTranscriptionText()
    {
        return this.transcriptionText;
    }

    public String getTranscriptionStatus()
    {
        return this.transcriptionStatus;
    }
}
