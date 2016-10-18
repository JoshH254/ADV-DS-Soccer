//Phillip Si, Josh Holden, Tony Lee
//Graphics-Asg1: Bouncing Shapes

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Formatter;
import java.util.*;
import javax.swing.*;

public class IndoorSoccerSimulator_SiHoldLee {
   public static void main(String[] args) {
      //int n = 11; //Integer.parseInt(JOptionPane.showInputDialog("How many balls do you want to put in motion?"));
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame("Soccer Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new SoccerGame(2000, 2600));
            frame.pack();// Preferred size of SoccerGame
            frame.setVisible(true);
         }
      });
   }
}


