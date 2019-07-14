/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import GUI.GameBoard;
import GameEngine.Player;
import GameEngine.Territory;
import RiskGame.Board;
import com.sun.deploy.uitoolkit.impl.fx.ui.resources.Deployment;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author Omar
 */
public class Search
{
    //PREDEFINES for the game State
    private final int DEOPLOYMENT = 0;
    private final int CHOOSEATTACKER = 1;
    private final int CHOOSEDEFENDER = 2;
    private final int CHOOSETOMOVEFROM = 3;
    private final int CHOOSETOMOVETO = 4;
    //==========================================
    
    
   private Board gameBoard;
 
   private HeuristicCalculator heuristicCalculator;
   private PriorityQueue<Node> heap;
   private HashSet<Node> explored;
   private AStarPlayer searchPlayer;
   private static Search search;
  

    private Search()
    {
        //this.board = GameBoard.getInstance();
        heuristicCalculator = HeuristicCalculator.getInstance();
        
      
       
        
    }
    
    public static Search getInstance()
    {
        if(search==null)
            return new Search();
        else
            return search;
    }
   private boolean isGoal( )
   {
      return (searchPlayer.getListOfTerritories().size()/gameBoard.getTerritoriesList().size()) >=0.7;
   }
   
    
   
    
    
    
    /*
    
    public Territory AStartSearch(AStarPlayer player)
    {
        ArrayList<Territory> possibleMoves;
        searchPlayer = new  AStarPlayer(player);
       
       
        heap = new PriorityQueue(new NodesComparetor());
        explored =new HashSet<Node>();
        for(Territory territory: searchPlayer.getListOfTerritories())
        {     
        Node node = new Node(searchPlayer.getGameState(),territory,0,null,0);
        heap.add(node);
        
       while(!heap.isEmpty())
       {
           node = heap.remove();
                    
           explored.add(node);
           possibleMoves = getPossibleMoves(searchPlayer);
           
            Node childNode;
           for(Territory territory:possibleMoves )
           {
                childNode = new Node(searchPlayer.getGameState(),territory,node.getPathCost()+1,node,heuristicCalculator.getNBSRHeuristic(searchPlayer, territory));
                searchPlayer.play(territory);
                  if(isGoal())
                     return getSolution(childNode);
                if(!decreasekey(childNode) && !explored.contains(childNode))
                {
                    System.out.println("Searching: Node Added => "+ childNode.getTargetTerritory().getTerritoryID()); 
                    heap.add(childNode);
                }
              
            }
         
       }
        
        }
        
        
         return null;
        
    }
    
     
  
    
   
   private boolean decreasekey( Node node)
   {
       Node[] nodes = new Node[heap.size()];
       nodes = heap.toArray(nodes);
       boolean flag=false;
       for(int i=0;i<nodes.length;i++)
       {    
           
           if(nodes[i].getTargetTerritory().getTerritoryID().equals(node.getTargetTerritory().getTerritoryID()) &&nodes[i].getGamePhase()==node.getGamePhase() )
           {
               flag = true;
               if( nodes[i].getHeuristicBSR()>node.getHeuristicBSR())
               nodes[i].setHeuristicBSR(node.getHeuristicBSR());
               return flag;
           }
       }
       return flag;
   }
   
   private Territory getSolution(Node node)
   {
       Node parent = node.getParent();
       Node temp =parent;
       
       while(parent !=null)
       {
           temp = parent;
           parent = parent.getParent();
       }
       return(Territory) temp.getTargetTerritory();
   }
   
   public Territory getBestDeployment(AStarPlayer player)
   {
       
       Float[] scores = new Float[player.getListOfTerritories().size()];
       HashMap<Territory,Float> map = new HashMap();
       ArrayList<Territory> currentListOfTerritories = player.getListOfTerritories();
       float max = -99;
       float heuristic;
       int index=0;
       for(int i=0;i<currentListOfTerritories.size();i++)
       {
           heuristic = heuristicCalculator.getNBSRHeuristic(player, currentListOfTerritories.get(i));
           if(max < heuristic)
           {
               max = heuristic;
               index =i;
           }
       }  
      
      return currentListOfTerritories.get(index);
        
   }
   
   */
   
   public int MiniMax(Board board,int depth ,int maxDepth, int turn, int alpha, int beta) throws CloneNotSupportedException
    {
        int score;
        int newScore=0;
        int bestScore=0;
        Territory bestTerritory=null;
        if(depth==maxDepth)
            return HeuristicCalculator.getInstance().getPlayerScore(board.getListOfPlayers().get(0));
        
        ArrayList<Territory> moves = board.getPossibleMoves();
        if (moves.size()==0)
            return HeuristicCalculator.getInstance().getPlayerScore(board.getListOfPlayers().get(0));
        
        for(Territory territory: moves)
        {
           
            if(turn==1)
             bestScore =-999;
            else
             bestScore=999;
                
            Board newBoard = new Board(board.getListOfPlayers(),board.getTerritoriesList(),turn);
            board.getCurrentPlayer().play(territory);
            int v = MiniMax(newBoard,depth+1,maxDepth,~turn,alpha,beta);
            
            if(turn==1 && bestScore < v )
            {
               bestScore =v;
               alpha = max(alpha,bestScore);
               bestTerritory = territory;
               if(beta < alpha) break;
            }
            else if(turn ==0 && bestScore>v)
            {
                bestScore =v;
                bestTerritory = territory;
                beta = min(beta,bestScore);
                if (beta < alpha) break;
            }
            
            if(depth==0)
                board.getCurrentPlayer().play(bestTerritory);
        }
        return bestScore;
    }
   
}
