package com.ymca.web.util;

import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor {

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        Date[] dates = (Date[])value;
        Long[] result = new Long[dates.length];
        for (int index = 0; index < dates.length; index++) {
            result[index] = dates[index].getTime();
        }
        return result;
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        Date date = (Date)value;
        return date.getTime();
    }
}
