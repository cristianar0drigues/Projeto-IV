package br.ufc.fastannotation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufc.fastannotation.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findFirstByLogin(String login);
	
	@Query(value = "SELECT * FROM users WHERE login = :login and password = :password", nativeQuery=true)
	User findUserByLoginAndPassword(@Param("login")String login, @Param("password")String password);
	
	@Query(value = "SELECT * FROM users WHERE login = :login", nativeQuery=true)
	Optional<User> findUserByLogin(@Param("login")String login);
	
}

