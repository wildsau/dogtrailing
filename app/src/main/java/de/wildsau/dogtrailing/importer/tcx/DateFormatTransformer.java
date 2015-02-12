package de.wildsau.dogtrailing.importer.tcx;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatTransformer implements Transform<Date> {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    public DateFormatTransformer() {
    }


    @Override
    public Date read(String value) throws Exception {
        return dateFormat.parse(value);
    }


    @Override
    public String write(Date value) throws Exception {
        return dateFormat.format(value);
    }

}