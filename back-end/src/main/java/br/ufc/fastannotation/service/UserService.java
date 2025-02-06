package br.ufc.fastannotation.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.fastannotation.model.User;
import br.ufc.fastannotation.repository.UserRepository;
import br.ufc.fastannotation.service.exception.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	public User addUser(User user) {
		if (user == null || (user != null && (user.getLogin() == null || user.getLogin().trim() == ""
			|| user.getPassword() == null || user.getPassword().trim() == "")
			||user.getName() == null || user.getName().trim() == "")) {
			
			return null;
		}
		return userRepo.save(user);

	}

	public boolean removeUser(int id) {
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
			return true;
		}

		return false;
	}

	public List<User> getUsers() {
		return userRepo.findAll();
	}

	public User getUser(int id) {
		Optional<User> obj = userRepo.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User getUserByLogin(String login) {
		return userRepo.findFirstByLogin(login);
	}

	public User updateUser(int id, User user) {
		User userAux = userRepo.findById(id).get();

		if (userAux != null) {
			if (user == null || (user != null && (user.getLogin() == null || user.getLogin().trim() == ""
				|| user.getPassword() == null || user.getPassword().trim() == "")
				||user.getName() == null || user.getName().trim() == "")) {
				
				return null;
			} else {
				userAux.setLogin(user.getLogin());
				userAux.setPassword(user.getPassword());
				userAux.setName(user.getName());
				userRepo.save(userAux);
			}
			
		}

		return userAux;
	}
	
	

	public User getUserByLoginAndPassword(String login, String password) {
		return userRepo.findUserByLoginAndPassword(login, password);
	}
	
}
