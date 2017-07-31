package src.core;

import src.commons.Globals;

import javax.swing.*;
import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {
        Lazarus lazarus = new Lazarus();
        JFrame window = new JFrame();
        window.setTitle("Lazarus!!!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(lazarus);
	    window.setBounds(50, 50, Globals.BOARD_SIZE + 18, Globals.BOARD_SIZE + 47);
        window.setResizable(true);
        window.setLocationByPlatform(true);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        lazarus.start();
        System.out.println("Done");
    }
}
