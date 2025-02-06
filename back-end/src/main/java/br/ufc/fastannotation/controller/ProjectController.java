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

import br.ufc.fastannotation.model.Project;
import br.ufc.fastannotation.service.ProjectService;

@RestController
@RequestMapping(path = "/api")

public class ProjectController {

	@Autowired
	ProjectService projectService;

	@GetMapping("/users/{id}/projects")
	public ResponseEntity<List<Project>> findAll(@PathVariable(value = "id") int id) {
		return new ResponseEntity<List<Project>>(projectService.findAll(id), HttpStatus.OK);
	}

	@GetMapping("/projects/{id}")
	public ResponseEntity<Project> find(@PathVariable("id") int id) {
		return new ResponseEntity<Project>(projectService.find(id), HttpStatus.OK);
	}

	@PostMapping("/users/{id}/projects")
	public ResponseEntity<Project> save(@PathVariable("id") int userId, @RequestBody Project project) {
		if (project.getName() == null || project.getName().trim() == "" || project.getDescription() == null
				|| project.getDescription().trim() == "") {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Project response = projectService.saveProject(userId, project);

		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@PutMapping("/projects/{id}")
	public ResponseEntity<Project> update(@PathVariable("id") int id, @RequestBody Project project) {
		Project response = projectService.updateProject(id, project);
		if (response == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Project>(response, HttpStatus.OK);
	}

	@DeleteMapping("/projects/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		if (projectService.deleteProject(id)) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

}