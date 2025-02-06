package br.ufc.fastannotation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.fastannotation.model.SubText;

public interface SubTextRepository extends JpaRepository<SubText, Integer> {

	List<SubText> findByTextId (int id);
	
	//List <SubText> findByAnnotationId(int id);
	
}
