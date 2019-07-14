package GameEngine;

import GUI.GameBoard;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class Map {
	
	private String name; 
	private ArrayList<Territory> territories;
        private Bridge bridge;
        private Browser browser;
	
	/*when user chooses a map(usa/egypt) create a map object by sending to the constructor its name*/
	public Map(ArrayList<Territory> territories) {
		
		this.territories =new ArrayList(territories);
                bridge = Bridge.getInstance();
                browser = GameBoard.getInstance().getBrowser();
              
                
	}
	
	/*after creating the map object in gui, call initialize map and send to it the map name*/
	public ArrayList<Territory> getTerritories() {
		return territories;
	}
        
        public Territory getTerritoryByID(String id)
        {
            int size = territories.size();
            for(int i=0;i<size;i++)
            {
                if(territories.get(i).getTerritoryID().trim().equals(id.trim()))
                    return territories.get(i);
            }
            return null;
        }

}
