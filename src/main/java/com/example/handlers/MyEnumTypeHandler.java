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

//这里是插入，自定义枚举最终插入的值
@MappedTypes({WeekDayEnum.class})
public class MyEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    //枚举的class类
    private final Class<E> type;
    //枚举里面的所有对象
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
        //定义一个变量，接收从数据库查出的rest_day
        Object valueFromDB = null;

        //确定当初存入时指定了哪个字段
        Field enumValueField = null;

        Field[] declaredFileds = type.getDeclaredFields();
        for (Field field : declaredFileds) {
            //是否有@EnumValue注解
            EnumValue enumValue = field.getAnnotation(EnumValue.class);
            if(enumValue != null){
                //找到带有@EnumValue的字段
                enumValueField =  field;
                //数据库返回了ResultSet 也就是查询结果集，我们可以从中获取restDay的值
                valueFromDB = rs.getObject(columnName,enumValueField.getType());
            }
        }

        if(enumValueField == null){
            //如果没有标注，还是按照默认解析返回
            return getResultByOrdinal(rs,columnName);
        }

        //遍历WeekDayEnum的所有实例，反射获取每个实例中标注了@EnumValue的字段并做比较
        enumValueField.setAccessible(true);
        for (E weekday : enums) {
            Object value = null;
            try{
                value = enumValueField.get(weekday);
                if(valueFromDB.equals(value)){
                    //值相等，返回对于的枚举对象
                    return weekday;
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    private E getResultByOrdinal(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);//通过列名得到数量
        if(ordinal == 0 && rs.wasNull()){
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
