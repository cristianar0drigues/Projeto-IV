package br.ufc.fastannotation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.fastannotation.model.Project;
import br.ufc.fastannotation.model.User;
import br.ufc.fastannotation.repository.ProjectRepository;
import br.ufc.fastannotation.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepo;

	@Autowired
	UserRepository userRepo;

	public Project find(int id) {
		if (id < 1) {
			return null;
		}

		Optional<Project> project = projectRepo.findById(id);

		if (project.isPresent()) {
			return project.get();
		}

		return null;
	}

	public List<Project> findAll(int userId) {
		return projectRepo.findByUserId(userId);
	}

	public Project updateProject(int id, Project project) {
		Project projectAux = find(id);
		if (projectAux != null) {
			if (project == null || (project != null && (project.getName() == null || project.getName().trim() == ""
					|| project.getDescription() == null || project.getDescription().trim() == ""))) {

				return null;
			} else {
				projectAux.setName(project.getName());
				projectAux.setDescription(project.getDescription());
				projectRepo.save(projectAux);
			}

		}
		return projectAux;
	}

	public Project saveProject(int userId, Project project) {
		User user = userRepo.findById(userId).get();
		if (project == null || (project != null && (project.getName() == null || project.getName().trim() == ""
				|| project.getDescription() == null || project.getDescription().trim() == ""))) {

			return null;
		} else {
			project.setUser(user);
			return projectRepo.save(project);
		}
	}

	public boolean deleteProject(int id) {
		if (projectRepo.existsById(id)) {
			Project project = find(id);
			projectRepo.delete(project);
			return true;
		}

		return false;
	}

}