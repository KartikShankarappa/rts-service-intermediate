package com.dewpoint.rts.service;

import com.dewpoint.rts.dao.UserDao;
import com.dewpoint.rts.mapper.UserMapper;
import com.dewpoint.rts.model.User;
import com.dewpoint.rts.util.ApiConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService {

	private UserMapper userMapper;
	private UserDao userDao;

	public UserService(UserDao userDao, UserMapper userMapper) {
		this.userDao = userDao;
		this.userMapper = userMapper;
	}

	public UserResponseDTO searchUsers(String userId) {
		UserResponseDTO response = new UserResponseDTO();
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		List<User> users = this.userDao.findByNamedQueryAndNamedParams("User.findSpecific", params);

		if(!users.isEmpty()) {
			List<UserDTO> usersList = new ArrayList<>();
			for(User aUser: users) {
				usersList.add(userMapper.convertEntityToDTO(aUser));
			}
			response.setUsers(usersList);
		}

		return response;
	}

	public UserResponseDTO retrieveAllUsers() {
		UserResponseDTO response = new UserResponseDTO();
		List<User> users = this.userDao.findAll();
		if(!users.isEmpty()) {
			List<UserDTO> usersList = new ArrayList<>();
			for(User aUser: users) {
				usersList.add(userMapper.convertEntityToDTO(aUser));
			}
			response.setUsers(usersList);
		}

		return response;
	}

	public void createUser(UserRequestDTO userDTO) {
		User user = userMapper.formatCreateEntry(userDTO, null);
		this.userDao.create(user);
	}

	public void updateUser(UserRequestDTO userDTO) {
		User searchUser = userMapper.formatSearchEntry(userDTO);
		Map<String, String> params = new HashMap<>();
		params.put("userId", searchUser.getUserId());
		List<User> users = this.userDao.findByNamedQueryAndNamedParams("User.findSpecific", params);

		if(!users.isEmpty()) {
			User user = userMapper.formatUpdateEntry(users.get(0), null);

			if(userDTO.getPassword() != null
					&& !userDTO.getPassword().isEmpty()) {
				// Below happens when user is changing initial default password
				user.setPassword(userDTO.getPassword());
			} else {
				// Below happens when Admin is resetting to initial default password
				user.setPassword(ApiConstants.DEFAULT_INITIAL_PASSWORD);
			}

			this.userDao.update(user);
		}
	}


}
