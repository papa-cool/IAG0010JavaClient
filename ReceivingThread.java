/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IAG0010JavaClient;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emmanuel
 */
public class ReceivingThread extends Thread{
    
    public ReceivingThread(){
        this.start();
    }
    
    @Override
    public void run(){
        try {
            Main.socketClient.recvHello();
            while(true){
                Main.semToRecvPacket.acquire();
                Main.fileClient.writeInFile(Main.socketClient.recvPacket());
                Main.semToSendReady.release();
            }
        } catch (SocketTimeoutException ex) {
            System.out.println("All the document received.");
            Main.windowControl.writeInTextArea("All the document received.");
            System.out.println("Opening of the received file in a default application.");
            Main.windowControl.writeInTextArea("Opening of the received file in a default application.");
            Main.fileClient.launchFileWithDefaultApplication();
        } catch (InterruptedException ex) {
            System.out.println("The receivingThread has been interrupted");
            Logger.getLogger(ReceivingThread.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (ParseException ex) {
            Logger.getLogger(ReceivingThread.class.getName()).log(Level.SEVERE, null, ex);
            Main.windowControl.informFailedSystem();
        } catch (SocketException ex) {
            if(this.isInterrupted())
                return;
            Logger.getLogger(ReceivingThread.class.getName()).log(Level.SEVERE, null, ex);
            Main.windowControl.informConnectionAbort();
        } catch (IOException ex) {
            Logger.getLogger(ReceivingThread.class.getName()).log(Level.SEVERE, null, ex);
            Main.windowControl.informFailedSystem();
        }
        if(Main.socketClient != null)
            Main.socketClient.close();
        Main.windowControl.informEndOfDownloading();
    }
}
