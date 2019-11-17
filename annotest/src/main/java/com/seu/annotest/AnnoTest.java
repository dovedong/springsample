package com.seu.annotest;

import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

@Configuration
public class AnnoTest {

    public static void main(String[] args) {
        Filter f1 = new Filter();
        f1.setId(10);

        Filter f2 = new Filter();
        f2.setUserName("sdfsdf");


        Filter f3 = new Filter();
        f3.setUserName("aaa,bbb,ccc");
        f3.setId(100);
        query(f1);
        query(f2);
        query(f3);

    }

    public static String query(Filter filter){
        StringBuilder sb = new StringBuilder();
        //1，获取到class
        Class c = filter.getClass();
        //2,获取到table的名字
        boolean exist = c.isAnnotationPresent(Table.class);
        if (!exist){
            return null;
        }
        Table t = (Table) c.getAnnotation(Table.class);
        String tableName = t.value();

        sb.append("select * from").append(tableName).append(" where 1=1");
        //3，遍历所有的字段
        Field[] fArray = c.getDeclaredFields();

        for (Field field:fArray){
            //4，处理每个字段对应的sql
            //4.1 拿到字段名称
            boolean fExist = field.isAnnotationPresent(Column.class);
            if (!fExist){
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            String columnName = column.value();
            //4.2 拿到字段的值
            String filedName = field.getName();
            String getMethodName = "get" + filedName.substring(0,1).toUpperCase() +
                    filedName.substring(1);
            Object fieldValue = null;
            try {
                Method getMethod = c.getMethod(getMethodName);
                fieldValue = getMethod.invoke(filter);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //4.3 拼装sql语句
            if (Objects.isNull(fieldValue) || (fieldValue instanceof Integer && (Integer) fieldValue == 0)){
                continue;
            }
            sb.append(" and ").append(columnName);
            if (fieldValue instanceof String){
                if (((String) fieldValue).contains(",")){
                    String[] values = ((String) fieldValue).split(",");
                    sb.append(" in(");
                    for (String v : values){
                        sb.append("'").append(v).append("'").append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(")");
                }else{
                    sb.append("=").append("'").append(fieldValue).append("'");
                }
            }else{
                sb.append("=").append(fieldValue);
            }
        }


        System.out.println("" + sb.toString());

        return sb.toString();
    }
}
