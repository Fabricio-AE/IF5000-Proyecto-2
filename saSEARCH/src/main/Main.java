package main;

import GUI.Panel;
import saSearch.SaSEARCHConnection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        
        try {
            
            SaSEARCHConnection slave = SaSEARCHConnection.getInstance();
            
             JFrame frame = new JFrame();
            frame.setSize(350,500);
            frame.setLocationRelativeTo(null);
            
            Panel panel = new Panel();
            
            frame.add(panel);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        }catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }//main

}//end class
