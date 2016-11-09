package com.main.converter;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Date;

/**
 * Created by romain on 01/11/16.
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return localDate.toDate();
    }

    @Override
    public LocalDate convertToEntityAttribute(Date d) {
        LocalDate ld = null;
        try{
            ld = new LocalDate(d.getTime());
        }
        catch (Exception e ){
            return null;
        }
        return ld;
    }
}


