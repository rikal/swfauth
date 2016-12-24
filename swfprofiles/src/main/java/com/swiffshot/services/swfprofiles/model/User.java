package com.swiffshot.services.swfprofiles.model;

import java.time.Instant;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * The homosapiens using the app from a device
 * 
 * @author Richard
 *
 */
@NodeEntity
public class User
{
	@GraphId
	private Long id;
	
	private String userName;
	private String phoneNumber;
	private String deviceID;
	private String authToken;
	private String verifyCode;
	private Instant since = Instant.now();
	private boolean verified;
	
	@Relationship(type = "KNOWS", direction = Relationship.OUTGOING)
	private Set<Contact> contacts;
	
	public Long getId()
	{
	    return id;
	}
	public Set<Contact> getContacts()
	{
		return contacts;
	}
	public void setContacts(Set<Contact> contacts)
	{
		this.contacts = contacts;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	public String getDeviceID()
	{
		return deviceID;
	}
	public void setDeviceID(String deviceID)
	{
		this.deviceID = deviceID;
	}
	public String getAuthToken()
	{
		return authToken;
	}
	public void setAuthToken(String authToken)
	{
		this.authToken = authToken;
	}
	public boolean isVerified()
	{
		return verified;
	}
	public void setVerified(boolean verified)
	{
		this.verified = verified;
	}
	public String getVerifyCode()
	{
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode)
	{
		this.verifyCode = verifyCode;
	}
	public Instant getSince()
	{
	    return since;
	}
	public void setId(Long id)
	{
	    this.id = id;
	}
	public void setSince(Instant since)
	{
	    this.since = since;
	}
}
