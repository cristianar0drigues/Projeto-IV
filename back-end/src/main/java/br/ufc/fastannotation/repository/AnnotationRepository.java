package br.ufc.fastannotation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.fastannotation.model.Annotation;

public interface AnnotationRepository extends JpaRepository<Annotation, Integer> {

	List<Annotation> findBySubTextId(int id);

	List<Annotation> findByTextId(int id);
}
