package com.example.handlers;

import com.example.anno.EnumValue;
import com.example.entity.WeekDayEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({WeekDayEnum.class})
public class MyEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Class<E> type;
    private final E[] enums;

    public MyEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
       if(jdbcType == null){
           // 获取WeekDayEnum的所有字段并循环，找到带有@EnumValue注解的字段
           Field[] declaredFields = type.getDeclaredFields();
           for (Field declaredField  : declaredFields) {
                //是否有@EnumValue注解
               EnumValue enumValue  = declaredField.getAnnotation(EnumValue.class);
               if(enumValue != null){
                   //找到了
                   Object fieldValue = null;
                   try {
                       //反射获取标记了@EnumValue注解的字段的value
                       declaredField.setAccessible(true);
                       fieldValue = declaredField.get(parameter);
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   }
                   //设置值
                   ps.setObject(i,fieldValue);
                   return;
               }
           }
       }else {
           // 不考虑jdbcType!=null的情况
           ps.setObject(i, parameter.name(), jdbcType.TYPE_CODE);
       }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);
        if (ordinal == 0 && rs.wasNull()) {
            return null;
        }
        return toOrdinalEnum(ordinal);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        if (ordinal == 0 && rs.wasNull()) {
            return null;
        }
        return toOrdinalEnum(ordinal);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        if (ordinal == 0 && cs.wasNull()) {
            return null;
        }
        return toOrdinalEnum(ordinal);
    }

    private E toOrdinalEnum(int ordinal) {
        try {
            return enums[ordinal];
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + ordinal + " to " + type.getSimpleName() + " by ordinal value.", ex);
        }
    }
}
