package com.trang.ShopmeBackEnd_Admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trang.ShopmeBackEnd_Admin.repository.RoleRepository;
import com.trang.ShopmeBackEnd_Admin.repository.UserRepository;
import com.trang.ShopmeCommon.entity.Role;
import com.trang.ShopmeCommon.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	public static final int USER_PER_PAGE = 4;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<User> listAllUsers() {
		return (List<User>) userRepository.findAll();
	}
	
	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE, sort);
		
		if(keyword != null) {
			return userRepository.findAll(keyword, pageable);
		}

		return userRepository.findAll(pageable);

		}

	public List<Role> listRoles() {
		return (List<Role>) roleRepository.findAll();
	}
	
	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		
		if (isUpdatingUser) {
			// User existingUser = userRepository.findById(user.getId()).get();
			User existingUser = userRepository.findById(user.getId());
			
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
			
		} else {		
			encodePassword(user);
		}
		
		return userRepository.save(user);
	}
	
//	public User save(User user) {
//		encodePassword(user);
//		return userRepository.save(user);
//	}
	

	public void encodePassword(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserByEmail(email);

		if (userByEmail == null)
			return true;

		boolean isCreatingNew = (id == null);

		if (isCreatingNew) {
			if (userByEmail != null)
				return false;
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}

		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			// return (User) userRepository.findById(id).get();
			return (User) userRepository.findById(id);
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID: " + id);
		}
	}

	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID: " + id);
		}
		userRepository.deleteById(id);
	}
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepository.updateEnabledStatus(id, enabled);
	}

}
