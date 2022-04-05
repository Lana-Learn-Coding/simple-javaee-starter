package io.lana.ejb.lib.utils;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.Optional;

public final class ModelUtils {
    private ModelUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericType(Class<?> clazz) {
        return (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getOriginalClass(T model) {
        Class<?> clazz = model.getClass();
        if (Proxy.isProxyClass(clazz) || clazz.getName().contains("$Proxy")) {
            return (Class<T>) clazz.getSuperclass();
        }
        return (Class<T>) clazz;
    }

    public static <T> T construct(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("cannot create object of type: " + clazz.getName(), e);
        }
    }
}
