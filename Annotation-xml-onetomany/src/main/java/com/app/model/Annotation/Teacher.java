

package com.app.model.Annotation;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name="com.app.model.Annotation.Teacher")
@Table
public class Teacher {
	@Id
        @Column
        @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column   //***no need to specify bcz table column name and pojo fild  name is same
	private String name;
	
	@OneToMany(mappedBy="teacher",cascade=CascadeType.ALL)
	private List<Department> departments;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

}
