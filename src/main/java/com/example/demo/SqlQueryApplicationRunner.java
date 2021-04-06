package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.google.common.io.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Order(1)
@Log4j2
public class SqlQueryApplicationRunner implements ApplicationRunner {

  @Autowired
  private ApplicationContext applicationContext;
  
  @Autowired
  JdbcTemplate jdbcTemplate;

  private void exit(final int exitCode) {
    SpringApplication.exit(applicationContext, () -> exitCode);
    System.exit(exitCode);
  }

  private String readFile(final String relFilePath) throws IOException {
    final URL url = Resources.getResource(relFilePath);
    return Resources.toString(url, StandardCharsets.UTF_8);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("Running an SQL query to check TODOs");
    final String sql = readFile("query.sql");
    final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

    if (rows.size() > 0) {
      rows.forEach(row -> log.error(row.toString()));
      log.error("You have TODOs ...");
      exit(1);
    } else {
      log.info("Congrats, your TODO list is empty");
    }
  }

}
