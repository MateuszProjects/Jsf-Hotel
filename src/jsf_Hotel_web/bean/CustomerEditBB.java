package jsf_Hotel_web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.ParseException;
import javax.servlet.http.HttpSession;
import into.jsf.dao.CustomerDAO;
import into.jsf.dao.EmployeeDAO;
import projectotel.entities.Customer;
import projectotel.entities.Employee;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ViewScoped
public class CustomerEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_CUSTOMER = "customer?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	CustomerDAO customerDAO;

	@EJB
	EmployeeDAO employeeDAO;

	private Integer idCustomer;
	private Integer idEmployee;
	private String name;
	private String surname;
	private String login;
	private String pass;
	private String dataBirth;
	private String city;
	private int postCode;

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getDataBirth() {
		return dataBirth;
	}

	public void setDataBirth(String dataBirth) {
		this.dataBirth = dataBirth;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	private Customer customer = null;

	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		customer = (Customer) session.getAttribute("customer");

		if (customer != null) {
			session.removeAttribute("customer");
		}

		if (customer != null && customer.getIdCustomer() != null) {

			setIdEmployee(customer.getEmployee().getIdEmployee());
			setIdCustomer(customer.getIdCustomer());
			setName(customer.getName());
			setSurname(customer.getSurname());
			setPostCode(customer.getPostCode());
			setCity(customer.getCity());
			setLogin(customer.getLogin());
			setPass(customer.getPass());
			String dataBirth = new SimpleDateFormat("dd-MM-yyyy").format(customer.getDatabirth());
			setDataBirth(dataBirth);
		}
	}

	/**
	 * Funkcja w warstwie web, do sprawdzania 
	 * poprawnoœci danych przekazywanych przez u¿ytkownika.
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (idEmployee == null) {
			ctx.addMessage(null, new FacesMessage("idEmployee Wymagane"));
		}

		if (name == null || name.length() == 0) {
			ctx.addMessage(null, new FacesMessage("Name Wymagane"));
		}

		if (surname == null || surname.length() == 0) {
			ctx.addMessage(null, new FacesMessage("idCustomer Wymagane"));
		}

		if (login == null || login.length() == 0) {
			ctx.addMessage(null, new FacesMessage("idCustomer Wymagane"));
		}

		if (pass == null || pass.length() == 0) {
			ctx.addMessage(null, new FacesMessage("idCustomer Wymagane"));
		}

		if (dataBirth == null || dataBirth.length() == 0) {
			ctx.addMessage(null, new FacesMessage("idCustomer Wymagane"));
		}
		if (city == null || city.length() == 0) {
			ctx.addMessage(null, new FacesMessage("idCustomer Wymagane"));
		}

		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(dataBirth);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (ctx.getMessageList().isEmpty()) {
			Employee employee = employeeDAO.find(idEmployee);
			customer.setDatabirth(date);
			customer.setEmployee(employee);
			customer.setLogin(login);
			customer.setName(name);
			customer.setSurname(surname);
			customer.setPass(pass);
			customer.setPostCode(postCode);
			customer.setCity(city);
			result = true;
		}
		return result;
	}

	/**
	 * Funkcja w warstwie web, do zapisywania
	 * danych w bazie danych.
	 * @return
	 */
	public String saveData() {

		if (customer == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("B³êdne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (customer.getIdCustomer() == null) {
				customerDAO.createCustomer(customer);
			} else {
				customerDAO.merge(customer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_CUSTOMER;
	}

}