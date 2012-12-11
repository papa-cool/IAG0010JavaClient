/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IAG0010JavaClient;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emmanuel
 */
public class FileClient {
    
    private File file;
    private BufferedOutputStream out_file;
    //private BufferedWriter in_file;
    
    FileClient(String pathname){
        try {
            file = new File(pathname);
            file.createNewFile();
            out_file = new BufferedOutputStream(new FileOutputStream(file, true), 2048);
            //in_file = new BufferedWriter(new FileWriter(file, true), 2048); // true : Le buffer est ajouté à la fin du fichier. 2048 : Taille du buffer.
        } catch (IOException ex) {
            Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeInFile(byte[] buffer){
        try {
            out_file.write(buffer, 4,Convertor.GetIntFromByteArray(buffer, 0));
        } catch (IOException ex) {
            Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            /*try {
                in_file.write(addString);
                in_file.flush();
                System.out.println("Write string in the file.");
            } catch (IOException ex) {
                System.out.println("write() failed");
                Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
            }*/
    }
    
    public void closeBufferedWriter(){
        try {
            System.out.println("Close BufferWriter of the file.");
            out_file.close();
            Main.windowControl.writeInTextArea("Close BufferWriter of the file.");
        } catch (IOException ex) {
            Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void openFile(){
        if (this.file.getPath() == null)
            throw new NullPointerException ();
        if (!Desktop.isDesktopSupported ())
            return;
        Desktop desktop = Desktop.getDesktop ();
        try {
            desktop.open (this.file);
            Main.windowControl.writeInTextArea("Application automatically launch to open the file.");
            System.out.println("Application automatically launch to open the file.");
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
    }
    
    public void closeFile(){
    }
}
