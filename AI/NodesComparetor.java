/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import java.util.Comparator;

/**
 *
 * @author Omar
 */
public class NodesComparetor implements Comparator<Node>
{

    @Override
    public int compare(Node o1, Node o2)
    {
        if(o1.getHeuristicBSR()>o2.getHeuristicBSR())
            return -1;
        else
          if (o1.getHeuristicBSR()==o2.getHeuristicBSR())
              return 0;
          else return 1;
        
    }
   
    
}
