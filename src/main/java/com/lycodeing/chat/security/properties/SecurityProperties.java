package com.lycodeing.chat.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaotianyu
 */
@Component
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {

    /**
     * 白名单
     */
    private List<String> whiteList;
}
