package com.sk.op.api.server.config;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableJpaRepositories(basePackages = "com.sk.op.api.server.repository")
public class AdminDatasourceConfiguration {

}
