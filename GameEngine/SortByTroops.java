package GameEngine;

import java.util.Comparator;

public class SortByTroops  implements Comparator<Territory>{

	@Override
	public int compare(Territory arg0, Territory arg1) {
		return arg0.getTroops() - arg1.getTroops();
	}
	

}
