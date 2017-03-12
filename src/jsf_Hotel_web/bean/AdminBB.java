package jsf_Hotel_web.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import into.jsf.dao.BookingDAO;
import projectotel.entities.Boocking;

@ManagedBean
@ViewScoped
public class AdminBB {

	String dataFrom;
	String dataTo;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelBooking();
	}

	private LazyDataModelBooking lazyModel = null;

	public LazyDataModel<Boocking> getListLazy() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (dataFrom != null) {
			searchParams.put("datafrom", dataFrom);
		}
		if (dataTo != null) {
			searchParams.put("datato", dataTo);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setBookingDAO(bookingDAO);

		return lazyModel;
	}

	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}

	public String getDataTo() {
		return dataTo;
	}

	public void setDataTo(String dataTo) {
		this.dataTo = dataTo;
	}

	@EJB
	BookingDAO bookingDAO;

	/**
	 * Funkcja w warstwie web, do pobierania danych 
	 * z bazy danych wraz z parametrami, do zpytania.
	 * @return
	 */
	public List<Boocking> getList() {
		List<Boocking> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (dataFrom != null) {
			searchParams.put("datafrom", dataFrom);
		}
		if (dataTo != null) {
			searchParams.put("datato", dataTo);
		}

		list = bookingDAO.getListSearch(searchParams);

		return list;
	}

}
