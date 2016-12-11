package com.swiffshot.services.swfprofiles.model;

import java.util.Date;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Represents Ownership info
 * 
 * @author Richard
 *
 */
@RelationshipEntity(type = "OWNED_BY")
public class Ownership
{
	@GraphId
	private Long id;
	
	private Date since;
	
	@StartNode
	private Profile profile;
	
	@EndNode
	private User owner;

	public Date getSince()
	{
		return since;
	}

	public void setSince(Date since)
	{
		this.since = since;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	public User getOwner()
	{
		return owner;
	}

	public void setOwner(User owner)
	{
		this.owner = owner;
	}
	
	
}
