package main;

import GUI.GUITunnelingSystem;

import javax.swing.*;


public class Main {

    public static void main(String args[]) {
        System.out.println("[STARTED] VPN-Tunneling-System");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUITunnelingSystem();
            }
        });
    }
}
