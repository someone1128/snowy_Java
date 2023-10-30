package vip.xiaonuo.core.redis;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import vip.xiaonuo.common.constants.RedisKeyExpirationConstant;
import vip.xiaonuo.common.util.LogPrintUtils;

/**
 * @author Administrator
 */
@Slf4j
@Configuration
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

	private final RedisTemplate<String, String> redisTemplate;

	@Autowired
	//private FrontWxPayService frontWxPayService;

	@Value("${spring.redis.database}")
	private Integer redisDatabase;

	public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, RedisTemplate<String, String> redisTemplate) {
		super(listenerContainer);
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 重写 doRegister 设置监听指定库
	 * 原本 Redis 是监听所有库，现在设定只监听当前库
	 *
	 * @param listenerContainer
	 */
	@Override
	protected void doRegister(RedisMessageListenerContainer listenerContainer) {
		listenerContainer.addMessageListener(this, new PatternTopic("__keyevent@" + redisDatabase + "__:expired"));
	}

	@Override
	public void onMessage(Message message, byte[] bytes) {
		LogPrintUtils.info(log, "============过期监听事件启动");
		RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
		String channel = String.valueOf(serializer.deserialize(message.getChannel()));
		// 获取过期的Key值
		String body = String.valueOf(serializer.deserialize(message.getBody()));
		// DB0 key过期监听
		if (CharSequenceUtil.format("__keyevent@{}__:expired", redisDatabase).equals(channel)) {
			LogPrintUtils.info(log, "第" + redisDatabase + "个DB库的key过期，key：" + body);
			if (body.startsWith(RedisKeyExpirationConstant.wxOrderClose)) {
				//log.info("关闭订单响应：" + frontWxPayService.closeOrder(body));
			}
		}
	}

}
