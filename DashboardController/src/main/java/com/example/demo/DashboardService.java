package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

@RestController
public class DashboardService {
	
	@Autowired
	private UserDao userDao;
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {	
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    dateFormat.setLenient(false);
	    webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

	@RequestMapping(value = "/processUserData")
	public void processUserData(@RequestParam("formDto") UserDto userDto) {
		
		
		User user = new User();
		
		BeanUtils.copyProperties(userDto, user);
		
		userDao.save(user);
		
	
	}
	
	
	@RequestMapping(value = "/findAllUsers")
	public List<UserDto> findAllUsers() {
		
		
		List<User> users =  userDao.findAll();
		
		List<UserDto> userDtos = new ArrayList<>();
		
		/*
		 * for(User user : users) { UserDto userDto = new UserDto();
		 * 
		 * BeanUtils.copyProperties(user, userDto);
		 * 
		 * userDtos.add(userDto); }
		 */
		
		
		users.forEach(obj -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(obj, userDto);
			userDtos.add(userDto);
		});
		
		
		
		return userDtos;
	}
	
	
	@RequestMapping(value = "/getUserById")
	public UserDto getUserById(@RequestParam("id") Integer id) {
		
		User user = userDao.getById(id);
		
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(user, userDto);
		
		
		return userDto;
	}
	
	@RequestMapping(value = "/deleteUserById")
	public void deleteUserById(@RequestParam("id") Integer id) {
		User user = userDao.getById(id);
		
		userDao.delete(user);
	}
	
	

}
