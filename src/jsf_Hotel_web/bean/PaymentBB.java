package jsf_Hotel_web.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import into.jsf.dao.PaymentDAO;
import projectotel.entities.Payment;

@ManagedBean
public class PaymentBB {

	private static final String PAGE_PAYMENT_EDIT = "paymentedit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@EJB
	PaymentDAO paymentDAO;

	/**
	 * Funkca w warstwie web, do przekazywania parametrów do zapytania do bazy
	 * danych.
	 * 
	 * @return
	 */
	public List<Payment> getList() {
		List<Payment> list = null;
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (amount != null) {
			searchParams.put("amount", amount);
		}

		list = paymentDAO.getSearchList(searchParams);

		return list;
	}

	/**
	 * Funkcja w warstwie web, do tworzenia nowego obiektu payment.
	 * 
	 * @return
	 */
	public String newPayment() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Payment payment = new Payment();
		session.setAttribute("payment", payment);
		return PAGE_PAYMENT_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do edytowania obiektu payment.
	 * 
	 * @param payment
	 * @return
	 */
	public String editPayment(Payment payment) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("payment", payment);
		return PAGE_PAYMENT_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do usuwania obiektu payment.
	 * 
	 * @param payment
	 * @return
	 */
	public String delete(Payment payment) {
		paymentDAO.remove(payment);
		return PAGE_STAY_AT_THE_SAME;
	}

}
