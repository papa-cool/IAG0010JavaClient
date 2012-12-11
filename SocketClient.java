/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IAG0010JavaClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emmanuel
 */
public class SocketClient {

    //Attribut
    private String address = "localhost";
    private int port = 1234;
    private Socket socket;
    private BufferedOutputStream out_socket;
    private BufferedInputStream in_socket;

    SocketClient() {
        try {
            socket = new Socket(address, port);
            out_socket = new BufferedOutputStream(socket.getOutputStream(), 44);
            in_socket = new BufferedInputStream(socket.getInputStream(), 2048);
            socket.setSoTimeout(6000);
        } catch (ConnectException ex) {
            System.out.println("Impossible de se connecter !");
            Main.windowControl.writeInTextArea("Impossible de se connecter !");
        } catch (SocketException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        try {
            if(Main.receivingThread.isAlive())
                Main.receivingThread.interrupt();
            if(Main.sendingThread.isAlive())
                Main.sendingThread.interrupt();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isConnected() {
        return socket.isConnected();
    }

    private void send(String sendText, int lenghtText) throws IOException{
        byte[] buffer = new byte[lenghtText+4];
        Convertor.PutIntIntoByteArray(buffer, 0, lenghtText);
        Convertor.PutStringIntoByteArray(buffer, 4, sendText);
        out_socket.write(buffer, 0, lenghtText+4);
        System.out.println("Message envoye : "+sendText);
    }
    
    public void sendHello() throws IOException{
        send("Hello IAG0010Server", 40);
    }
    
    public void sendReady() throws IOException{
        send("Ready", 40);
    }
    
    public void recvHello() throws ParseException, IOException{
        String recvText;
        int lenghtText;
        int lenghtMessage = 44;
        byte[] buffer = new byte[lenghtMessage];
        in_socket.read(buffer, 0, lenghtMessage);
        lenghtText = Convertor.GetIntFromByteArray(buffer, 0);
        recvText = Convertor.GetStringFromByteArray(buffer, 4, lenghtMessage);
        System.out.println("Longueur du text : "+lenghtText+"\nMessage recu : "+recvText);
    }
    
    public byte[] recvPacket() throws ParseException, IOException{
        //String recvText;
        int lenghtText;
        int lenghtMessage = 2048;
        byte[] buffer = new byte[lenghtMessage];
        in_socket.read(buffer, 0, lenghtMessage);
        lenghtText = Convertor.GetIntFromByteArray(buffer, 0);
        //recvText = Convertor.GetStringFromByteArray(buffer, 4, lenghtMessage);
        System.out.println("Texte de "+lenghtText+" octets recus");
        Main.windowControl.writeInTextArea(lenghtText+" bytes got");
        //return recvText;
        return buffer;
    }
}
