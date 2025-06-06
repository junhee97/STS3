package com.example.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // 트랜잭션 관리를 활성화 (스프링은 @Transactional 어노테이션이 붙은 메서드나 클래스의 트랜잭션을 자동으로 관리)

public class TxConfig {

	@Autowired
	private DataSource dataSource3;

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource3);
	}

}
