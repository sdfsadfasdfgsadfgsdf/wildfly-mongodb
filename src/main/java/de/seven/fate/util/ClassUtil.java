package de.seven.fate.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Mario on 28.04.2016.
 */
public final class ClassUtil {

    private ClassUtil(){
        throw new UnsupportedOperationException(getClass().getName() + " should not be called with new!");
    }

    public static <T> T getGenericType(Class<?> type) {
        assert type != null;

        ParameterizedType genericSuperclass = (ParameterizedType) type.getGenericSuperclass();

        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();

        return (T) actualTypeArguments[0];
    }
}
