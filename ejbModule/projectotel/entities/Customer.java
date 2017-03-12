package projectotel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCustomer;

	private String city;

	@Temporal(TemporalType.DATE)
	private Date databirth;

	private String login;

	private String name;

	private String pass;

	private int postCode;

	private String surname;

	// bi-directional many-to-one association to Boocking
	@OneToMany(mappedBy = "customer")
	private List<Boocking> boockings;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;

	public Customer() {
	}

	public Integer getIdCustomer() {
		return this.idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getDatabirth() {
		return this.databirth;
	}

	public void setDatabirth(Date databirth) {
		this.databirth = databirth;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPostCode() {
		return this.postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Boocking> getBoockings() {
		return this.boockings;
	}

	public void setBoockings(List<Boocking> boockings) {
		this.boockings = boockings;
	}

	public Boocking addBoocking(Boocking boocking) {
		getBoockings().add(boocking);
		boocking.setCustomer(this);

		return boocking;
	}

	public Boocking removeBoocking(Boocking boocking) {
		getBoockings().remove(boocking);
		boocking.setCustomer(null);

		return boocking;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}