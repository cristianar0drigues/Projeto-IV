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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.fastannotation.model.Annotation;
import br.ufc.fastannotation.service.AnnotationService;

@RestController
@RequestMapping(path = "/api")

public class AnnotationController {

	@Autowired
	AnnotationService annotationService;

	@GetMapping("/subTexts/{id}/annotations")
	public ResponseEntity<List<Annotation>> findAll(@PathVariable(value = "id") int id) {
		return new ResponseEntity<List<Annotation>>(annotationService.findAllSubText(id), HttpStatus.OK);
	}
	
	@GetMapping("/texts/{id}/annotations")
	public ResponseEntity<List<Annotation>> findAllText(@PathVariable(value = "id") int id) {
		return new ResponseEntity<List<Annotation>>(annotationService.findAllText(id), HttpStatus.OK);
	}

	@GetMapping("/annotations/{id}")
	public ResponseEntity<Annotation> find(@PathVariable("id") int id) {
		return new ResponseEntity<Annotation>(annotationService.find(id), HttpStatus.OK);
	}

	@PostMapping("/texts/{id}/annotations")
	public ResponseEntity<List<Annotation>> save(@PathVariable("id") int TextId,
			@RequestBody List<Annotation> annotations) {

		List<Annotation> response = annotationService.saveAnnotation(TextId, annotations);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Annotation>>(annotations, HttpStatus.OK);
	}

	@PutMapping("/annotations/{id}")
	public ResponseEntity<Annotation> update(@PathVariable("id") int id, @RequestBody Annotation annotation) {
		Annotation response = annotationService.updateAnnotation(id, annotation);
		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Annotation>(response, HttpStatus.OK);
	}

	@DeleteMapping("/annotations/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		if (annotationService.deleteAnnotation(id)) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/subtexts/{id}/annotations/{annotation}")
	public ResponseEntity<Annotation> save(@PathVariable("id") int subTextId,
			@PathVariable("annotation") int annotation) {
		if (annotation < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Annotation response = annotationService.associationAnnotationAndSubText(subTextId, annotation);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Annotation>(HttpStatus.OK);
	}

	@DeleteMapping("/subtexts/{id}/annotations/{annotation}")
	public ResponseEntity<Void> delete(@PathVariable("id") int idSubText,
			@PathVariable("annotation") int idAnnotation) {

		if (annotationService.deleteAnnotationAndSubText(idSubText, idAnnotation)) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

	}

	@PutMapping("/subtexts/{id}/annotations/{annotation}")
	public ResponseEntity<Annotation> update(@PathVariable("id") int id, @PathVariable("annotation") int annotation,
			@RequestParam("newSubTextId") int newSubTextId, @RequestParam("newAnnotationId") int newAnnotationId) {

		Annotation response = annotationService.updateAnnotationAndSubText(id, newSubTextId, annotation,
				newAnnotationId);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
