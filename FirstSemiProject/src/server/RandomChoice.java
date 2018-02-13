package server;

import java.util.List;

import client.FoodDTO;

public class RandomChoice {
	private List<FoodDTO> yesterdayfood;

	public RandomChoice(List<FoodDTO> yesterdayfood) {
		this.yesterdayfood = yesterdayfood;
	}

	// 리스트 중에서 랜덤해서 하나를 선택해주는 기능
	public FoodDTO pickOne() {
		int selectedTimes = 0;
		FoodDTO result = null;

		while (true) {
			if (PerClientThread.foods.size() > 0) {
				int randomNum = (int) (Math.random() * PerClientThread.foods.size());
				result = PerClientThread.foods.get(randomNum);

				for (FoodDTO e : yesterdayfood)
					if (result.getRestraunt().equals(e.getRestraunt()) && selectedTimes < 2) {
						selectedTimes++;
						continue;
					}
				return result;
			} else{
				return null;
			}
		}

	}

}
