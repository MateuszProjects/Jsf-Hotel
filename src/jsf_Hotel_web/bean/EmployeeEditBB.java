package jsf_Hotel_web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import into.jsf.dao.EmployeeDAO;
import projectotel.entities.Employee;

@ManagedBean
@ViewScoped
public class EmployeeEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_EMPLOYEE = "employee?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	EmployeeDAO employeeDAO;

	private Integer idEmployee;
	private String address;
	private String databirth;
	private String login;
	private String name;
	private String pass;
	private String surname;

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDatabirth() {
		return databirth;
	}

	public void setDatabirth(String databirth) {
		this.databirth = databirth;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	private Employee employee = null;

	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		employee = (Employee) session.getAttribute("employee");

		if (employee != null) {
			session.removeAttribute("employee");
		}
		
		if (employee != null && employee.getIdEmployee() != null) {
			// setIdEmployee(employee.getIdEmployee());
			setName(employee.getName());
			setSurname(employee.getSurname());
			setAddress(employee.getAddress());
			String date = new SimpleDateFormat("dd-MM-yyyy").format(employee.getDatabirth());
			setDatabirth(date);
			setLogin(employee.getLogin());
			setPass(employee.getPass());
		}

	}

	/**
	 * Funkcja w warstwie web, do sprawdzania poprawnoœci
	 * danych przekazywanych przez u¿ytkownika.
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (address == null || address.length() == 0) {
			ctx.addMessage(null, new FacesMessage("address Wymagane"));
		}
		if (databirth == null || databirth.length() == 0) {
			ctx.addMessage(null, new FacesMessage("databirth Wymagane"));
		}
		if (login == null || login.length() == 0) {
			ctx.addMessage(null, new FacesMessage("login Wymagane"));
		}
		if (name == null || name.length() == 0) {
			ctx.addMessage(null, new FacesMessage("name Wymagane"));
		}
		if (pass == null || pass.length() == 0) {
			ctx.addMessage(null, new FacesMessage("pass Wymagane"));
		}
		if (surname == null || surname.length() == 0) {
			ctx.addMessage(null, new FacesMessage("surname Wymagane"));
		}

		Date dateBirth = null;
		try {
			dateBirth = new SimpleDateFormat("dd-MM-yyyy").parse(databirth);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (ctx.getMessageList().isEmpty()) {
			employee.setDatabirth(dateBirth);
			employee.setName(name);
			employee.setSurname(surname);
			employee.setAddress(address);
			employee.setPass(pass);
			employee.setLogin(login);
			result = true;
		}

		return result;
	}

	/**
	 * Funkcja w warstwie web, do zapisywania danych
	 * do bazy danych.
	 * @return
	 */
	public String saveData() {

		if (employee == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("B³êdne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (employee.getIdEmployee() == null) {
				employeeDAO.createEmployee(employee);
			} else {
				employeeDAO.merge(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_EMPLOYEE;
	}
}
