package com.example.handlers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 自定义枚举转换器（直接StringToEnumConverterFactory）
 */
public final class MyEnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnum(targetType);
    }


    private static class StringToEnum<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        /**
         * StringToEnumConverterFactory默认是调用Enum.valueOf()，也就是根据Enum.name匹配
         * 我们改成根据Enum.ordinal匹配
         *
         * @param source
         * @return
         */
        @Override
        public T convert(String source) {
            if (source.isEmpty()) {
                // It's an empty enum identifier: reset the enum value to null.
                return null;
            }
            for (T enumObject : enumType.getEnumConstants()) {
                if (source.equals(String.valueOf(enumObject.ordinal()))) {
                    return enumObject;
                }
            }
            return null;
        }
    }

}
