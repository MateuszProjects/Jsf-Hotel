package jsf_Hotel_web.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.CustomerDAO;
import projectotel.entities.Boocking;
import projectotel.entities.Customer;

@ManagedBean
public class CustomerBB {

	private static final String PAGE_BOOKING = "booking?faces-redirect=true";
	private static final String PAGE_BOOKING_EDIT = "bookingedit?faces-redirect=true";
	private static final String PAGE_CUSTOMER_EDIT = "customeredit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	Integer idCustomer;
	String name;
	String surname;

	@EJB
	CustomerDAO customerDAO;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelCustomer();
	}

	private LazyDataModelCustomer lazyModel = null;

	public LazyDataModelCustomer getLazyList() {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		if (idCustomer != null) {
			searchParams.put("idCustomer", idCustomer);
		}

		if (name != null) {
			searchParams.put("name", name);
		}

		if (surname != null) {
			searchParams.put("surname", surname);
		}
		
		lazyModel.setSearchParams(searchParams);
		lazyModel.setCustomerDAO(customerDAO);
		return lazyModel;
	}
	
	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void newWindow() {
		addMessage("System Error", "Please try again later.");
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Funkcja w warstwie web, do pobierania danych z bazy dnaych oraz
	 * przekazywanie parametów do zapytania.
	 * 
	 * @return
	 */
	public List<Customer> getAllList() {
		List<Customer> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idCustomer != null) {
			searchParams.put("idCustomer", idCustomer);
		}

		if (name != null) {
			searchParams.put("name", name);
		}

		if (surname != null) {
			searchParams.put("surname", surname);
		}

		list = customerDAO.getSearchList(searchParams);

		return list;
	}

	/**
	 * Funkcja w warstwie web, do tworzenia nowego obiektu customer.
	 * 
	 * @return
	 */
	public String newCustomer() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Customer customer = new Customer();
		session.setAttribute("customer", customer);
		return PAGE_CUSTOMER_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do edycji obiektu customer.
	 * 
	 * @param customer
	 * @return
	 */
	public String editCustomer(Customer customer) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("customer", customer);
		return PAGE_CUSTOMER_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do usuwania obiektu customer.
	 * 
	 * @param customer
	 * @return
	 */
	public String delete(Customer customer) {
		customerDAO.remove(customer);
		return PAGE_STAY_AT_THE_SAME;
	}

	/**
	 * Funkacja w warstwie web, do tworania nowej rezerwacji oraz przekazywanie
	 * danych do nowej rezerwacji.
	 * 
	 * @param customer
	 * @return
	 */
	public String newBooking(Customer customer) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Boocking booking = new Boocking();
		session.setAttribute("customer", customer);
		session.setAttribute("boocking", booking);
		return PAGE_BOOKING_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do szukania obiektów klasy Customer powi¹zanej
	 * relacj¹ z obiektem customer.
	 * 
	 * @param customer
	 * @return
	 */
	public String searchCustomer(Customer customer) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("customer", customer);

		return PAGE_BOOKING;
	}
}
