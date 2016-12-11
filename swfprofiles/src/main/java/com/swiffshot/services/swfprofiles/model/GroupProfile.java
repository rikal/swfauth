package com.swiffshot.services.swfprofiles.model;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * A
 * @author Richard
 *
 */
@NodeEntity
public class GroupProfile extends Profile
{
	@Relationship(type = "MEMBERSHIP", direction = Relationship.UNDIRECTED)
	private List<User> members; //who belongs to this profile, group members

	public List<User> getMembers()
	{
		return members;
	}

	public void setMembers(List<User> members)
	{
		this.members = members;
	}
}
