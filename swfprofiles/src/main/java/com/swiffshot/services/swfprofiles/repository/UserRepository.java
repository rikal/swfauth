package com.swiffshot.services.swfprofiles.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.swiffshot.services.swfprofiles.model.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends GraphRepository<User>
{
    @Query("MATCH (u:User)-[k:KNOWS]->(c:User) RETURN u.userName as user, collect(k) as contacts LIMIT {limit}")
    List<Map<String, Object>> graph(@Param("limit") int limit);
}
