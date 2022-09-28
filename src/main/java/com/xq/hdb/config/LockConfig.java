package com.xq.hdb.config;


import org.springframework.stereotype.Component;

/**
 * 此类用于锁定操作（目前可废除）
 */

@Component
public class LockConfig {


    public static Integer lockStatus = 0;


    public static Integer getLockStatus() {
        return lockStatus;
    }

    public static void setLockStatus(Integer lockStatus) {
        LockConfig.lockStatus = lockStatus;
    }


}
