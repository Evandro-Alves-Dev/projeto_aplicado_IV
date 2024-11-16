//package com.senai.pa4.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.jdbc.datasource.init.ScriptUtils;
//
//import javax.sql.DataSource;
//
//public class DatabaseInitializer {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private ResourceLoader resourceLoader;
//
//    @Bean
//    public CommandLineRunner initDatabase() {
//        return args -> {
//            Resource resource = resourceLoader.getResource("classpath:audit.sql");
//            try {
//                ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//    }
//}
