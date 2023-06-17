/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package droidgame_paa;

import javax.swing.JFrame;

/**
 *
 * @author Asus
 */

public class MyFrame extends JFrame {
    private MyPanel panel;

    public MyFrame() {
        setTitle("Droid Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        panel = new MyPanel();
        add(panel);

        setVisible(true);
    }

}