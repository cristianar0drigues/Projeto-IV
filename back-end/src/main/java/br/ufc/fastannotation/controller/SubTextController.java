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

import br.ufc.fastannotation.model.SubAnnotationDTO;
import br.ufc.fastannotation.model.SubText;
import br.ufc.fastannotation.service.SubTextService;

@RestController
@RequestMapping(path = "/api")

public class SubTextController {

	@Autowired
	SubTextService subTextService;

	@GetMapping("/texts/{id}/subTexts")
	public ResponseEntity<List<SubText>> findAll(@PathVariable(value = "id") int id) {
		return new ResponseEntity<List<SubText>>(subTextService.findAll(id), HttpStatus.OK);
	}

	@GetMapping("/subTexts/{id}")
	public ResponseEntity<SubText> find(@PathVariable("id") int id) {
		return new ResponseEntity<SubText>(subTextService.find(id), HttpStatus.OK);
	}

	@PostMapping("/texts/{id}/subTexts")
	public ResponseEntity<SubText> save(@PathVariable("id") int textId, @RequestBody SubText subText) {
		if (subText.getValue() == null || subText.getValue().trim() == "") {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		SubText response = subTextService.saveSubTextFromText(textId, subText);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<SubText>(subText, HttpStatus.OK);
	}

	@PostMapping("/texts/{id}/association")
	public void saveAssociation(@PathVariable("id") int textId,
			@RequestBody List<SubAnnotationDTO> subText) {
		subTextService.saveSubTextAssociation(textId, subText);
	}

	@PutMapping("/subTexts/{id}")
	public ResponseEntity<SubText> update(@PathVariable("id") int id, @RequestBody SubText subText) {
		SubText response = subTextService.updateSubText(id, subText);
		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<SubText>(response, HttpStatus.OK);
	}

	@DeleteMapping("/subTexts/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		if (subTextService.deleteSubText(id)) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

}
