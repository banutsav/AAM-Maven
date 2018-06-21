/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uga.ovpr.aam.email;

import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Jonathan
 */
//public class EmailThread extends Thread {
public class EmailThread implements Runnable {

    private MimeMessage message;

    public EmailThread(MimeMessage argMessage){
        message = argMessage;
    }

    @Override
    public void run() {
        try{
            Transport.send(message);
        } catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}
