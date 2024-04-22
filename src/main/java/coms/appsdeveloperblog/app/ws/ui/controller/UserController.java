package coms.appsdeveloperblog.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coms.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import coms.appsdeveloperblog.app.ws.service.UserService;
import coms.appsdeveloperblog.app.ws.shared.dto.UserDto;
import coms.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import coms.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import coms.appsdeveloperblog.app.ws.ui.model.response.OperationStatusModel;
import coms.appsdeveloperblog.app.ws.ui.model.response.RequestOperationStatus;
import coms.appsdeveloperblog.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}",
			    consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }, 
			    produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE }
			)
	
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();
       
		
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		
		List<UserRest> returnValue= new ArrayList();
		
		List<UserDto> users=userService.getUsers(page,limit);
		
		for(UserDto userDto:users) {
			UserRest userModel=new UserRest();
			BeanUtils.copyProperties(userDto,userModel);
			returnValue.add(userModel);
		}
		
		return returnValue;

	}

	@PostMapping(
			     consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			     )
	public UserRest createUser(@RequestBody UserDetailsRequestModel userdata) throws Exception{
		UserRest returnValue = new UserRest();

        if(userdata.getFirstname().isEmpty()) throw new NullPointerException("The object is null");
		
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userdata, userDto);
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		return returnValue;

	}

	@PutMapping(path = "/{id}",
			    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			    )
	public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel userdata) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userdata, userDto);
		UserDto updatedUser = userService.updateUser(id,userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		return returnValue;

	}

	@DeleteMapping(path = "/{id}",
			       consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                   produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	               )
	public OperationStatusModel deleteUser(@PathVariable String id) {
		
		OperationStatusModel returnValue=new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteuser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

		return returnValue;
		
	}
	
	
}
