package jsf_Hotel_web.bean;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import into.jsf.dao.CustomerDAO;
import into.jsf.dao.PaginationInfo;

import projectotel.entities.Customer;

public class LazyDataModelCustomer extends LazyDataModel<Customer> {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> searchParams;

	CustomerDAO customerDAO;

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	@Override
	public Customer getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Customer customer) {
		return null;
	}
	
	@Override
	public List<Customer> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Customer> list = null;
		
		PaginationInfo info = new PaginationInfo();
		
		info.setLimit(pageSize);
		info.setOffset(first);
		
		list = customerDAO.lazyFunction(searchParams, info);
		setRowCount(info.getCount());
		return list;

	}
}
