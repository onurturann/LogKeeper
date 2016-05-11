package net.logkeeper.spring.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parameter", catalog = "fileupload")
public class Parameter implements java.io.Serializable {

	private Integer id;
	private String key;
	private String value;

	public Parameter() {
	}

	public Parameter(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "key", unique = true, length = 255)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "value", length = 255)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Parameter [id=" + id + ", key=" + key + ", value=" + value + "]";
	}

}