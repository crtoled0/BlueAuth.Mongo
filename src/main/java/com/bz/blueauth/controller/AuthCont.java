package com.bz.blueauth.controller;

import java.util.Arrays;
import java.util.Map;

import com.bz.blueauth.dto.ResponseDTO;
import com.bz.blueauth.dto.pojo.UserPO;
import com.bz.blueauth.exception.AccessException;
import com.bz.blueauth.model.UserDAO;
import com.bz.blueauth.tools.CryptoTools;


public class AuthCont {
	
	private UserDAO userDao;
	private CryptoTools crypto; 
	
	public AuthCont() {
		this.userDao = new UserDAO();
		this.crypto = new CryptoTools();
	}
	
	public ResponseDTO<Map<String,?>> authenticate(UserPO userPo) throws AccessException {
		
		UserPO logUser = this.userDao.getUser(userPo.getUserid()!=null?userPo.getUserid():userPo.getEmail());
		String encPass = this.crypto.sha2Encprypt(userPo.getPassword());
		//System.out.println(encPass +" == " + logUser.getPassword());
		if(!encPass.equalsIgnoreCase(logUser.getPassword()))
			throw new AccessException("User or Password Incorrect");
		Map<String,?> obj = logUser.omit(Arrays.asList("password"));
		String accessToken = this.crypto.jwtSign("user", obj);
		return  new ResponseDTO<Map<String,?>>(obj, accessToken);
	}


	public Map isAuthorized(String token) throws AccessException {
		Map logUser = this.crypto.jwtVerify("user", token);
		ResponseDTO<Map> resp = new ResponseDTO<Map>(logUser, null);
		return resp.omit(Arrays.asList("access_token"));
	}


	public Map register(UserPO userPo) throws AccessException {
		String pass = userPo.getPassword();
		userPo.setPassword(this.crypto.sha2Encprypt(pass));
		this.userDao.create(userPo);
		ResponseDTO<String> res = new ResponseDTO<String>("User "+userPo.getUserid()+" Successfully created", "");
		return res.omit(Arrays.asList("access_token"));
	}
	
	public ResponseDTO<String> refresh(String token) throws AccessException {
		String accessToken = this.crypto.jwtRefresh("user", token);
		return  new ResponseDTO<String>("Token Refreshed", accessToken);
	}
	
}
