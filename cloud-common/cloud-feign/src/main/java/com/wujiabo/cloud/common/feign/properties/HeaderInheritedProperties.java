package com.wujiabo.cloud.common.feign.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("feign.header.inherited")
public class HeaderInheritedProperties {
    private Boolean enabled = true;

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
