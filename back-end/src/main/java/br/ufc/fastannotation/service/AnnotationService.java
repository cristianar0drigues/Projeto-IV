package br.ufc.fastannotation.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.fastannotation.model.Annotation;
import br.ufc.fastannotation.model.SubText;
import br.ufc.fastannotation.model.Text;
import br.ufc.fastannotation.repository.AnnotationRepository;
import br.ufc.fastannotation.repository.SubTextRepository;
import br.ufc.fastannotation.repository.TextRepository;

@Service
public class AnnotationService {

	@Autowired
	AnnotationRepository annotationRepo;

	@Autowired
	SubTextRepository subTextRepo;

	@Autowired
	TextRepository TextRepo;

	public Annotation find(int id) {
		if (id < 1) {
			return null;
		}

		Optional<Annotation> annotation = annotationRepo.findById(id);

		if (annotation.isPresent()) {
			return annotation.get();
		}

		return null;
	}

	public List<Annotation> findAllSubText(int subTextId) {
		return annotationRepo.findBySubTextId(subTextId);
	}

	public List<Annotation> findAllText(int TextId) {
		return annotationRepo.findByTextId(TextId);
	}
	
	public Annotation updateAnnotation(int id, Annotation annotation) {
		Annotation annotationAux = find(id);
		if (annotationAux != null) {
			if (annotation == null
					|| (annotation != null && (annotation.getValue() == null || annotation.getValue().trim() == ""
							|| annotation.getColor() == null || annotation.getColor().trim() == ""))) {

				return null;
			} else {
				annotationAux.setValue(annotation.getValue());
				annotationAux.setColor(annotation.getColor());
				annotationRepo.save(annotationAux);
			}

		}
		return annotationAux;
	}

	public List<Annotation> saveAnnotation(int TextId, List<Annotation> annotations) {
		Text text = TextRepo.findById(TextId).get();
		for (Annotation annotation : annotations) {
			if (annotation != null && (annotation.getValue() == null || annotation.getValue().trim() == ""
					|| annotation.getColor() == null || annotation.getColor().trim() == "")) {
				return null;
			} else {

				annotation.setText(text);

			}
		}
		return annotationRepo.saveAll(annotations);

	}

	public boolean deleteAnnotation(int id) {
		if (annotationRepo.existsById(id)) {
			Annotation annotation = find(id);
			annotationRepo.delete(annotation);
			return true;
		}

		return false;
	}

	@Transactional
	public Annotation associationAnnotationAndSubText(int subTextId, int annotationId) {
		SubText subText = subTextRepo.findById(subTextId).get();
		Annotation annotation = annotationRepo.findById(annotationId).get();
		if (annotation == null
				|| (annotation != null && (annotation.getValue() == null || annotation.getValue().trim() == ""
						|| annotation.getColor() == null || annotation.getColor().trim() == ""))) {

			return null;
		} else {

			annotation.getSubText().add(subText);
			return annotationRepo.save(annotation);

		}
	}

	@Transactional
	public boolean deleteAnnotationAndSubText(int subTextId, int annotationId) {
		SubText subText = subTextRepo.findById(subTextId).orElse(null);
		Annotation annotation = annotationRepo.findById(annotationId).orElse(null);

		if (subText != null && annotation != null) {
			annotation.getSubText().remove(subText);
			annotationRepo.save(annotation);
			return true;
		}
		return false;

	}

	@Transactional
	public Annotation updateAnnotationAndSubText(int oldSubTextId, int newSubTextId, int oldAnnotationId,
			int newAnnotationId) {
		SubText subText = subTextRepo.findById(oldSubTextId).orElse(null);
		SubText newSubText = subTextRepo.findById(newSubTextId).orElse(null);
		Annotation annotation = annotationRepo.findById(oldAnnotationId).orElse(null);
		Annotation newAnnotation = annotationRepo.findById(newAnnotationId).orElse(null);

		if (subText == null || newSubText == null || annotation == null || newAnnotation == null) {
			return null;
		}

		annotation.getSubText().remove(subText);
		subText.getAnnotations().remove(annotation);

		newAnnotation.getSubText().add(newSubText);
		newSubText.getAnnotations().add(newAnnotation);

		annotationRepo.delete(annotation);
		subTextRepo.save(subText);
		annotationRepo.save(newAnnotation);
		subTextRepo.save(newSubText);

		return newAnnotation;
	}

}
