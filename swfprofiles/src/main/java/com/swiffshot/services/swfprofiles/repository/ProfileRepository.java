package com.swiffshot.services.swfprofiles.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.swiffshot.services.swfprofiles.model.Profile;

@RepositoryRestResource(collectionResourceRel = "profiles", path = "profiles")
public interface ProfileRepository extends GraphRepository<Profile>
{

}
