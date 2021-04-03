package com.yrx.dawdler.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by r.x on 2021/3/21.
 */
@Component
@Slf4j
public class DbConnectionConfig {
    private List<DbConnectionItem> connectionItems;
    private Map<String, DbConnectionItem> profileConnectionConfigCache;
    @Autowired
    private DbConnectionProperties connectionProperties;


    public List<DbConnectionItem> getAllConnectionItems() {
        return connectionItems;
    }

    @PostConstruct
    private void init() {
        Map<String, String> url = connectionProperties.url;
        Map<String, String> username = connectionProperties.username;
        Map<String, String> password = connectionProperties.password;

        connectionItems = new ArrayList<>(url.keySet().size());
        profileConnectionConfigCache = new HashMap<>(url.keySet().size());

        for (Map.Entry<String, String> entry : url.entrySet()) {
            String profile = entry.getKey();
            String dbUrl = entry.getValue();
            String dbUsername = username.get(profile);
            String dbPassword = password.get(profile);

            if (StringUtils.isAnyBlank(profile, dbUrl, dbUsername, dbPassword)) {
                log.warn("数据库连接参数配置错误 当前配置: profile: {}, url: {}, username: {}, password: {}",
                        profile, dbUrl, dbUsername, dbPassword);
                continue;
            }

            DbConnectionItem connectionItem = new DbConnectionItem(profile, dbUrl, dbUsername, dbPassword);
            connectionItems.add(connectionItem);
            profileConnectionConfigCache.put(profile, connectionItem);
        }
    }

    public DbConnectionItem getByProfile(String profile) {
        return profileConnectionConfigCache.get(profile);
    }

    @Data
    @AllArgsConstructor
    public static class DbConnectionItem {
        private String profile;
        private String url;
        private String username;
        private String password;
    }

    @Component
    @ConfigurationProperties(prefix = "datasource")
    @Setter
    public static class DbConnectionProperties {
        private Map<String, String> url;
        private Map<String, String> username;
        private Map<String, String> password;
    }
}
