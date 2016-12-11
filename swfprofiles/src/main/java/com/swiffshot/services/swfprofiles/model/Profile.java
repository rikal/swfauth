package com.swiffshot.services.swfprofiles.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Profile in the app represents single user, group(users, media audience, etc..)
 * @author Richard
 *
 */
@NodeEntity
public abstract class Profile
{
	@GraphId
	private Long id;
	private String profileName;
	private String profileType;
	private String profilePic;
	
	@Relationship(type = "OWNED_BY", direction = Relationship.OUTGOING)
	private Ownership owner;
	
	public String getProfileName()
	{
		return profileName;
	}
	public void setProfileName(String profileName)
	{
		this.profileName = profileName;
	}
	public String getProfileType()
	{
		return profileType;
	}
	public void setProfileType(String profileType)
	{
		this.profileType = profileType;
	}
	public Ownership getProfileCreator()
	{
		return owner;
	}
	public void setProfileCreator(Ownership profileCreator)
	{
		this.owner = profileCreator;
	}
	public String getProfilePic()
	{
		return profilePic;
	}
	public void setProfilePic(String profilePic)
	{
		this.profilePic = profilePic;
	}
	
}
