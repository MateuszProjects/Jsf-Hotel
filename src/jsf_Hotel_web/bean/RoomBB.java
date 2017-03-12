package jsf_Hotel_web.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import into.jsf.dao.RoomDAO;
import projectotel.entities.Room;
import projectotel.entities.Typeroom;

@ManagedBean
public class RoomBB {

	private static final String PAGE_TYPE_ROOM_EDIT = "typeroomedit?faces-redirect=true";
	private static final String PAGE_ROOM_EDIT = "roomedit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	Double price;
	Integer floor;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	@EJB
	RoomDAO roomDAO;

	/**
	 * Funkcja przekazuj¹ca parametry do 
	 * zapytania do bazy danych.
	 * @return
	 */
	public List<Room> getListf() {
		List<Room> list = null;
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (price != null) {
			searchParams.put("price", price);
		}

		if (floor != null) {
			searchParams.put("floor", floor);
		}

		list = roomDAO.getSearchList(searchParams);

		return list;
	}

	/**
	 * Funkcja w warstwie web, do tworzenia 
	 * nowego obiektu room.
	 * @return
	 */
	public String newRoom() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Room room = new Room();
		session.setAttribute("room", room);
		return PAGE_ROOM_EDIT;
	}

	/**
	 * Funkcja  w warstwie web, do edycji 
	 * obiektu room.
	 * @param room
	 * @return
	 */
	public String editRight(Room room) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("room", room);
		return PAGE_ROOM_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do usuwania 
	 * obiektu room.
	 * @param room
	 * @return
	 */
	public String delete(Room room) {
		roomDAO.remove(room);
		return PAGE_STAY_AT_THE_SAME;
	}

	/**
	 * Funkcja w warstwie web, do tworzenia 
	 * nowego obiektu typrroom.
	 * @param room
	 * @return
	 */
	public String addTypeRoom() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Typeroom typeroom = new Typeroom();
		session.setAttribute("typeroom", typeroom);
		return PAGE_TYPE_ROOM_EDIT;
	}

}
