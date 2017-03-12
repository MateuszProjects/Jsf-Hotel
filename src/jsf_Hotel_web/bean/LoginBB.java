package jsf_Hotel_web.bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.BookingDAO;
import into.jsf.dao.EmployeeLoginDAO;
import projectotel.entities.Employee;
import projectotel.entities.Right;

@ManagedBean
@SessionScoped
public class LoginBB {

	private static final String PAGE_MAIN = "admin/admin?faces-redirect=true";
	private static final String PAGE_LOGIN = "/loginadmin?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String login;
	private String pass;

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

	LoginBB loginBB = null;

	@EJB
	EmployeeLoginDAO employeeLoginDAO;

	/**
	 * Funkcja w warstiwe web, do sprawdzania
	 * poprawnoœci przekazywanych danych do ziarna 
	 * zarz¹dzalnego.
	 * @return
	 */
	public boolean validateData() {
		boolean result = true;
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (login == null || login.length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "podaj login", "null"));
		}

		if (pass == null || pass.length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "podaj password", "null"));
		}

		if (ctx.getMessageList().isEmpty()) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Funkcja w warstwie web, sprawdzaj¹ca posiadene uprawnienia
	 * u¿tkownika w systemie.
	 * @param text
	 * @return
	 */
	public boolean hasEmployeeRights(String role) {
		boolean result = false;
		
		for(Right r: getEmployee().getRights()){
			if (r.getName().equals(role)) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Funkcja w warstwie web, do logowania 
	 * do systemu.
	 * @return
	 */
	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Employee empl = null;

		if (!validateData()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		empl = employeeLoginDAO.getEmployee(login, pass);

		if (empl == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niepoprawny login lub has³o", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
		session.setAttribute("empl", empl);

		return PAGE_MAIN;
	}

	/**
	 * Funkcja wprowadzaj¹ca dane do sesji o u¿ytkowniku.
	 * @return
	 */
	public Employee getEmployee() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (Employee) session.getAttribute("empl");
	}

	/**
	 * Funkcja w warstwie web, do wylogowywania u¿ytkownika 
	 * z systemu poprzez usuniêcie danych z sesji.
	 * @return
	 */
	public String doLogout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
		return PAGE_LOGIN;
	}

}
