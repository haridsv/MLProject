package net.dara.mlproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.basic.DateConverter;

/**
 * Change date formatting to ISO8601. The implementation ensures it is
 * compatible with MarkLogic's built-in support.
 *
 * Note that the implementation follows the solution at:
 * http://developer.marklogic.com/learn/2004-09-dates
 * but not sure why the colon is even required, as without that it is still
 * ISO8661 complaint, may be MarkLogic's implementation is not completely
 * complaint?
 */
public class ISODateConverter extends DateConverter
{
    public ISODateConverter()
    {
        super("yyyy-MM-dd'T'HH:mm:ssZ", new String[] {});
    }

    public String toString(Object obj)
    {
        String dateStr = super.toString(obj);

        // Map the timezone from 0000 to 00:00 (: at char 22)
        return dateStr.substring (0, 22) + ":" + dateStr.substring (22);
    }

    public Object fromString(String str)
    {
        // Remap the timezone back from 00:00 to 0000 (: at char 22)
        String dateStr = str.substring(0, 22) + str.substring(23);
        return super.fromString(dateStr);
    }
}
