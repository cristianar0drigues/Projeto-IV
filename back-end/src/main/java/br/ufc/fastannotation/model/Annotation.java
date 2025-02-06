package br.ufc.fastannotation.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "annotations")
public class Annotation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String value;
	private String color;

	@ManyToMany
	@JoinTable(name = "subText_annotation", joinColumns = @JoinColumn(name = "annotation_id"), inverseJoinColumns = @JoinColumn(name = "subText_id"))
	@Cascade(CascadeType.SAVE_UPDATE)
	private List<SubText> subText;

	@ManyToOne
	@JoinColumn(name = "text_id")
	private Text text;

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

	 public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@JsonIgnore
	public List<SubText> getSubText() {
		return subText;
	}

	@JsonProperty
	public void setSubText(List<SubText> subText) {
		this.subText = subText;
	}

	@JsonIgnore
	public Text getText() {
		return text;
	}

	@JsonProperty
	public void setText(Text text) {
		this.text = text;
	}

	public Annotation(int id, String value, List<SubText> subText,String color) {
		super();
		this.id = id;
		this.value = value;
		this.subText = subText;
		this.color = color;
	}

	public Annotation(String value, List<SubText> subText) {
		super();
		this.value = value;
		this.subText = subText;
	}

	public Annotation() {
	}

	@Override
	public String toString() {
		return "Annotation [id=" + id + ", value=" + value + ", subText=" + subText + "]";
	}

}