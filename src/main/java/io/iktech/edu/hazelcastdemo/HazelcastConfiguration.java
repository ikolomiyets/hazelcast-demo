package io.iktech.edu.hazelcastdemo;

import com.hazelcast.config.CacheSimpleConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import io.iktech.edu.hazelcastdemo.dao.entity.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HazelcastConfiguration {
    @Bean
    public Config config() {
        CacheSimpleConfig cacheSimpleConfig = new CacheSimpleConfig();
        cacheSimpleConfig.setBackupCount(0);
        Map<String, CacheSimpleConfig> m = new HashMap<>();
        m.put("default", cacheSimpleConfig);
        return new Config()
                .setManagementCenterConfig(
                        new ManagementCenterConfig()
                        .setEnabled(true)
                        .setUrl("http://localhost:8080/hazelcast-mancenter")
                )
                .setCacheConfigs(m)
                .setProperty("hazelcast.jmx", "true")
                ;
    }

    @Bean
    public HazelcastInstance instance(Config config) {
        return com.hazelcast.core.Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    @Qualifier("nameMap")
    public IMap<String, String> nameMap(HazelcastInstance instance) {
        return instance.getMap("nameMap");
    }

    @Bean
    @Qualifier("customerMap")
    public IMap<Long, Customer> customerMap(HazelcastInstance instance) {
        IMap<Long, Customer> customerMap = instance.getMap("customerMap");
        customerMap.addIndex("firstName", true);
        customerMap.addIndex("lastName", true);
        customerMap.addIndex("ssn", true);
        customerMap.addIndex("currency", true);
        return customerMap;
    }
}
