package org.code4everything.ichat;

import com.zhazhapan.util.Formatter;
import org.junit.Test;

import java.text.ParseException;

/**
 * @author pantao
 * @since 2018-08-06
 */
public class CommonTest {

    @Test
    public void testDataToLong() throws ParseException {
        System.out.println(Formatter.stringToDate("1994-09-03").getTime());
    }
}
