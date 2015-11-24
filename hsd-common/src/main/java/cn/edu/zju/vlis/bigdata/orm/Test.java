package cn.edu.zju.vlis.bigdata.orm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by wangxiaoyi on 15/11/24.
 */
public class Test {



    public static void main(String []args) throws Exception{
        Student stu = new Student();
        stu.setName("wang xiaoyi");
        stu.setAddr("hangzhou china");
        stu.setPhoneNumber(13250813370l);
        stu.setAge(24);

        Class stuCl = stu.getClass();

        Method []methods = stuCl.getMethods();
        for(Method method : methods){
            String methodName = method.getName();
            if(methodName.startsWith("get") && ! methodName.endsWith("Class"))
                System.out.println(method.invoke(stu, new Object[]{}));
        }


        Field [] fields = stuCl.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);

            System.out.println(field);

        }
    }


}
