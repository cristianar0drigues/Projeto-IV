package br.ufc.fastannotation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.fastannotation.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findByUserId (int id);
}
