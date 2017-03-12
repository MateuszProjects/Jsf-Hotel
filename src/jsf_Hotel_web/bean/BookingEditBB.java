package jsf_Hotel_web.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import into.jsf.dao.BookingDAO;
import into.jsf.dao.CustomerDAO;
import into.jsf.dao.EmployeeDAO;
import into.jsf.dao.RoomDAO;
import projectotel.entities.Boocking;
import projectotel.entities.Customer;
import projectotel.entities.Employee;
import projectotel.entities.Room;

@ManagedBean
@ViewScoped
public class BookingEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_BOOKING = "booking?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	BookingDAO bookingDAO;

	@EJB
	CustomerDAO customerDAO;

	@EJB
	RoomDAO roomDAO;

	@EJB
	EmployeeDAO employeeDAO;

	private String param;
	private int idBoocking;
	private byte active;
	private String dateFrom;
	private String datoTo;
	private Integer idCustomer;
	private Integer idRoom;
	private Integer idEmployee;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public Integer getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
	}

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}

	public Boocking getBooking() {
		return booking;
	}

	public void setBooking(Boocking booking) {
		this.booking = booking;
	}

	public int getIdBoocking() {
		return idBoocking;
	}

	public void setIdBoocking(int idBoocking) {
		this.idBoocking = idBoocking;
	}

	public byte getActive() {
		return active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDatoTo() {
		return datoTo;
	}

	public void setDatoTo(String datoTo) {
		this.datoTo = datoTo;
	}

	private Boocking booking = null;

	/**
	 * Funkcja w warstwie web, do pobierania danych z bazy danych 
	 * poprzez przekazane parametry.
	 * @return
	 */
	public List<Boocking> getList() {
		List<Boocking> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (param != null && param.length() > 0) {
			searchParams.put("param", param);

		}
		list = bookingDAO.getListSearch(searchParams);

		return list;
	}

	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		booking = (Boocking) session.getAttribute("boocking");

		Customer customer = (Customer) session.getAttribute("customer");

		if (customer != null) {
			setIdCustomer(customer.getIdCustomer());
			session.removeAttribute("customer");
		}

		if (booking != null) {
			session.removeAttribute("boocking");
		}

		// for edit object
		if (booking != null && booking.getIdBoocking() != null) {

			setActive(booking.getActive());
			String StringFrom = new SimpleDateFormat("dd-MM-yyyy").format(booking.getDateFrom());
			String StringTo = new SimpleDateFormat("dd-MM-yyyy").format(booking.getDatoTo());
			setDateFrom(StringFrom);
			setDatoTo(StringTo);
			setIdCustomer(booking.getCustomer().getIdCustomer());
			setIdRoom(booking.getRoom().getIdRoom());
			setIdEmployee(booking.getEmployee().getIdEmployee());
		}

	}

	/**
	 * Funkcaj w warstwie web, do sprawdzania poprawnoœci
	 * danych.
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (idCustomer == null) {
			ctx.addMessage(null, new FacesMessage("idCustomer Wymagane"));
		}
		if (idEmployee == null) {
			ctx.addMessage(null, new FacesMessage("idEmployee wymagane"));
		}
		if (datoTo == null || dateFrom.length() == 0) {
			ctx.addMessage(null, new FacesMessage("dataTo wymagane"));
		}
		if (dateFrom == null || dateFrom.length() == 0) {
			ctx.addMessage(null, new FacesMessage("dateFrom wymagane"));
		}
		if (idRoom == null) {
			ctx.addMessage(null, new FacesMessage("idRoom Wymagane"));
		}

		Date dateFro = null;
		Date dateTo = null;
		try {
			dateFro = new SimpleDateFormat("dd-MM-yyyy").parse(dateFrom);
			dateTo = new SimpleDateFormat("dd-MM-yyyy").parse(datoTo);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (ctx.getMessageList().isEmpty()) {
			booking.setDateFrom(dateFro);
			booking.setDatoTo(dateTo);
			booking.setActive(active);

			Customer customer = customerDAO.find(idCustomer);
			booking.setCustomer(customer);
			Room room = roomDAO.find(idRoom);
			booking.setRoom(room);
			Employee employee = employeeDAO.find(idEmployee);
			booking.setEmployee(employee);
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

		if (booking == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("B³êdne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (booking.getIdBoocking() == null) {
				bookingDAO.createBooking(booking);
			} else {
				bookingDAO.merge(booking);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_BOOKING;
	}
}