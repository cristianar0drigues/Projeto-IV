package br.ufc.fastannotation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.fastannotation.model.Text;

public interface TextRepository extends JpaRepository<Text, Integer> {

	List<Text> findByProjectId (int id);
}
