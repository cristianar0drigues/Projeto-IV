package br.ufc.fastannotation.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "texts")
public class Text {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String text;
	private String type;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@OneToMany(mappedBy = "text", fetch = FetchType.EAGER)
	@Cascade(CascadeType.REMOVE)
	private Set<SubText> subTexts;

	@OneToMany(mappedBy = "text", fetch = FetchType.EAGER)
	@Cascade(CascadeType.REMOVE)
	private Set<Annotation> annotations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public Project getProject() {
		return project;
	}

	@JsonProperty
	public void setProject(Project project) {
		this.project = project;
	}

	public Set<SubText> getSubTexts() {
		return subTexts;
	}

	public void setSubTexts(Set<SubText> subTexts) {
		this.subTexts = subTexts;
	}

	public Set<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Set<Annotation> annotations) {
		this.annotations = annotations;
	}

	public Text(String text, String type) {
		super();
		this.text = text;
		this.type = type;
	}

	public Text(int id, String text, String type) {
		super();
		this.id = id;
		this.text = text;
		this.type = type;
	}

	public Text() {
	}

	@Override
	public String toString() {
		return "Text [id=" + id + ", text=" + text + ", type=" + type + ", project=" + project + "]";
	}

}
