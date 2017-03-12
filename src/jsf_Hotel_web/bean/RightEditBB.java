package jsf_Hotel_web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.RightDAO;
import projectotel.entities.Right;

@ManagedBean
@ViewScoped
public class RightEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_RIGHT = "rights?faces-redirect=true";

	@EJB
	RightDAO rightDAO;

	private Integer idRight;
	private String name;

	public Integer getIdRight() {
		return idRight;
	}

	public void setIdRight(Integer idRight) {
		this.idRight = idRight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Right right = null;

	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		right = (Right) session.getAttribute("right");

		if (right != null) {
			session.removeAttribute("right");
		}

		if (right != null && right.getIdRight() != null) {
			setName(name);
		}
	}

	/**
	 * Funkcja do w warstwie web do sprawdzania poprawnoœci
	 * danych przekazywanych przez u¿ytkowników do sytemu.
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (name == null) {
			ctx.addMessage(null, new FacesMessage("name Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			right.setName(name);
			result = true;
		}

		return result;
	}

	/**
	 * Funkcja w warstwie web, do zapisywania danych.
	 * @return
	 */
	public String saveData() {

		if (right == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("B³êdne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (right.getIdRight() == null) {
				rightDAO.createRight(right);
			} else {
				rightDAO.merge(right);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_RIGHT;
	}
}
