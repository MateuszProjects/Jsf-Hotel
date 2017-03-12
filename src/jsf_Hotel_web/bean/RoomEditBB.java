package jsf_Hotel_web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.BookingDAO;
import into.jsf.dao.RoomDAO;
import into.jsf.dao.TyperoomDAO;
import projectotel.entities.Room;
import projectotel.entities.Typeroom;

@ManagedBean
@ViewScoped
public class RoomEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_ROOM = "room?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	RoomDAO roomDAO;

	@EJB
	BookingDAO bookingDAO;

	@EJB
	TyperoomDAO typeroomDAO;

	private Integer idRoom;
	private Integer idBooking;
	private Integer idTypoofRoom;
	private String descriptionRoom;
	private double pricePerDay;
	private int roomFloor;

	public Integer getIdRoom() {
		return idRoom;
	}

	public Integer getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(Integer idBooking) {
		this.idBooking = idBooking;
	}

	public Integer getIdTypoofRoom() {
		return idTypoofRoom;
	}

	public void setIdTypoofRoom(Integer idTypoofRoom) {
		this.idTypoofRoom = idTypoofRoom;
	}

	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
	}

	public String getDescriptionRoom() {
		return descriptionRoom;
	}

	public void setDescriptionRoom(String descriptionRoom) {
		this.descriptionRoom = descriptionRoom;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public int getRoomFloor() {
		return roomFloor;
	}

	public void setRoomFloor(int roomFloor) {
		this.roomFloor = roomFloor;
	}

	private Room room = null;

	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		room = (Room) session.getAttribute("room");

		if (room != null) {
			session.removeAttribute("room");
		}

		if (room != null && room.getIdRoom() != null) {
			setIdTypoofRoom(room.getTyperoom().getIdTypeRoom());
			setIdRoom(room.getIdRoom());
			setRoomFloor(room.getRoomFloor());
			setPricePerDay(room.getPricePerDay());
			setDescriptionRoom(room.getDescriptionRoom());
		}
	}

	/**
	 * Funkcja w warstwie web, do sprawdzania poprawnoœci
	 * danych przekazywanych przez u¿ytkownika.
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (idTypoofRoom == null) {
			ctx.addMessage(null, new FacesMessage("idTyperoom Wymagane"));
		}
		if (descriptionRoom == null || descriptionRoom.length() == 0) {
			ctx.addMessage(null, new FacesMessage("idBooking Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			Typeroom typeroom = typeroomDAO.find(idTypoofRoom);
			room.setTyperoom(typeroom);
			room.setRoomFloor(roomFloor);
			room.setPricePerDay(pricePerDay);
			room.setDescriptionRoom(descriptionRoom);
			result = true;
		}

		return result;
	}

	/**
	 * Funkcja w warstwie web, do zapisywania 
	 * obiektu room do bazy danych.
	 * @return
	 */
	public String saveData() {

		if (room == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("B³êdne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (room.getIdRoom() == null) {
				roomDAO.createRoom(room);
			} else {
				roomDAO.merge(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_ROOM;
	}
}
