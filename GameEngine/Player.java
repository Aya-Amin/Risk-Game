package GameEngine;

import RiskGame.Board;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Player implements Cloneable
{
    private final int DEOPLOYMENT = 0;
    private final int CHOOSEATTACKER = 1;
    private final int CHOOSEDEFENDER = 2;
    private final int CHOOSETOMOVEFROM = 3;
    private final int CHOOSETOMOVETO = 4;
    //==========================================
    
    private String name;
    private int availableTroops; //no of undeployed troops
    protected ArrayList<Territory> listOfTerritories;
    protected Territory attackingTerritory;
    protected Territory defendingTerritory;
    public String fromId;
    public String toId;
    protected Controller controller;
    private int gamePhase;
    public Board board;

    public void setAvailableTroops(int availableTroops)
    {
        this.availableTroops = availableTroops;
    }

    public void setListOfTerritories(ArrayList<Territory> listOfTerritories)
    {
        this.listOfTerritories = listOfTerritories;
    }

    public void setDefendingTerritory(Territory defendingTerritory)
    {
        this.defendingTerritory = defendingTerritory;
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public Player(String name) throws CloneNotSupportedException
    {
        this.availableTroops = 20;
        listOfTerritories = new ArrayList();
        this.name = name;
        controller = Controller.getInstance(null);
       
    }

    public Territory getAttackingTerritory()
    {
        return attackingTerritory;
    }

    public void setAttackingTerritory(String attackingTerritory)
    {
        for (int i = 0; i < listOfTerritories.size(); i++)
        {
            if (listOfTerritories.get(i).getTerritoryID().equals(attackingTerritory))
            {
                this.attackingTerritory = listOfTerritories.get(i);
                return;
            }
        }
    }

    public Territory getDefendingTerritory()
    {
        return defendingTerritory;
    }

    public void setDefendingTerritory(String territoryId)
    {
        for (Territory t : this.attackingTerritory.getAdjacentTerritoris())
        {
            if (t.getTerritoryID().equalsIgnoreCase(territoryId))
            {
                this.defendingTerritory = t;
                return;
            }

        }
    }

    public Territory getTerritoryByID(String territoryID)
    {
        for (int i = 0; i < listOfTerritories.size(); i++)
        {
            if (listOfTerritories.get(i).getTerritoryID().equals(territoryID))
            {

                return listOfTerritories.get(i);
            }
        }
        return null;
    }

    public ArrayList<Territory> getAdjacentTerritoriesIds(String attackingTerritory)
    {
        for (Territory t : listOfTerritories)
        {
            if (t.getTerritoryID().equalsIgnoreCase(attackingTerritory))
            {
                this.attackingTerritory = t;
                break;
            }
        }

        int i = 0;
        String[] adjacentIds = new String[this.attackingTerritory.getAdjacentTerritoris().size()];
        for (Territory t : this.attackingTerritory.getAdjacentTerritoris())
        {
            adjacentIds[i] = t.getTerritoryID();
            i++;
        }
        return this.attackingTerritory.getAdjacentTerritoris();
    }

    public abstract boolean attack(Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackers);

    public abstract void getDeploymentTerritory();

    public abstract void deployTroops(String territory);
    
    /* Select the amount of troops to deploy to each territory.
	 Call from GUI, after deployment, based on player's selection of extra troops deployment. It shall be
	 called several times depending on user's preference of deployment.
	 In GUI, force the player to deploy all the extra troops in to territories. */
//    public abstract void deployTroops(String territory);
//    protected abstract Territory sortOwnedTerritories();
    // protected abstract Territory sortNeighbourTerritories();
    public abstract void movement(int troopsToMove);

     public void play(Territory territory)
    {
        switch(gamePhase)
        {
            case DEOPLOYMENT:
                this.deployTroops(territory.getTerritoryID());
                break;
            case CHOOSEATTACKER:
                this.setAttackingTerritory(territory.getTerritoryID());
                this.setGamePhase(CHOOSEDEFENDER);
                break;
            case CHOOSEDEFENDER:
                this.setDefendingTerritory(territory.getTerritoryID());
                this.getListOfTerritories().add(territory);
                territory.setOwner(this);
                this.setGamePhase(DEOPLOYMENT);
                
                break;
        }
    }
    public void addTerritory(Territory territory)
    {
        listOfTerritories.add(territory);
    }

    public void removeTerritory(Territory territory)
    {
        listOfTerritories.remove(territory);
    }

    public int getAvailabelTroops()
    {
        return availableTroops;
    }

    public void setAvailabelTroops(int availabelTroops)
    {
        this.availableTroops = availabelTroops;
    }

    public ArrayList<Territory> getListOfTerritories()
    {
        return listOfTerritories;
    }

    public String getFromId()
    {
        return fromId;
    }

    public void setFromId(String fromId)
    {
        this.fromId = fromId;
    }

    public String getToId()
    {
        return toId;
    }

    public void setToId(String toId)
    {
        this.toId = toId;
    }

    public String getName()
    {
        return name;
    }

    public int getTotalTroops()
    {
        int troops = 0;
        for (int i = 0; i < listOfTerritories.size(); i++)
        {
            troops += listOfTerritories.get(i).getTroops();
        }
        return troops;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();

    }

    public void setGamePhase(int gamePhase)
    {
        this.gamePhase = gamePhase;
    }
    
}
