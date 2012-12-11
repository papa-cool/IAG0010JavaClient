/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IAG0010JavaClient;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Emmanuel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static SocketClient socketClient;
    public static FileClient fileClient;
    public static Semaphore semToSendReady = new Semaphore(0);
    public static Semaphore semToRecvPacket = new Semaphore(0);
    public static SendingThread sendingThread;
    public static ReceivingThread receivingThread;
    public static WindowControl windowControl;
    
    public static void main(String[] args) throws Exception {
        windowControl = new WindowControl();
        /*
        new ReceivingThread().start();
        sendingThread.start();
        */
    }
}
