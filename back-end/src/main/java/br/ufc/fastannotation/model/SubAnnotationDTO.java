package br.ufc.fastannotation.model;

public class SubAnnotationDTO {

	String value;
	String annotations;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAnnotations() {
		return annotations;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public SubAnnotationDTO(String value, String annotations) {
		super();
		this.value = value;
		this.annotations = annotations;
	}

	public SubAnnotationDTO() {
		super();
	}

	@Override
	public String toString() {
		return "SubAnnotationDTO [value=" + value + ", annotations=" + annotations + "]";
	}

}
