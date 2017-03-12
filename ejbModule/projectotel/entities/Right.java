package projectotel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the right database table.
 * 
 */
@Entity
@Table(name = "hoteljee.right")
@NamedQuery(name = "Right.findAll", query = "SELECT r FROM Right r")
public class Right implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer idRight;

	@Column(length = 45)
	private String name;

	// bi-directional many-to-many association to Employee

	@ManyToMany
	@JoinTable(name = "hoteljee.employee_has_right", 
	joinColumns = {
	@JoinColumn(name = "idRight") },
	inverseJoinColumns = {
	@JoinColumn(name = "idEmployee") })
	private List<Employee> employees;

	public Right() {
	}


	public Integer getIdRight() {
		return idRight;
	}


	public void setIdRight(Integer idRight) {
		this.idRight = idRight;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}