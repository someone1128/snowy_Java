package vip.xiaonuo.common.cache;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.BusinessException;
import vip.xiaonuo.common.util.FastjsonUtils;
import vip.xiaonuo.common.util.FilterNullUtil;
import vip.xiaonuo.common.util.StringUtil;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 志源大魔王
 * @Date: 2021/9/17 11:32
 * @description: 读写Redis相关操作
 */
@Component
@Slf4j
public class RedisUtils {

	private static RedisTemplate<String, Object> redisTemplate;

	@Autowired
	public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
		RedisUtils.redisTemplate = redisTemplate;
	}

	public static boolean isRedisTemplate() {
		return redisTemplate != null;
	}

	/** -------------------key相关操作--------------------- */

	/**
	 * 删除key
	 *
	 * @param key
	 */
	public static boolean delete(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

	/**
	 * 批量删除key
	 *
	 * @param keys
	 */
	public static boolean delete(List<String> keys) {
		return FilterNullUtil.defaultIfNull(redisTemplate.delete(keys)) == keys.size();
	}

	/**
	 * 是否存在key
	 *
	 * @param key
	 * @return
	 */
	public static Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 设置过期时间
	 *
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public static Boolean expire(String key, long timeout, TimeUnit unit) {
		return redisTemplate.expire(key, timeout, unit);
	}

	/**
	 * 设置过期时间
	 *
	 * @param key  键
	 * @param time 过期时间 时间单位为秒
	 */
	public static void expire(String key, long time) {
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * 设置过期时间
	 *
	 * @param key
	 * @param date
	 * @return
	 */
	public static Boolean expireAt(String key, Date date) {
		return redisTemplate.expireAt(key, date);
	}

	/**
	 * 查找匹配的key
	 *
	 * @param pattern
	 * @return
	 */
	public static Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * @param pattern 表达式，如：abc*，找出所有以abc开始的键
	 * @return
	 */
	public static Set<String> scan(String pattern) {
		return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
			Set<String> keysTmp = new HashSet<>();
			try (Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder()
					.match(pattern)
					.count(10000).build())) {
				while (cursor.hasNext()) {
					keysTmp.add(new String(cursor.next(), StandardCharsets.UTF_8));
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new BusinessException(e);
			}
			return keysTmp;
		});
	}

	/**
	 * 将当前数据库的 key 移动到给定的数据库 db 当中
	 *
	 * @param key
	 * @param dbIndex
	 * @return
	 */
	public static Boolean move(String key, int dbIndex) {
		return redisTemplate.move(key, dbIndex);
	}

	/**
	 * 移除 key 的过期时间，key 将持久保持
	 *
	 * @param key
	 * @return
	 */
	public static Boolean persist(String key) {
		return redisTemplate.persist(key);
	}

	/**
	 * 返回 key 的剩余的过期时间
	 *
	 * @param key
	 * @param unit
	 * @return
	 */
	public static Long getExpire(String key, TimeUnit unit) {
		return redisTemplate.getExpire(key, unit);
	}

	/**
	 * 返回 key 的剩余的过期时间
	 *
	 * @param key
	 * @return
	 */
	public static Long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 从当前数据库中随机返回一个 key
	 *
	 * @return
	 */
	public static String randomKey() {
		return redisTemplate.randomKey();
	}

	/**
	 * -------------------string相关操作---------------------
	 */

	public static Long increment(String key, Long timeout) {
		Long increment = redisTemplate.opsForValue().increment(key, 1L);
		// 指定缓存失效时间
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		// 对指定 key 进行递增 1
		return increment;
	}

	public static Long increment(String key) {
		// 对指定 key 进行递增 1
		return redisTemplate.opsForValue().increment(key, 1L);
	}

	public static Long increment(String key, Long value, Long expireTime) {
		return redisTemplate.execute((RedisCallback<Long>) connection -> {
			// 将键转换为字节数组
			RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
			byte[] keyBytes = keySerializer.serialize(key);

			// 执行 INCRBY 命令
			Long result = connection.incrBy(keyBytes, value);

			// 设置过期时间
			connection.expire(keyBytes, expireTime);

			return result;
		});
	}

	public static Long increment(String key, Integer value, Long expireTime) {
		log.info("expireTime = " + expireTime);
		return increment(key, Long.valueOf(value), expireTime);
	}


	public static void incrementByKey(String key, int increment) {
		redisTemplate.opsForValue().increment(key, increment);
	}

	/**
	 * 获取指定 key 的值
	 *
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		String value = String.valueOf(redisTemplate.opsForValue().get(key));
		if (Objects.equals(value, "null")) {
			value = null;
		}
		return value;
	}

	/**
	 * 获取指定 key 的值
	 *
	 * @param key
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> T getValue(String key, Class<T> cls) {
		String value = String.valueOf(redisTemplate.opsForValue().get(key));
		if ("null".equals(value)) {
			value = null;
		}
		if (StrUtil.isBlank(value)) {
			return null;
		}
		if (cls.equals(String.class)) {
			return (T) value;
		}
		if (cls.equals(Integer.class)) {
			Integer iValue = Integer.parseInt(value);
			return (T) iValue;
		} else if (cls.equals(BigDecimal.class)) {
			BigDecimal bValue = new BigDecimal(value);
			return (T) bValue;
		}
		return (T) value;
	}

	/**
	 * 将从 redis 中获取的数据转换为指定的 class 对象返回
	 *
	 * @param key
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> T getObjectByValue(String key, Class t) {
		Object redisResult = redisTemplate.opsForValue().get(key);
		return (T) FastjsonUtils.convertValue(redisResult, t);
	}

	public static Integer getInteger(String key) {
		String value = String.valueOf(redisTemplate.opsForValue().get(key));
		return StringUtils.isBlank(value) || "null".equals(value) ? null : Integer.parseInt(value);
	}

	public static int getIntegerDefault0(String key) {
		Integer value = getInteger(key);
		return value == null ? 0 : value;
	}

	public static Integer getIntegerDefault0(String key, int defaultValue) {
		Integer value = getInteger(key);
		return value == null ? defaultValue : value;
	}

	public static Long getLong(String key) {
		String value = String.valueOf(redisTemplate.opsForValue().get(key));
		return StringUtils.isBlank(value) || "null".equals(value) ? null : Long.parseLong(value);
	}

	/**
	 * 根据 key 获取 redis 中 string 结构数据
	 *
	 * @param key
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> getListByValue(String key) {
		return (List<T>) redisTemplate.opsForValue().get(key);
	}

	/**
	 * 根据 key 获取 map
	 *
	 * @param key
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, T> getMapByValue(String key) {
		return (Map<String, T>) redisTemplate.opsForValue().get(key);
	}

	public static <T> List<T> getList(String key, Class<T> clazz) {
		String str = getString(key);
		if (StringUtils.isBlank(str)) {
			return Collections.emptyList();
		}
		return JSON.parseArray(str, clazz);
	}

	public static List<String> getStringConvertList(String key) {
		String s = getString(key);
		return StringUtil.toListComma(s);
	}

	public static BigDecimal getBigDecimal(String key) {
		return new BigDecimal(getString(key));
	}

	/**
	 * 设置指定 key 的值
	 *
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public static void setValue(String key, String value, Integer expireTime) {
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
	}

	public static void setValue(String key, String value, Long expireTime) {
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
	}

	public static <T> void setValue(String key, T value, Long expireTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
	}

	/**
	 * 设置指定 key 的值
	 *
	 * @param key
	 * @param value
	 */
	public static <T> void setValue(String key, T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 设置指定 key 的值
	 *
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public static void setLongValue(String key, long value, Long expireTime) {
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
	}

	/**
	 * 设置指定 key 的值
	 *
	 * @param key
	 * @param value
	 */
	public static void setIntValue(String key, int value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 设置指定 key 的值
	 *
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public static void setIntValue(String key, int value, Long expireTime) {
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
	}

	/**
	 * 将对象设置在 redis string 对象中
	 *
	 * @param key
	 * @param object
	 */
	public static void saveObjectToValue(String key, Object object) {
		redisTemplate.opsForValue().set(key, object);
	}

	/**
	 * 保存 list 数据到 redis string 数据结构中
	 *
	 * @param key
	 * @param list
	 * @param <T>
	 */
	public static <T> void saveListToValue(String key, List<T> list) {
		redisTemplate.opsForValue().set(key, list);
	}

	/**
	 * 将 map 存储在 redis string 数据结构中
	 *
	 * @param key
	 * @param map
	 * @param <T>
	 */
	public static <T> void saveMapToValue(String key, Map<String, T> map) {
		redisTemplate.opsForValue().set(key, map);
	}

	/**
	 * 获取列表长度
	 *
	 * @param key
	 * @return
	 */
	public static Long lLen(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * set添加元素
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public static Long sAdd(String key, String... values) {
		return redisTemplate.opsForSet().add(key, values);
	}

	/**
	 * set移除元素
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public static Long sRemove(String key, Object... values) {
		return redisTemplate.opsForSet().remove(key, values);
	}

	/**
	 * 移除并返回集合的一个随机元素
	 *
	 * @param key
	 * @return
	 */
	public static Object sPop(String key) {
		return redisTemplate.opsForSet().pop(key);
	}

	/**
	 * 将元素value从一个集合移到另一个集合
	 *
	 * @param key
	 * @param value
	 * @param destKey
	 * @return
	 */
	public static Boolean sMove(String key, String value, String destKey) {
		return redisTemplate.opsForSet().move(key, value, destKey);
	}

	/**
	 * 获取集合的大小
	 *
	 * @param key
	 * @return
	 */
	public static Long sSize(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	/**
	 * 判断集合是否包含value
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static Boolean sIsMember(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}


	/**
	 * 序列化key
	 *
	 * @param key
	 * @return
	 */
	public byte[] dump(String key) {
		return redisTemplate.dump(key);
	}

	/**
	 * 修改 key 的名称
	 *
	 * @param oldKey
	 * @param newKey
	 */
	public void rename(String oldKey, String newKey) {
		redisTemplate.rename(oldKey, newKey);
	}

	/**
	 * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
	 *
	 * @param oldKey
	 * @param newKey
	 * @return
	 */
	public Boolean renameIfAbsent(String oldKey, String newKey) {
		return redisTemplate.renameIfAbsent(oldKey, newKey);
	}

	/**
	 * 返回 key 所储存的值的类型
	 *
	 * @param key
	 * @return
	 */
	public DataType type(String key) {
		return redisTemplate.type(key);
	}

	/**
	 * 获取指定 key 的值
	 *
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 返回 key 中字符串值的子字符
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String getRange(String key, long start, long end) {
		return redisTemplate.opsForValue().get(key, start, end);
	}

	/**
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Object getAndSet(String key, String value) {
		return redisTemplate.opsForValue().getAndSet(key, value);
	}

	/**
	 * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
	 *
	 * @param key
	 * @param offset
	 * @return
	 */
	public Boolean getBit(String key, long offset) {
		return redisTemplate.opsForValue().getBit(key, offset);
	}

	/**
	 * 批量获取
	 *
	 * @param keys
	 * @return
	 */
	public List<Object> multiGet(Collection<String> keys) {
		return redisTemplate.opsForValue().multiGet(keys);
	}

	/**
	 * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
	 *
	 * @param key   位置
	 * @param value 值,true为1, false为0
	 * @return
	 */
	public boolean setBit(String key, long offset, boolean value) {
		return redisTemplate.opsForValue().setBit(key, offset, value);
	}

	/**
	 * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
	 *
	 * @param key
	 * @param value
	 * @param timeout 过期时间
	 * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
	 *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
	 */
	public void setEx(String key, String value, long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	/**
	 * 只有在 key 不存在时设置 key 的值
	 *
	 * @param key
	 * @param value
	 * @return 之前已经存在返回false, 不存在返回true
	 */
	public boolean setIfAbsent(String key, String value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	/** -------------------hash相关操作------------------------- */

	/**
	 * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
	 *
	 * @param key
	 * @param value
	 * @param offset 从指定位置开始覆写
	 */
	public void setRange(String key, String value, long offset) {
		redisTemplate.opsForValue().set(key, value, offset);
	}

	/**
	 * 获取字符串的长度
	 *
	 * @param key
	 * @return
	 */
	public Long size(String key) {
		return redisTemplate.opsForValue().size(key);
	}

	/**
	 * 批量添加
	 *
	 * @param maps
	 */
	public void multiSet(Map<String, String> maps) {
		redisTemplate.opsForValue().multiSet(maps);
	}

	/**
	 * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
	 *
	 * @param maps
	 * @return 之前已经存在返回false, 不存在返回true
	 */
	public boolean multiSetIfAbsent(Map<String, String> maps) {
		return redisTemplate.opsForValue().multiSetIfAbsent(maps);
	}

	/**
	 * 增加(自增长), 负数则为自减
	 *
	 * @param key
	 * @return
	 */
	public Long incrBy(String key, long increment) {
		return redisTemplate.opsForValue().increment(key, increment);
	}

	/**
	 * @param key
	 * @return
	 */
	public Double incrByFloat(String key, double increment) {
		return redisTemplate.opsForValue().increment(key, increment);
	}

	/**
	 * 追加到末尾
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Integer append(String key, String value) {
		return redisTemplate.opsForValue().append(key, value);
	}

	/**
	 * 获取存储在哈希表中指定字段的值
	 *
	 * @param key
	 * @param field
	 * @return
	 */
	public Object hGet(String key, String field) {
		return redisTemplate.opsForHash().get(key, field);
	}

	/**
	 * 获取所有给定字段的值
	 *
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hGetAll(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 获取所有给定字段的值
	 *
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<Object> hMultiGet(String key, Collection<Object> fields) {
		return redisTemplate.opsForHash().multiGet(key, fields);
	}

	public void hPut(String key, String hashKey, String value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	public void hPutAll(String key, Map<String, String> maps) {
		redisTemplate.opsForHash().putAll(key, maps);
	}

	/**
	 * 仅当hashKey不存在时才设置
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 * @return
	 */
	public Boolean hPutIfAbsent(String key, String hashKey, String value) {
		return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
	}

	/**
	 * 删除一个或多个哈希表字段
	 *
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long hDelete(String key, Object... fields) {
		return redisTemplate.opsForHash().delete(key, fields);
	}

	/** ------------------------list相关操作---------------------------- */

	/**
	 * 查看哈希表 key 中，指定的字段是否存在
	 *
	 * @param key
	 * @param field
	 * @return
	 */
	public boolean hExists(String key, String field) {
		return redisTemplate.opsForHash().hasKey(key, field);
	}

	/**
	 * 为哈希表 key 中的指定字段的整数值加上增量 increment
	 *
	 * @param key
	 * @param field
	 * @param increment
	 * @return
	 */
	public Long hIncrBy(String key, Object field, long increment) {
		return redisTemplate.opsForHash().increment(key, field, increment);
	}

	/**
	 * 为哈希表 key 中的指定字段的整数值加上增量 increment
	 *
	 * @param key
	 * @param field
	 * @param delta
	 * @return
	 */
	public Double hIncrByFloat(String key, Object field, double delta) {
		return redisTemplate.opsForHash().increment(key, field, delta);
	}

	/**
	 * 获取所有哈希表中的字段
	 *
	 * @param key
	 * @return
	 */
	public Set<Object> hKeys(String key) {
		return redisTemplate.opsForHash().keys(key);
	}

	/**
	 * 获取哈希表中字段的数量
	 *
	 * @param key
	 * @return
	 */
	public Long hSize(String key) {
		return redisTemplate.opsForHash().size(key);
	}

	/**
	 * 获取哈希表中所有值
	 *
	 * @param key
	 * @return
	 */
	public List<Object> hValues(String key) {
		return redisTemplate.opsForHash().values(key);
	}

	/**
	 * 迭代哈希表中的键值对
	 *
	 * @param key
	 * @param options
	 * @return
	 */
	public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
		return redisTemplate.opsForHash().scan(key, options);
	}

	/**
	 * 通过索引获取列表中的元素
	 *
	 * @param key
	 * @param index
	 * @return
	 */
	public Object lIndex(String key, long index) {
		return redisTemplate.opsForList().index(key, index);
	}

	/**
	 * 获取列表指定范围内的元素
	 *
	 * @param key
	 * @param start 开始位置, 0是开始位置
	 * @param end   结束位置, -1返回所有
	 * @return
	 */
	public List<Object> lRange(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 存储在list头部
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lLeftPush(String key, String value) {
		return redisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lLeftPushAll(String key, String... value) {
		return redisTemplate.opsForList().leftPushAll(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lLeftPushAll(String key, Collection<String> value) {
		return redisTemplate.opsForList().leftPushAll(key, value);
	}

	/**
	 * 当list存在的时候才加入
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lLeftPushIfPresent(String key, String value) {
		return redisTemplate.opsForList().leftPushIfPresent(key, value);
	}

	/**
	 * 如果pivot存在,再pivot前面添加
	 *
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 */
	public Long lLeftPush(String key, String pivot, String value) {
		return redisTemplate.opsForList().leftPush(key, pivot, value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lRightPush(String key, String value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lRightPushAll(String key, String... value) {
		return redisTemplate.opsForList().rightPushAll(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lRightPushAll(String key, Collection<String> value) {
		return redisTemplate.opsForList().rightPushAll(key, value);
	}

	/**
	 * 为已存在的列表添加值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lRightPushIfPresent(String key, String value) {
		return redisTemplate.opsForList().rightPushIfPresent(key, value);
	}

	/**
	 * 在pivot元素的右边添加值
	 *
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 */
	public Long lRightPush(String key, String pivot, String value) {
		return redisTemplate.opsForList().rightPush(key, pivot, value);
	}

	/**
	 * 通过索引设置列表元素的值
	 *
	 * @param key
	 * @param index 位置
	 * @param value
	 */
	public void lSet(String key, long index, String value) {
		redisTemplate.opsForList().set(key, index, value);
	}

	/**
	 * 移出并获取列表的第一个元素
	 *
	 * @param key
	 * @return 删除的元素
	 */
	public Object lLeftPop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 *
	 * @param key
	 * @param timeout 等待时间
	 * @param unit    时间单位
	 * @return
	 */
	public Object lBLeftPop(String key, long timeout, TimeUnit unit) {
		return redisTemplate.opsForList().leftPop(key, timeout, unit);
	}

	/** --------------------set相关操作-------------------------- */

	/**
	 * 移除并获取列表最后一个元素
	 *
	 * @param key
	 * @return 删除的元素
	 */
	public Object lRightPop(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 *
	 * @param key
	 * @param timeout 等待时间
	 * @param unit    时间单位
	 * @return
	 */
	public Object lBRightPop(String key, long timeout, TimeUnit unit) {
		return redisTemplate.opsForList().rightPop(key, timeout, unit);
	}

	/**
	 * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
	 *
	 * @param sourceKey
	 * @param destinationKey
	 * @return
	 */
	public Object lRightPopAndLeftPush(String sourceKey, String destinationKey) {
		return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
				destinationKey);
	}

	/**
	 * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 *
	 * @param sourceKey
	 * @param destinationKey
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Object lBRightPopAndLeftPush(String sourceKey, String destinationKey,
	                                    long timeout, TimeUnit unit) {
		return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
				destinationKey, timeout, unit);
	}

	/**
	 * 删除集合中值等于value得元素
	 *
	 * @param key
	 * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
	 *              index<0, 从尾部开始删除第一个值等于value的元素;
	 * @param value
	 * @return
	 */
	public Long lRemove(String key, long index, String value) {
		return redisTemplate.opsForList().remove(key, index, value);
	}

	/**
	 * 裁剪list
	 *
	 * @param key
	 * @param start
	 * @param end
	 */
	public void lTrim(String key, long start, long end) {
		redisTemplate.opsForList().trim(key, start, end);
	}

	/**
	 * 获取两个集合的交集
	 *
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public Set<Object> sIntersect(String key, String otherKey) {
		return redisTemplate.opsForSet().intersect(key, otherKey);
	}

	/**
	 * 获取key集合与多个集合的交集
	 *
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public Set<Object> sIntersect(String key, Collection<String> otherKeys) {
		return redisTemplate.opsForSet().intersect(key, otherKeys);
	}

	/**
	 * key集合与otherKey集合的交集存储到destKey集合中
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long sIntersectAndStore(String key, String otherKey, String destKey) {
		return redisTemplate.opsForSet().intersectAndStore(key, otherKey,
				destKey);
	}

	/**
	 * key集合与多个集合的交集存储到destKey集合中
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long sIntersectAndStore(String key, Collection<String> otherKeys,
	                               String destKey) {
		return redisTemplate.opsForSet().intersectAndStore(key, otherKeys,
				destKey);
	}

	/**
	 * 获取两个集合的并集
	 *
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public Set<Object> sUnion(String key, String otherKeys) {
		return redisTemplate.opsForSet().union(key, otherKeys);
	}

	/**
	 * 获取key集合与多个集合的并集
	 *
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public Set<Object> sUnion(String key, Collection<String> otherKeys) {
		return redisTemplate.opsForSet().union(key, otherKeys);
	}

	/**
	 * key集合与otherKey集合的并集存储到destKey中
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long sUnionAndStore(String key, String otherKey, String destKey) {
		return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
	}

	/**
	 * key集合与多个集合的并集存储到destKey中
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long sUnionAndStore(String key, Collection<String> otherKeys,
	                           String destKey) {
		return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
	}

	/**
	 * 获取两个集合的差集
	 *
	 * @param key
	 * @param otherKey
	 * @return
	 */
	public Set<Object> sDifference(String key, String otherKey) {
		return redisTemplate.opsForSet().difference(key, otherKey);
	}

	/**
	 * 获取key集合与多个集合的差集
	 *
	 * @param key
	 * @param otherKeys
	 * @return
	 */
	public Set<Object> sDifference(String key, Collection<String> otherKeys) {
		return redisTemplate.opsForSet().difference(key, otherKeys);
	}

	/**
	 * key集合与otherKey集合的差集存储到destKey中
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long sDifference(String key, String otherKey, String destKey) {
		return redisTemplate.opsForSet().differenceAndStore(key, otherKey,
				destKey);
	}

	/**
	 * key集合与多个集合的差集存储到destKey中
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long sDifference(String key, Collection<String> otherKeys,
	                        String destKey) {
		return redisTemplate.opsForSet().differenceAndStore(key, otherKeys,
				destKey);
	}

	/**
	 * 获取集合所有元素
	 *
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 随机获取集合中的一个元素
	 *
	 * @param key
	 * @return
	 */
	public Object sRandomMember(String key) {
		return redisTemplate.opsForSet().randomMember(key);
	}

	/**
	 * 随机获取集合中count个元素
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	public List<Object> sRandomMembers(String key, long count) {
		return redisTemplate.opsForSet().randomMembers(key, count);
	}

	/**
	 * 随机获取集合中count个元素并且去除重复的
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	public Set<Object> sDistinctRandomMembers(String key, long count) {
		return redisTemplate.opsForSet().distinctRandomMembers(key, count);
	}

	/**
	 * @param key
	 * @param options
	 * @return
	 */
	public Cursor<Object> sScan(String key, ScanOptions options) {
		return redisTemplate.opsForSet().scan(key, options);
	}

	/**------------------zSet相关操作--------------------------------*/

	/**
	 * 添加元素,有序集合是按照元素的score值由小到大排列
	 *
	 * @param key
	 * @param value
	 * @param score
	 * @return
	 */
	public Boolean zAdd(String key, String value, double score) {
		return redisTemplate.opsForZSet().add(key, value, score);
	}

	/**
	 * @param key
	 * @param values
	 * @return
	 */
	public Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> values) {
		return redisTemplate.opsForZSet().add(key, values);
	}

	/**
	 * @param key
	 * @param values
	 * @return
	 */
	public Long zRemove(String key, Object... values) {
		return redisTemplate.opsForZSet().remove(key, values);
	}

	/**
	 * 增加元素的score值，并返回增加后的值
	 *
	 * @param key
	 * @param value
	 * @param delta
	 * @return
	 */
	public Double zIncrementScore(String key, String value, double delta) {
		return redisTemplate.opsForZSet().incrementScore(key, value, delta);
	}

	/**
	 * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
	 *
	 * @param key
	 * @param value
	 * @return 0表示第一位
	 */
	public Long zRank(String key, Object value) {
		return redisTemplate.opsForZSet().rank(key, value);
	}

	/**
	 * 返回元素在集合的排名,按元素的score值由大到小排列
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Long zReverseRank(String key, Object value) {
		return redisTemplate.opsForZSet().reverseRank(key, value);
	}

	/**
	 * 获取集合的元素, 从小到大排序
	 *
	 * @param key
	 * @param start 开始位置
	 * @param end   结束位置, -1查询所有
	 * @return
	 */
	public Set<Object> zRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().range(key, start, end);
	}

	/**
	 * 获取集合元素, 并且把score值也获取
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start,
	                                                               long end) {
		return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
	}

	/**
	 * 根据Score值查询集合元素
	 *
	 * @param key
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public Set<Object> zRangeByScore(String key, double min, double max) {
		return redisTemplate.opsForZSet().rangeByScore(key, min, max);
	}

	/**
	 * 根据Score值查询集合元素, 从小到大排序
	 *
	 * @param key
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public Set<ZSetOperations.TypedTuple<Object>>
	zRangeByScoreWithScores(String key, double min, double max) {
		return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
	}

	/**
	 * @param key
	 * @param min
	 * @param max
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key,
	                                                                      double min, double max, long start, long end) {
		return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max,
				start, end);
	}

	/**
	 * 获取集合的元素, 从大到小排序
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<Object> zReverseRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}

	/**
	 * 获取集合的元素, 从大到小排序, 并返回score值
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<ZSetOperations.TypedTuple<Object>> zReverseRangeWithScores(String key,
	                                                                      long start, long end) {
		return redisTemplate.opsForZSet().reverseRangeWithScores(key, start,
				end);
	}

	/**
	 * 根据Score值查询集合元素, 从大到小排序
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<Object> zReverseRangeByScore(String key, double min,
	                                        double max) {
		return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
	}

	/**
	 * 根据Score值查询集合元素, 从大到小排序
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<ZSetOperations.TypedTuple<Object>> zReverseRangeByScoreWithScores(
			String key, double min, double max) {
		return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,
				min, max);
	}

	/**
	 * @param key
	 * @param min
	 * @param max
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<Object> zReverseRangeByScore(String key, double min,
	                                        double max, long start, long end) {
		return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max,
				start, end);
	}

	/**
	 * 根据score值获取集合元素数量
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zCount(String key, double min, double max) {
		return redisTemplate.opsForZSet().count(key, min, max);
	}

	/**
	 * 获取集合大小
	 *
	 * @param key
	 * @return
	 */
	public Long zSize(String key) {
		return redisTemplate.opsForZSet().size(key);
	}

	/**
	 * 获取集合大小
	 *
	 * @param key
	 * @return
	 */
	public Long zZCard(String key) {
		return redisTemplate.opsForZSet().zCard(key);
	}

	/**
	 * 获取集合中value元素的score值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Double zScore(String key, Object value) {
		return redisTemplate.opsForZSet().score(key, value);
	}

	/**
	 * 移除指定索引位置的成员
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zRemoveRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().removeRange(key, start, end);
	}

	/**
	 * 根据指定的score值的范围来移除成员
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zRemoveRangeByScore(String key, double min, double max) {
		return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
	}

	/**
	 * 获取key和otherKey的并集并存储在destKey中
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long zUnionAndStore(String key, String otherKey, String destKey) {
		return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
	}

	/**
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long zUnionAndStore(String key, Collection<String> otherKeys,
	                           String destKey) {
		return redisTemplate.opsForZSet()
				.unionAndStore(key, otherKeys, destKey);
	}

	/**
	 * 交集
	 *
	 * @param key
	 * @param otherKey
	 * @param destKey
	 * @return
	 */
	public Long zIntersectAndStore(String key, String otherKey,
	                               String destKey) {
		return redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
				destKey);
	}

	/**
	 * 交集
	 *
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @return
	 */
	public Long zIntersectAndStore(String key, Collection<String> otherKeys,
	                               String destKey) {
		return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
				destKey);
	}

	/**
	 * 匹配获取键值对，ScanOptions.NONE为获取全部键值对；ScanOptions.scanOptions().match("C").build()匹配获取键位map1的键值对,不能模糊匹配。
	 *
	 * @param key
	 * @param options
	 * @return
	 */
	public Cursor<ZSetOperations.TypedTuple<Object>> zScan(String key, ScanOptions options) {
		return redisTemplate.opsForZSet().scan(key, options);
	}

}
