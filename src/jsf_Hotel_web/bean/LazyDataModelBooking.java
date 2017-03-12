package jsf_Hotel_web.bean;


import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import into.jsf.dao.BookingDAO;
import into.jsf.dao.PaginationInfo;
import projectotel.entities.Boocking;

public class LazyDataModelBooking extends LazyDataModel<Boocking> {
	private static final long serialVersionUID = 1L;
	
	BookingDAO bookingDAO;
	
	private Map<String, Object> searchParams;

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public void setBookingDAO(BookingDAO bookingDAO) {
		this.bookingDAO = bookingDAO;
	}

	@Override
	public Boocking getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Boocking booking) {
		return null;
	}

	@Override
	public List<Boocking> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Boocking> list = null;

		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);

		list = bookingDAO.getAdminList(searchParams, info);
		setRowCount(info.getCount());

		return list;
	}

}
