package jsf_Hotel_web.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import java.util.List;
import into.jsf.dao.TyperoomDAO;
import projectotel.entities.Typeroom;

@ManagedBean
public class TyperoomBB {

	private static final String PAGE_TYPE_ROOM_EDIT = "typeroomedit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	TyperoomDAO typeroomDAO;

	/**
	 * Funkcja w warstwie web, do pobierania 
	 * danych z bazy danych.
	 * @return
	 */
	public List<Typeroom> getList() {
		return typeroomDAO.getFullList();
	}

	/**
	 * Funkcja w warstwie web, do tworzenia 
	 * nowego obiektu typeroom.
	 * @return
	 */
	public String newTyperoom() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Typeroom typeroom = new Typeroom();
		session.setAttribute("typeroom", typeroom);
		return PAGE_TYPE_ROOM_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do edycji obiektu 
	 * typeroom.
	 * @param typeroom
	 * @return
	 */
	public String editTyperoom(Typeroom typeroom) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("typeroom", typeroom);
		return PAGE_TYPE_ROOM_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do usuwania obiektu typeroom.
	 * @param typeroom
	 * @return
	 */
	public String delete(Typeroom typeroom) {
		typeroomDAO.remove(typeroom);
		return PAGE_STAY_AT_THE_SAME;
	}
}
