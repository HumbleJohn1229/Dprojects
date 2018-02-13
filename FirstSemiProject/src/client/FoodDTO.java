package client;

import java.io.Serializable;

public class FoodDTO implements Serializable{
	private String eatDate;
	private String restraunt;
	private String name;
	private int eatTimes;
	
	public FoodDTO() {}
	
	public String getRestraunt() {
		return restraunt;
	}

	public void setRestraunt(String restaurant) {
		this.restraunt = restaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEatDate() {
		return eatDate;
	}

	public void setEatDate(String eatDate) {
		this.eatDate = eatDate;
	}

	public int getEatTimes() {
		return eatTimes;
	}

	public void setEatTimes(int eatTimes) {
		this.eatTimes = eatTimes;
	}
	
	
	
}
