package br.ufc.fastannotation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.fastannotation.model.Project;
import br.ufc.fastannotation.model.Text;
import br.ufc.fastannotation.repository.ProjectRepository;
import br.ufc.fastannotation.repository.TextRepository;

@Service
public class TextService {

	@Autowired
	TextRepository textRepo;

	@Autowired
	ProjectRepository projectRepo;
	
	public Text find(int id) {
		if (id < 1) {
			return null;
		}

		Optional<Text> text = textRepo.findById(id);

		if (text.isPresent()) {
			return text.get();
		}

		return null;
	}
	
	public List<Text> findAll(int projectId) {
		return textRepo.findByProjectId(projectId);
	}

	public Text updateText(int id, Text text) {
		Text textAux = find(id);
		if (textAux != null) {
			if (text == null || (text != null && (text.getText() == null || text.getText().trim() == ""
					|| text.getType() == null || text.getType().trim() == ""))) {

				return null;
			} else {
				textAux.setText(text.getText());
				textRepo.save(textAux);
			}

		}
		return textAux;
	}

	public Text saveText(int projectId, Text text) {
		Project project = projectRepo.findById(projectId).get();
		if (text == null || (text != null && (text.getText() == null || text.getText().trim() == ""
				|| text.getType() == null || text.getType().trim() == ""))) {

			return null;
		} else {
			text.setProject(project);
			return textRepo.save(text);
		}
	}

	public boolean deleteText(int id) {
		if (textRepo.existsById(id)) {
			Text text = find(id);
			textRepo.delete(text);
			return true;
		}

		return false;
	}

}
