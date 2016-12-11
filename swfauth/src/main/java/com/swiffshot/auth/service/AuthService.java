package com.swiffshot.auth.service;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authy.AuthyApiClient;
import com.authy.api.Token;
import com.swiffshot.auth.model.User;
import com.swiffshot.auth.repository.UserRepository;


@RestController
public class AuthService
{
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AuthyApiClient authyClient;
    
    @RequestMapping(value = "/{userId}/sendCode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> sendAuthCode(@PathVariable Long userId)
    {
	User usr = userRepo.findOne(userId);
	com.authy.api.User authyUser = authyClient.getUsers().createUser("", usr.getPhoneNumber(), "1");
	
	if (authyUser.isOk()) {
            int authyUserId = authyUser.getId();
            usr.setVerifyCode(Integer.toString(authyUserId));
            usr = userRepo.save(usr);
            authyClient.getUsers().requestSms(authyUserId);
        } else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
	return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
    @RequestMapping(value = "/{userId}/reSendCode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> resendAuthCode( @PathVariable Long userId)
    {
	User usr = userRepo.findOne(userId);
        authyClient.getUsers().requestSms(Integer.parseInt(usr.getVerifyCode()));
	return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
   
    @RequestMapping(value = "/{userId}/verifyCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> verifyAuthCode(@PathVariable Long userId,@RequestBody String code)
    {
	User usr = userRepo.findOne(userId);

        Token token = authyClient.getTokens().verify(Integer.parseInt(usr.getVerifyCode()), code);
        
        if (token.isOk()) {
            usr.setVerified(true);
            userRepo.save(usr);
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	
	return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
