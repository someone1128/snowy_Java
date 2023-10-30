package vip.xiaonuo.common.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * @author Administrator
 */
@Aspect
@Component
@Slf4j
public class RemoveBlankAspect {

    @Before("@annotation(vip.xiaonuo.common.annotation.RemoveBlank)")
    public void removeBlank(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null) {
                removeBlankFromObject(arg);
            }
        }
    }

    private void removeBlankFromObject(Object obj) {
        try {
            // 获取对象的Class
            Class<?> clazz = obj.getClass();
            // 遍历所有字段
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                // 设置字段可访问（如果是私有的）
                field.setAccessible(true);
                // 判断字段是否为String类型
                if (field.getType() == String.class) {
                    // 获取字段的值
                    String value = (String) field.get(obj);

                    // 如果值为空或者只包含空格，则设置为null
                    if (value == null || value.trim().isEmpty()) {
                        field.set(obj, null);
                    } else {
                        // 如果值不为空，则去除所有空格
                        field.set(obj, value.replace(" ", ""));
                    }
                } else if (field.getType().isArray()) {
                    // 处理数组类型的字段
                    Object array = field.get(obj);
                    if (array != null) {
                        int length = Array.getLength(array);
                        for (int i = 0; i < length; i++) {
                            Object element = Array.get(array, i);
                            if (element instanceof String) {
                                String value = (String) element;
                                if (value.trim().isEmpty()) {
                                    Array.set(array, i, null);
                                } else {
                                    Array.set(array, i, value.replace(" ", ""));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
