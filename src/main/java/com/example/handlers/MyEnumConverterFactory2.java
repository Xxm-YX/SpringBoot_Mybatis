package com.example.handlers;

import com.example.anno.MyJsonCreator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.Field;

/**
 * 自定义枚举转换器，还是用原生的Enum
 * 使用分三步：
 *  1。自定义一个注解，假设叫@MyJsonCreator
 *  2. 读取注解
 *  3. 解析注解字段的值，找到匹配的枚举对象
 *
 *  MyEnumConverterFactory 主要负责2、3步
 */
public class MyEnumConverterFactory2  implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnum(targetType);
    }

    private static class StringToEnum<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        private StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }


        @Override
        public T convert(String source) {

            if(source.isEmpty()){
                return null;
            }

            try{
                for (T enumObject : enumType.getEnumConstants()) {
                    Field[] declaredFields = enumObject.getClass().getDeclaredFields();

                    for (Field declaredField : declaredFields) {
                        //读取@MyJsonCreator标注的字段
                        if(declaredField.isAnnotationPresent(MyJsonCreator.class)){
                            declaredField.setAccessible(true);
                            //读取对应的字段value；
                            Object fieldValue = declaredField.get(enumObject);
                            //匹配并返回对应的Enum
                            if(source.equals(String.valueOf(fieldValue))){
                                return enumObject;
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
