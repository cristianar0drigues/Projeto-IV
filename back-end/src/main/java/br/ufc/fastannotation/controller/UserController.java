package br.ufc.fastannotation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.fastannotation.model.User;
import br.ufc.fastannotation.service.UserService;

@RestController
@RequestMapping(path = "/api/users")

public class UserController {


	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
		return new ResponseEntity<User>(userService.getUser(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody User user) {
		if (user.getLogin() == null || user.getLogin().trim() == "" || user.getPassword() == null
				|| user.getPassword().trim() == "") {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User response = userService.addUser(user);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
		return new ResponseEntity<User>(userService.updateUser(id, user), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
		if (userService.removeUser(id)) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/login")
	public ResponseEntity<Integer> validatePassword(@RequestBody User user) {

		User optUsuario = userService.getUserByLogin(user.getLogin());
		if (optUsuario == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(0);
		}

		User usuario = optUsuario;
		boolean valid = user.getPassword().equals(usuario.getPassword());

		HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		if(valid) {
			return ResponseEntity.status(status).body(usuario.getId());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(0);
	}
}
