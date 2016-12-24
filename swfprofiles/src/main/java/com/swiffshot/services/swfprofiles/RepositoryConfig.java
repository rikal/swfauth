package com.swiffshot.services.swfprofiles;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.swiffshot.services.swfprofiles.model.Contact;
import com.swiffshot.services.swfprofiles.model.Profile;
import com.swiffshot.services.swfprofiles.model.User;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter
{
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) 
    {
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Profile.class);
        config.exposeIdsFor(Contact.class);
    }

}
