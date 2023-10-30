package vip.xiaonuo.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.xiaonuo.common.util.LogPrintUtils;

@Configuration
@Slf4j
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value(("${spring.redis.port}"))
    private String port;

    @Value(("${spring.redis.password}"))
    private String password;

    @Value(("${spring.redis.database}"))
    private String database;

    @Bean
    public RedissonClient client() {
        Config config = new Config();
        LogPrintUtils.info(log, "Redisson配置 host=》{} port =>{} password =>{} database =>{}", host, port, password, database);
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password).setDatabase(Integer.parseInt(database));
        return Redisson.create(config);
    }
}
