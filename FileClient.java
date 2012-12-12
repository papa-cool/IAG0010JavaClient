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
    
    FileClient(String pathname) throws IOException{
            file = new File(pathname);
            file.createNewFile();
            System.out.println("The downloaded file will be saved in the following address :\n"+pathname);
            Main.windowControl.writeInTextArea("The downloaded file will be saved in the following address :\n"+pathname);
            out_file = new BufferedOutputStream(new FileOutputStream(file, true), 2048);
    }
    
    public void writeInFile(byte[] buffer){
        try {
            out_file.write(buffer, 4,Convertor.GetIntFromByteArray(buffer, 0));
        } catch (IOException ex) {
            Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() throws IOException{
            out_file.close();
            Main.fileClient = null;
            System.out.println("Close file.");
            Main.windowControl.writeInTextArea("Close file.");
    }

    
    public void launchFileWithDefaultApplication(){
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
}
