/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IAG0010JavaClient;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emmanuel
 */
public class SendingThread extends Thread{
    
    public SendingThread(){
        this.start();
    }
    
    @Override
     public synchronized void run(){
        try {
            Main.socketClient.sendHello();
            Main.semToRecvPacket.release();
            while(true){
                Main.semToSendReady.acquire();
                Main.socketClient.sendReady();
                Main.semToRecvPacket.release();
            }
        } catch (InterruptedException ex) {
            System.out.println("The sendingThread has been interrupted");
            return;
        } catch (SocketException ex) {
            if(this.isInterrupted())
                return;
            Logger.getLogger(SendingThread.class.getName()).log(Level.SEVERE, null, ex);
            Main.windowControl.informConnectionAbort();
        } catch (IOException ex) {
            Logger.getLogger(SendingThread.class.getName()).log(Level.SEVERE, null, ex);
            Main.windowControl.informFailedSystem();
        }
        if(Main.socketClient != null)
            Main.socketClient.close();
    }    
}
