package com.example.handlers;

import com.example.entity.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 自定义枚举转换器，配合统一枚举接口IEnum
 */
public class MyEnumConverterFactory1 implements ConverterFactory<String, IEnum> {
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(Class<T> targeType) {
        return new StringToEnum(targeType);
    }

    private static class StringToEnum<T extends IEnum> implements Converter<String, T> {
        private final Class<T> enumType;

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if(source.isEmpty()){
                return null;
            }
            for (T enumConstant : enumType.getEnumConstants()) {
                //默认项目中所有Enum都实现了IEnum，那么必然又getValue（）
                if(source.equals(String.valueOf(enumConstant.getValue()))){
                    return enumConstant;
                }
            }
            return null;
        }
    }
}
