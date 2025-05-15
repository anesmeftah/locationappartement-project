/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import javax.swing.JFrame;

/**
 *
 * @author motaz
 */
public class baseController {
    protected JFrame mainFrame;
    
    public void setMainFrame(JFrame F){
        if (F == null) {
            System.out.println("Warning: Attempting to set null mainFrame");
        } else {
            System.out.println("Setting mainFrame: " + F);
        }
        this.mainFrame = F;
    }
    
    /**
     * Try to find the main JFrame from the current application context
     * @return The JFrame or null if not found
     */
    protected JFrame findMainFrame() {
        if (mainFrame != null) {
            return mainFrame;
        }
        
        // Try to find the mainFrame by looking at all windows
        for (java.awt.Window win : java.awt.Window.getWindows()) {
            if (win instanceof JFrame && win.isVisible()) {
                System.out.println("Found a visible JFrame: " + win);
                return (JFrame) win;
            }
        }
        
        return null;
    }
}
