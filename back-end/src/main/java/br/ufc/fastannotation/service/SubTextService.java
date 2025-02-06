package br.ufc.fastannotation.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufc.fastannotation.model.Annotation;
import br.ufc.fastannotation.model.SubAnnotationDTO;
import br.ufc.fastannotation.model.SubText;
import br.ufc.fastannotation.model.Text;
import br.ufc.fastannotation.repository.AnnotationRepository;
import br.ufc.fastannotation.repository.SubTextRepository;
import br.ufc.fastannotation.repository.TextRepository;

@Service
public class SubTextService {

	@Autowired
	SubTextRepository subTextRepo;

	@Autowired
	TextRepository textRepo;

	@Autowired
	AnnotationRepository annotationRepo;

	public SubText find(int id) {
		if (id < 1) {
			return null;
		}

		Optional<SubText> subText = subTextRepo.findById(id);

		if (subText.isPresent()) {
			return subText.get();
		}

		return null;
	}

	public List<SubText> findAll(int textId) {
		return subTextRepo.findByTextId(textId);
	}

	public SubText updateSubText(int id, SubText subText) {
		SubText subTextAux = find(id);
		if (subTextAux != null) {
			if (subText == null
					|| (subText != null && (subText.getValue() == null || subText.getValue().trim() == ""))) {

				return null;
			} else {
				subTextAux.setValue(subText.getValue());
				subTextRepo.save(subTextAux);
			}

		}
		return subTextAux;
	}

	// int annotation_id
	public SubText saveSubTextFromText(int textId, SubText subText) {
		Text text = textRepo.findById(textId).get();
		// Annotation annotation = annotationRepo.findById(annotation_id).get();
		if (subText == null || (subText != null && (subText.getValue() == null || subText.getValue().trim() == ""))) {

			return null;
		} else {
			subText.setText(text);
			// subText.setAnnotation(annotations);
			return subTextRepo.save(subText);
		}
	}

	@Transactional
	public void saveSubTextAssociation(int textId, List<SubAnnotationDTO> subAnnotations) {
		Text text = textRepo.findById(textId).get();
		List<SubText> list = new ArrayList<>();

		for (SubAnnotationDTO subAnnotationDTO : subAnnotations) {
			SubText aux = new SubText();
			aux.setText(text);
			aux.setValue(subAnnotationDTO.getValue());
			String[] arrayAnnotations = subAnnotationDTO.getAnnotations().split(",");
			List<Annotation> listAnnotation = new ArrayList<>();

			for (String annotation : arrayAnnotations) {
				Annotation auxAnnotation = annotationRepo.findById(Integer.parseInt(annotation)).get();
				auxAnnotation.getSubText().add(aux);
				listAnnotation.add(auxAnnotation);
			}

			subTextRepo.save(aux);
			annotationRepo.saveAll(listAnnotation);
		}
	}

	// int annotation_id
	// n está sendo utilizada no controller
	public SubText saveSubTextFromAnnotation(int annotationId, SubText subText) {

		Annotation annotation = annotationRepo.findById(annotationId).get();
		if (subText == null || (subText != null && (subText.getValue() == null || subText.getValue().trim() == ""))) {

			return null;
		} else {
			subText.setAnnotations((List<Annotation>) annotation);
			return subTextRepo.save(subText);
		}
	}

	public boolean deleteSubText(int id) {
		if (subTextRepo.existsById(id)) {
			SubText subText = find(id);
			subTextRepo.delete(subText);
			return true;
		}

		return false;
	}

}
