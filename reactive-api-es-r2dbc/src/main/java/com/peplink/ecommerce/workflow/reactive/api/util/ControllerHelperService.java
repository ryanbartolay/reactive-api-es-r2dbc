package com.peplink.ecommerce.workflow.reactive.api.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ControllerHelperService {

    public static String[] getNullAndZero(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
            if (pd.getPropertyEditorClass() == Integer.class)
                if (Integer.valueOf(srcValue.toString()) == 0)
                    emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static <S extends T, T> S mapper(T source, S target) {
        if (source != null && target != null) {
            BeanUtils.copyProperties(source, target, getNullAndZero(source));
            return target;
        }
        return null;
    }

}
