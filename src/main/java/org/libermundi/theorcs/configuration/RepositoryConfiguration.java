package org.libermundi.theorcs.configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"org.libermundi.theorcs.domain"})
@EnableJpaRepositories(basePackages = {"org.libermundi.theorcs.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}