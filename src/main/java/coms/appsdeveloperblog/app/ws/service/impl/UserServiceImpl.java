package coms.appsdeveloperblog.app.ws.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coms.appsdeveloperblog.app.ws.repository.UserRepository;
import coms.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import coms.appsdeveloperblog.app.ws.io.entity.UserEntity;
import coms.appsdeveloperblog.app.ws.service.UserService;
import coms.appsdeveloperblog.app.ws.shared.Utils;
import coms.appsdeveloperblog.app.ws.shared.dto.UserDto;
import coms.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("Record already exits");

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String PublicUserId = utils.GenerateRandomString(35);
		userEntity.setUserId(PublicUserId);
//		userEntity.setEncryptedpassword(user.getPassword());
		userEntity.setEncryptedpassword(bCryptPasswordEncoder.encode(user.getPassword()));
		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnvalue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnvalue);

		return returnvalue;
	}
	
	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity=userRepository.findByEmail(email);
		
		if(userEntity == null)
			throw new UsernameNotFoundException(email);
		
        UserDto returnValue=new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		
	   
		return returnValue;
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		return new User(username, userEntity.getEncryptedpassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException("The UserID: "+ userId + " is not found in the database");

		BeanUtils.copyProperties(userEntity, returnValue);
		
	   
		return returnValue;
	}

	@Transactional
	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        
		userEntity.setFirstname(user.getFirstname());
		userEntity.setLastname(user.getLastname());
		
		UserEntity updatedUserDetails=userRepository.save(userEntity);
		
		BeanUtils.copyProperties(updatedUserDetails, returnValue);

		return returnValue;
	}

	@Transactional
	@Override
	public void deleteuser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);
		
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		
		if(page>0) page=-1;
		
		List<UserDto> returnValue =new ArrayList<>();
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		 		
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users =usersPage.getContent();
		
		for(UserEntity userEntity:users) {
			UserDto userDto=new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		return returnValue;
	}

	

}
