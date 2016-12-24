package com.swiffshot.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.swiffshot.auth.model.User;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter
{
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) 
    {
        config.exposeIdsFor(User.class);
    }

}