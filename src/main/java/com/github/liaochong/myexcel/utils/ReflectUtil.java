/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liaochong.myexcel.utils;

import com.github.liaochong.myexcel.core.converter.Converter;
import com.github.liaochong.myexcel.core.reflect.ClassFieldContainer;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

/**
 * 反射工具类
 *
 * @author liaochong
 * @version 1.0
 */
@UtilityClass
public final class ReflectUtil {

    /**
     * 获取指定类的所有字段，包含父类字段，其中
     *
     * @param clazz 类
     * @return 类的所有字段
     */
    public static ClassFieldContainer getAllFieldsOfClass(Class<?> clazz) {
        ClassFieldContainer container = new ClassFieldContainer();
        getAllFieldsOfClass(clazz, container);
        return container;
    }

    /**
     * 根据对象以及指定字段，获取字段的值
     *
     * @param o     对象
     * @param field 指定字段
     * @return 字段值
     */
    public static Object getFieldValue(Object o, Field field) {
        if (Objects.isNull(o) || Objects.isNull(field)) {
            return null;
        }
        try {
            return field.get(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void getAllFieldsOfClass(Class<?> clazz, ClassFieldContainer container) {
        container.setClazz(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            container.getDeclaredFields().add(field);
            container.getFieldMap().put(field.getName(), field);
        }
        if (clazz.getSuperclass() != null) {
            ClassFieldContainer parentContainer = new ClassFieldContainer();
            container.setParent(parentContainer);
            getAllFieldsOfClass(clazz.getSuperclass(), parentContainer);
        }
    }

    public static Optional<Class> getTargetParameterOfConverter(Class<? extends Converter> clazz) {
        Type[] types = clazz.getGenericInterfaces();
        for (Type type : types) {
            if (!type.getTypeName().startsWith("com.github.liaochong.myexcel.core.converter.Converter")) {
                continue;
            }
            Type argument = ((ParameterizedType) type).getActualTypeArguments()[1];
            return Optional.of((Class) argument);
        }
        return Optional.empty();
    }
}
