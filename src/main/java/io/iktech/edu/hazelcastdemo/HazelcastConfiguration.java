package io.iktech.edu.hazelcastdemo;

import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import io.iktech.edu.hazelcastdemo.dao.entity.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {
    @Bean
    public Config config() {
        return new Config()
                .setManagementCenterConfig(
                        new ManagementCenterConfig()
                        .setEnabled(true)
                        .setUrl("http://localhost:8080/hazelcast-mancenter")
                );
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
        return instance.getMap("customerMap");
    }
}
