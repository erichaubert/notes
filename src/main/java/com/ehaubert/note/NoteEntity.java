package com.ehaubert.note;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="note")
@NamedQueries({
    @NamedQuery(name="NoteEntity.findAll", query="SELECT ne FROM NoteEntity ne"),
    @NamedQuery(name="NoteEntity.search", query="SELECT ne FROM NoteEntity ne WHERE ne.body like :criteria"),
}) 
public class NoteEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String body;
	
	public Integer getId() {
		return id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
