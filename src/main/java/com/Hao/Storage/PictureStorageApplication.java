package com.Hao.Storage;

import org.apache.shardingsphere.spring.boot.ShardingSphereAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication(exclude = {ShardingSphereAutoConfiguration.class})
@EnableAsync
@MapperScan("com.Hao.Storage.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class PictureStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureStorageApplication.class, args);
    }

}
