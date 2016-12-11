package com.swiffshot.auth;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import(RepositoryRestMvcConfiguration.class)
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.swiffshot.services.swfprofiles.service"})
@EnableNeo4jRepositories(basePackages = "com.swiffshot.services.swfprofiles.repository")
public class Neo4jConfig extends Neo4jConfiguration
{

	
	@Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
		org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config
                .driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI("http://swfdb:EM7hZ4ahUoRSQMr1G4xt@hobby-bmlkfbeejildgbkecfnbhjol.dbs.graphenedb.com:24789");
        return config;
    }

	@Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.swiffshot.services.swfprofiles.model");
    }
}