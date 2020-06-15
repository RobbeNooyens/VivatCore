package net.vivatcreative.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class formats dates.
 *
 * @author Robnoo02
 */
public enum DateUtil {

    YYYY_MM_DD("yyyy-MM-dd"), //
    HH_MM_SS("HH:mm:ss"), //
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss");

    private final String f;

    DateUtil(String format) {
        this.f = format;
    }

    public String now(){
        SimpleDateFormat formatDate = new SimpleDateFormat(f);
        return formatDate.format(new Date());
    }

    public String fromDate(Date date){
        SimpleDateFormat formatDate = new SimpleDateFormat(f);
        return formatDate.format(date);
    }

    public Date fromString(String date) throws ParseException {
        return (new SimpleDateFormat(f)).parse(date);
    }

}
