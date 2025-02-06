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

import br.ufc.fastannotation.model.Text;
import br.ufc.fastannotation.service.TextService;

@RestController
@RequestMapping(path = "/api")

public class TextController {

	@Autowired
	TextService textService;

	@GetMapping("/projects/{id}/texts")
	public ResponseEntity<List<Text>> findAll(@PathVariable(value = "id") int id) {
		return new ResponseEntity<List<Text>>(textService.findAll(id), HttpStatus.OK);
	}

	@GetMapping("/texts/{id}")
	public ResponseEntity<Text> find(@PathVariable("id") int id) {
		return new ResponseEntity<Text>(textService.find(id), HttpStatus.OK);
	}

	@PostMapping("/projects/{id}/texts")
	public ResponseEntity<Text> save(@PathVariable("id") int projectId, @RequestBody Text text) {
		if (text.getText() == null || text.getText().trim() == "" || text.getType() == null
				|| text.getType().trim() == "") {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Text response = textService.saveText(projectId, text);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Text>(text, HttpStatus.OK);
	}

	@PutMapping("/texts/{id}")
	public ResponseEntity<Text> update(@PathVariable("id") int id, @RequestBody Text text) {
		Text response = textService.updateText(id, text);
		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Text>(response, HttpStatus.OK);
	}

	@DeleteMapping("/texts/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		if (textService.deleteText(id)) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

}
