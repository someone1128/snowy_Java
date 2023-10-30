package vip.xiaonuo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

/**
 * @author hzy
 */
@Slf4j
public class ObjectUtils {

	private ObjectUtils() {
	}

	/**
	 * 判断对象是否为空，如果为空则创建按一个对象返回，否则直接返回
	 *
	 * @param object
	 * @param tClass
	 * @param <T>
	 * @return
	 */
	public static <T> T filterObjectNull(T object, Class<? extends T> tClass) {
		if (org.apache.commons.lang3.ObjectUtils.isEmpty(object)) {
			try {
				object = tClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				log.error(getStackTrace(e));
				e.printStackTrace();
			}
		}
		return object;
	}


	/**
	 * 对象属性复制类型转换
	 *
	 * @param source      源对象
	 * @param targetClass 目标类型
	 * @param <T>
	 * @param <H>
	 * @return
	 */
	public static <T, H> H objectCopyProperties(T source, Class<H> targetClass) {
		H h = null;
		try {
			h = targetClass.newInstance();
			BeanUtils.copyProperties(source, h);
		} catch (InstantiationException | IllegalAccessException e) {
			log.error(getStackTrace(e));
			e.printStackTrace();
		}
		return h;
	}

	/**
	 * @param sourceBean 被提取的对象bean
	 * @param targetBean 用于合并的对象bean
	 * @return targetBean 合并后的对象
	 * @Description: 该方法是用于相同对象不同属性值的合并，如果两个相同对象中同一属性都有值，
	 * 		那么sourceBean中的值会覆盖tagetBean重点的值
	 * @return: Object
	 */
	public static <T> T combineObject(T sourceBean, T targetBean) {
		if (targetBean == null) {
			return sourceBean;
		}
		Class sourceBeanClass = sourceBean.getClass();
		Class targetBeanClass = targetBean.getClass();
		Field[] sourceFields = sourceBeanClass.getDeclaredFields();
		Field[] targetFields = targetBeanClass.getDeclaredFields();
		for (int i = 0; i < sourceFields.length; i++) {
			Field sourceField = sourceFields[i];
			Field targetField = targetFields[i];
			sourceField.setAccessible(true);
			targetField.setAccessible(true);
			try {
				if (Boolean.FALSE.equals(sourceField.get(sourceBean) == null)) {
					targetField.set(targetBean, sourceField.get(sourceBean));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return targetBean;
	}

}
