package util;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author motaz
 */
public class RentryException extends Exception {
    private int code = 0;
    private String msg = "Nothing to be shown here";
    public RentryException(){
        super();
    }
    public RentryException(String msg){
        super();
        this.msg = msg;
    }
    public RentryException(int code){
        super();
        this.code = code;
    }
    public RentryException(String msg,int code){
        super();
        this.msg = msg;
        this.code = code;
    }
    public RentryException(String msg,int code,boolean show){
        super();
        this.msg = msg;
        this.code = code;
        if(show){
            this.show();
        }
    }
    public int getErrorCode(){
        return(this.code);
    }
    public void show(){
        JOptionPane optionPane = new JOptionPane("Error Code " + code + " : "+
                msg,
                JOptionPane.INFORMATION_MESSAGE
            );

            JDialog dialog = optionPane.createDialog("i");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
    }
    public static void main(){
        RentryException testingE = new RentryException("ur an admin bruh",-1,true);
        testingE.show();
        
    }
}
