package br.ufc.fastannotation.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "subtexts")
public class SubText {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String value;

	@ManyToOne
	@JoinColumn(name = "text_id")
	private Text text;

	@ManyToMany(mappedBy = "subText",fetch = FetchType.EAGER)
	private List<Annotation> annotations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@JsonIgnore
	public Text getText() {
		return text;
	}

	@JsonProperty
	public void setText(Text text) {
		this.text = text;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	public SubText(int id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public SubText(int id, String value,List<Annotation> annotations) {
		super();
		this.id = id;
		this.value = value;
		this.annotations = annotations;
	}

	public SubText() {
	}

	@Override
	public String toString() {
		return "SubText [id=" + id + ", value=" + value + ", text=" + text + "]";
	}

}
