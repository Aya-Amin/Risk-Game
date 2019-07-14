package GameEngine;

import java.util.ArrayList;

public class Territory implements Cloneable
{

    private String territoryID;  // unique identifier of each territory. As if that's its name.
    private int troops;
    private Player owner;
    private ArrayList<Territory> adjacentTerritories;

    public String getTerritoryID()
    {
        return territoryID;
    }

    public void setTerritoryID(String territoryID)
    {
        this.territoryID = territoryID;
    }

    public Territory(String id)
    {
        this.troops = 0;
        this.owner = null;
        this.territoryID = id;
        adjacentTerritories = new ArrayList();
    }

    public void addAdjacentTerritory(Territory territory)
    {
        this.adjacentTerritories.add(territory);
    }

    public ArrayList<Territory> getAdjacentTerritoris()
    {
        return this.adjacentTerritories;
    }

    public int getTroops()
    {
        return troops;
    }

    public void setTroops(int troops)
    {
        this.troops = troops;
    }

    public Player getOwner()
    {
        return owner;
    }

    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
