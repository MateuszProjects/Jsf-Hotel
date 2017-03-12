package projectotel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer idEmployee;

	@Column(length = 45)
	private String address;

	@Temporal(TemporalType.DATE)
	private Date databirth;

	@Column(length = 45)
	private String login;

	@Column(length = 45)
	private String name;

	@Column(length = 45)
	private String pass;

	@Column(length = 45)
	private String surname;

	// bi-directional many-to-one association to Boocking
	@OneToMany(mappedBy = "employee")
	private List<Boocking> boockings;

	// bi-directional many-to-one association to Customer
	@OneToMany(mappedBy = "employee")
	private List<Customer> customers;

	// bi-directional many-to-many association to Right
	@ManyToMany(mappedBy = "employees")
	private List<Right> rights;

	public Employee() {
	}



	public Integer getIdEmployee() {
		return idEmployee;
	}



	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}



	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		boocking.setEmployee(this);

		return boocking;
	}

	public Boocking removeBoocking(Boocking boocking) {
		getBoockings().remove(boocking);
		boocking.setEmployee(null);

		return boocking;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setEmployee(this);

		return customer;
	}

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		customer.setEmployee(null);

		return customer;
	}

	public List<Right> getRights() {
		return this.rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

}