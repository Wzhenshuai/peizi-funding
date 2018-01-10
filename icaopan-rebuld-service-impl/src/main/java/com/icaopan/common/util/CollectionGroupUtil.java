package com.icaopan.common.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;

/**
 * Created by dongyuan on 17/3/31.
 */
public class CollectionGroupUtil {

    public static <K, T> Map<K, List<T>> groupByProperty(Collection<T> collection, String propertyName, Class<T> clazz) {
        Map<K, List<T>> map = new HashMap<>();
        for (T item : collection) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(item);
            @SuppressWarnings("unchecked")
            K key = (K) beanWrapper.getPropertyValue(propertyName);
            List<T> list = map.get(key);
            if (null == list) {
                list = new ArrayList<T>();
                map.put(key, list);
            }
            list.add(item);
        }
        return map;
    }

}
