package com.xq.hdb.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 此类用于读取yml中的参数值
 */

@Component
@ConfigurationProperties("hdb")
@Data
public class HdbConstantConfig {

    /**
     * 远端服务器域名
     */
    private String domainName;

    /**
     * 远端服务器地址
     */
    private String serverUrl;

    /**
     * sqlite job数据库url
     */
    private String sqliteDBJobUrl;

    /**
     * sqlite other数据库url
     */
    private String sqliteDBOtherUrl;

    /**
     * 数据备份地址
     */
    private String backupDBUrl;

    /**
     * 数据源服务器接口身份认证
     */
    private String authorization;

    /**
     * 加密盐
     */
    private String salt;


}
