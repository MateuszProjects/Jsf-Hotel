package jsf_Hotel_web.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.BookingDAO;
import projectotel.entities.Boocking;
import projectotel.entities.Customer;
import projectotel.entities.Payment;

@ManagedBean
public class BookingBB implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String PAGE_PAYMENT_EDIT = "paymentedit?faces-redirect=true";
	private static final String PAGE_BOOKING_EDIT = "bookingedit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	Integer idBooking;
	Integer idCustomer;

	Integer limit;
	Integer offset;

	public Integer getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(Integer idBooking) {
		this.idBooking = idBooking;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	@EJB
	BookingDAO bookingDAO;

	private Customer customer = null;

	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		customer = (Customer) session.getAttribute("customer");

		if (customer != null) {
			session.removeAttribute("customer");
		}

		if (customer != null && customer.getIdCustomer() != null) {
			setIdCustomer(customer.getIdCustomer());
		}

	}

	/**
	 * Funkcja w warstwie web, do pobierania danych
	 * z bazy danych, oraz przekazywanie parametów do zapytania.
	 * @return
	 */
	public List<Boocking> getList() {
		List<Boocking> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idBooking != null) {
			searchParams.put("idBooking", idBooking);
		}

		if (idCustomer != null) {
			searchParams.put("idCustomer", idCustomer);
		}

		list = bookingDAO.getListSearch(searchParams);

		return list;
	}

	/**
	 * Funkca w warstwiw web, do tworzenia nowego
	 * obiektu booking.
	 * @return
	 */
	public String newBooking() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Boocking booking = new Boocking();
		session.setAttribute("boocking", booking);
		return PAGE_BOOKING_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do edycji 
	 * obiektu booking.
	 * @param boocking
	 * @return
	 */
	public String editBooking(Boocking boocking) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("boocking", boocking);
		return PAGE_BOOKING_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do usuwania 
	 * obiektu booking. 
	 * @param booking
	 * @return
	 */
	public String delete(Boocking booking) {
		bookingDAO.remove(booking);
		return PAGE_STAY_AT_THE_SAME;
	}

	/**
	 * Funkcja w warstwie web, do dodawania 
	 * nowej p³atnoœci do danej rezerwacji.
	 * @param booking
	 * @return
	 */
	public String addPayment(Boocking booking) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Payment payment = new Payment();
		session.setAttribute("payment", payment);
		session.setAttribute("booking", booking);

		return PAGE_PAYMENT_EDIT;
	}

}
