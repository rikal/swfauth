package com.swiffshot.services.swfprofiles.model;

import java.util.Date;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "KNOWS")
public class Contact
{
	@GraphId
	private Long id;
	
	@StartNode
	private User initiator;
	
	@EndNode
	private User recipient;
	
	private Date initiation;
	
	private Date confirmation;

	public User getInitiator()
	{
		return initiator;
	}

	public void setInitiator(User initiator)
	{
		this.initiator = initiator;
	}

	public User getRecipient()
	{
		return recipient;
	}

	public void setRecipient(User recipient)
	{
		this.recipient = recipient;
	}

	public Date getInitiation()
	{
		return initiation;
	}

	public void setInitiation(Date initiation)
	{
		this.initiation = initiation;
	}

	public Date getConfirmation()
	{
		return confirmation;
	}

	public void setConfirmation(Date confirmation)
	{
		this.confirmation = confirmation;
	}

	public Long getId()
	{
	    return id;
	}

	public void setId(Long id)
	{
	    this.id = id;
	}

	
	

}
