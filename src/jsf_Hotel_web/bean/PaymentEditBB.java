package jsf_Hotel_web.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.BookingDAO;
import into.jsf.dao.PaymentDAO;
import projectotel.entities.Boocking;
import projectotel.entities.Payment;

@ManagedBean
@ViewScoped
public class PaymentEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PAYMENT = "payment?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	PaymentDAO paymentDAO;

	@EJB
	BookingDAO bookingDAO;

	private Integer idPayment;
	private Integer idBooking;
	private double amount;
	private byte paid;
	private String paymentData;

	public Integer getIdPayment() {
		return idPayment;
	}

	public Integer getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(Integer idBooking) {
		this.idBooking = idBooking;
	}

	public void setIdPayment(Integer idPayment) {
		this.idPayment = idPayment;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public byte getPaid() {
		return paid;
	}

	public void setPaid(byte paid) {
		this.paid = paid;
	}

	public String getPaymentData() {
		return paymentData;
	}

	public void setPaymentData(String paymentData) {
		this.paymentData = paymentData;
	}

	private Payment payment = null;
	private Boocking booking = null;

	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		payment = (Payment) session.getAttribute("payment");
		booking = (Boocking) session.getAttribute("booking");

		if (payment != null) {
			session.removeAttribute("payment");
		}
		if (booking != null) {
			session.removeAttribute("booking");
		}

		if (payment != null && payment.getIdPayment() != null) {
			setIdPayment(payment.getIdPayment());
			setIdBooking(payment.getBoocking().getIdBoocking());
			setAmount(payment.getAmount());
			setPaid(payment.getPaid());
			String date = new SimpleDateFormat("dd-MM-yyyy").format(payment.getPaymentData());
			setPaymentData(date);
		}

		if (booking != null) {
			setIdBooking(booking.getIdBoocking());
		}

	}

	/**
	 * Funkcja w warstwie web, do sprawdzania poprawnoœci
	 * przekazywanych danych przez u¿ytkownika.
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (idBooking == null) {
			ctx.addMessage(null, new FacesMessage("idBooking Wymagane"));
		}
		if (paymentData == null || paymentData.length() == 0) {
			ctx.addMessage(null, new FacesMessage("paymentData Wymagane"));
		}

		Date datePayment = null;
		try {
			datePayment = new SimpleDateFormat("dd-MM-yyyy").parse(paymentData);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (ctx.getMessageList().isEmpty()) {
			Boocking boocking = bookingDAO.find(idBooking);
			payment.setBoocking(boocking);
			payment.setAmount(amount);
			payment.setPaid(paid);
			payment.setPaymentData(datePayment);
			result = true;
		}

		return result;
	}

	/**
	 * Funkcja w warstwie web, do zapisywania danych.
	 * @return
	 */
	public String saveData() {

		if (payment == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("B³êdne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (payment.getIdPayment() == null) {
				paymentDAO.createPayment(payment);
			} else {
				paymentDAO.merge(payment);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_PAYMENT;
	}
}
