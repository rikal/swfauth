package com.swiffshot.services.swfprofiles.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiffshot.services.swfprofiles.model.Contact;
import com.swiffshot.services.swfprofiles.model.Ownership;
import com.swiffshot.services.swfprofiles.model.Profile;
import com.swiffshot.services.swfprofiles.model.User;
import com.swiffshot.services.swfprofiles.repository.ProfileRepository;
import com.swiffshot.services.swfprofiles.repository.UserRepository;

@RestController
public class ProfileService
{
    @Autowired
    private ProfileRepository profRepo;

    @Autowired
    private UserRepository userRepo;

    /**
     * Add a new human user of the app
     * 
     * @param user
     * @return the user entity as saved
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<User> addNewUser(@RequestBody User user)
    {
	User savedEntity = null;
	try
	{
	    savedEntity = userRepo.save(user);

	} catch (Exception e)
	{
	    e.printStackTrace();
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		    .body(null);
	}
	return ResponseEntity.status(HttpStatus.OK).body(savedEntity);
    }

    /**
     * Add a profile within the app
     * 
     * @param userId
     *            of user creating the profile
     * @param prof
     * @return the profile as saved
     */
    @RequestMapping(value = "/{userId}/profile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Profile> addNewProfile(@PathVariable Long userId,
	    @RequestBody Profile prof)
    {
	Profile savedProf = null;
	Ownership owner = new Ownership();
	owner.setOwner(userRepo.findOne(userId));
	prof.setProfileCreator(owner);

	try
	{
	    savedProf = profRepo.save(prof);

	} catch (Exception e)
	{
	    e.printStackTrace();
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		    .body(null);
	}

	return ResponseEntity.status(HttpStatus.OK).body(savedProf);
    }

    /**
     * Update list of contacts for the user
     * 
     * @param userId
     * @param newContacts
     * @return
     */
    @RequestMapping(value = "/{userId}/contacts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Set<Contact>> addNewContacts(
	    @PathVariable Long userId, @RequestBody Set<Contact> newContacts)
    {
	User usr = userRepo.findOne(userId);

	try
	{
	    usr.getContacts().addAll(newContacts);
	    usr = userRepo.save(usr);

	} catch (Exception e)
	{
	    e.printStackTrace();
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		    .body(null);
	}
	return ResponseEntity.status(HttpStatus.OK).body(usr.getContacts());
    }

    /**
     * Get the set of contacts for a given user
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}/contacts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Set<Contact>> getContacts(@PathVariable Long userId)
    {
	User usr = null;
	Set<Contact> contacts = null;

	try
	{
	    usr = userRepo.findOne(userId);
	    contacts = usr.getContacts();

	} catch (Exception e)
	{
	    e.printStackTrace();
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		    .body(null);
	}
	return ResponseEntity.status(HttpStatus.OK).body(contacts);
    }

    @SuppressWarnings({ "unchecked" })
    @RequestMapping("/graph")
    public Map<String, Object> graph(@RequestParam(value = "limit", required = false) Integer limit)
    {

	Iterator<Map<String, Object>> result = userRepo.graph(limit == null ? 100 : limit).iterator();

	List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> rels = new ArrayList<Map<String, Object>>();
	
	int i = 0;
	while (result.hasNext())
	{
	    Map<String, Object> row = result.next();
	    nodes.add(map("title", row.get("user"), "label", "user"));
	    int target = i;
	    i++;
	    for (Contact c : (Collection<Contact>) row.get("contacts"))
	    {
		Map<String, Object> contact = map("title", c.getRecipient().getUserName(), "label", "contact");
		int source = nodes.indexOf(contact);
		if (source == -1)
		{
		    nodes.add(contact);
		    source = i++;
		}
		rels.add(map("source", source, "target", target));
	    }
	}
	return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2)
    {
	Map<String, Object> result = new HashMap<String, Object>(2);
	result.put(key1, value1);
	result.put(key2, value2);
	return result;
    }

}
