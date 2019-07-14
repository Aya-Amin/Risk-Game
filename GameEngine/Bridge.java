/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import GUI.GameBoard;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 *
 * @author Omar
 */
public class Bridge
{
    Browser browser;
    private static Bridge bridge;
    private  JSValue document;
    private  JSValue async;
    private final Controller controller;
    
    public static Bridge getInstance() throws CloneNotSupportedException
    {
        if(bridge==null)
            return new Bridge();
        else
            return bridge;
    }
    private Bridge() throws CloneNotSupportedException
    {
        browser = GameBoard.getInstance().getBrowser();
        controller = Controller.getInstance(null);
        bridge = this;
        
         
        
        
            
        
    }
    
    public void enableTerritories(ArrayList<Territory> Territories)
    {
        
        JSValue document = browser.executeJavaScriptAndReturnValue("document");
        JSValue async = document.asObject().getProperty("asyncFunc");
        int  k = Territories.size();
        String s = Territories.get(0).getTerritoryID();
        for (int i= 1 ; i < k; i++) 
        {
              s += "|" + Territories.get(i).getTerritoryID() ;
        }
        System.out.println(s);
         async.asFunction().invokeAsync(document.asObject(), s);
       
        
    }
    
    
    public void recivedClick(String territory) throws CloneNotSupportedException
    {
     
       int boardState = Controller.getInstance(null).getBoardState();
        System.out.println("Game state is: "+boardState);
       switch(boardState)
       {
           case 0: 
               controller.deployNewTroop(territory);
               break;
           case 1:
               controller.setAttacker(territory);
               break;
           case 2:
               controller.setDefender(territory);
               break;
       }
         
         
       
    }
    
    
}
