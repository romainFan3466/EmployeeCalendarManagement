package com.main.converter;

/**
 * Created by romain on 01/11/16.
 */

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {

    @Override
    public String convertToDatabaseColumn(LocalTime localTime) {
        String t = localTime.toString(DateTimeFormat.forPattern("HH:mm"));
        return t;
    }

    @Override
    public LocalTime convertToEntityAttribute(String s) {
        LocalTime d = null;
        try {
            d = LocalTime.parse(s, DateTimeFormat.forPattern("HH:mm"));
        } catch (Exception e) {
            return null;
        }
        return d;
    }
}
