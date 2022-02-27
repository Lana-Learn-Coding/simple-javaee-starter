package io.lana.ejb.lib.conf;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Provider
public class LocalDateTimeConverter implements ParamConverterProvider {
    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType == LocalDate.class) {
            return (ParamConverter<T>) new ParamConverter<LocalDate>() {
                @Override
                public LocalDate fromString(String value) {
                    try {
                        return LocalDate.parse(value);
                    } catch (DateTimeParseException e) {
                        return null;
                    }
                }

                @Override
                public String toString(LocalDate value) {
                    return value.toString();
                }
            };
        }

        if (rawType == LocalDateTime.class) {
            return (ParamConverter<T>) new ParamConverter<LocalDateTime>() {
                @Override
                public LocalDateTime fromString(String value) {
                    try {
                        return LocalDateTime.parse(value);
                    } catch (DateTimeParseException e) {
                        return null;
                    }
                }

                @Override
                public String toString(LocalDateTime value) {
                    return value.toString();
                }
            };
        }
        return null;
    }
}
