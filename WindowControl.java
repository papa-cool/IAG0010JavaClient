/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IAG0010JavaClient;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Emmanuel
 */
public class WindowControl extends JFrame {

    private JTextArea jTextArea = new JTextArea("Welcome in IAG0010JavaClient\n", 20, 35);
    private JScrollPane jScrollPane = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JButton buttonConnect = new JButton("Connect");
    private JButton buttonBreak = new JButton("Disconnect");
    private JPanel panelConnection = new JPanel();
    private JButton buttonOpen = new JButton("Open");
    private JButton buttonClose = new JButton("Close");
    private JPanel panelFile = new JPanel();
    private JButton buttonExit = new JButton("Exit");

    public WindowControl() {
        // Configuration fenêtre
        this.setTitle("IAG0010Client");
        this.setSize(600, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            // windowsClosing when the X button is clicked.
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));


        buttonConnect.setEnabled(false);
        buttonBreak.setEnabled(false);
        buttonClose.setEnabled(false);


        // Button Listener


        buttonConnect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    System.out.println("Connect");
                    Main.socketClient = new SocketClient();
                    buttonConnect.setEnabled(false);
                    buttonBreak.setEnabled(true);
                    Main.receivingThread = new ReceivingThread();
                    Main.sendingThread = new SendingThread();
                } catch (ConnectException ex) {
                    JOptionPane.showMessageDialog(WindowControl.this, "Check that the server is listening the network.", "Connection impossible !", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Impossible to connect the client to the server !\nCheck that the server is listening the network.");
                } catch (UnknownHostException ex) {
                    Logger.getLogger(WindowControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SocketException ex) {
                    Logger.getLogger(WindowControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(WindowControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        buttonBreak.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println("Diconnect");
                WindowControl.this.writeInTextArea("Disconnection");
                Main.socketClient.close();
                if (Main.socketClient.isConnected()) {
                    WindowControl.this.writeInTextArea("Client is disconnected from the server.");
                    buttonBreak.setEnabled(false);
                    buttonConnect.setEnabled(true);
                }
            }
        });
        buttonOpen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    System.out.println("Open");
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Choose the target directory to download the file !");
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.showOpenDialog(WindowControl.this);
                    Main.fileClient = new FileClient(fileChooser.getSelectedFile() + File.separator + "TTU.doc");
                    buttonConnect.setEnabled(true);
                    buttonOpen.setEnabled(false);
                } catch (IOException ex) {
                    Logger.getLogger(WindowControl.class.getName()).log(Level.SEVERE, null, ex);
                    WindowControl.this.informDirectoryNotExist();
                }
                
            }
        });
        buttonClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    Main.fileClient.close();
                    buttonClose.setEnabled(false);
                    buttonOpen.setEnabled(true);
                } catch (IOException ex) {
                    Logger.getLogger(WindowControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        buttonExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                exitApplication();
            }
        });


        // Template


        jTextArea.setPreferredSize(new Dimension(600, 500));
        jScrollPane.setViewportView(jTextArea);
        jTextArea.setLineWrap(true);
        this.getContentPane().add(jScrollPane);

        panelConnection.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        panelConnection.setBorder(BorderFactory.createTitledBorder("Connection"));
        panelConnection.add(buttonConnect);
        panelConnection.add(buttonBreak);
        this.getContentPane().add(panelConnection);

        panelFile.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        panelFile.setBorder(BorderFactory.createTitledBorder("Data File"));
        panelFile.add(buttonOpen);
        panelFile.add(buttonClose);
        this.getContentPane().add(panelFile);

        this.getContentPane().add(buttonExit);

        this.pack();
        this.setVisible(true);
    }

    // This funciton is called by the ReceivingThread just before completing.
    public void informEndOfDownloading() {
        buttonConnect.setEnabled(true);
        buttonBreak.setEnabled(false);
        buttonClose.setEnabled(true);

    }

    // This function permit to write a string in the JTextArea.
    public void writeInTextArea(String addString) {
        System.out.println("String add in the text area.");
        this.jTextArea.append(addString + "\n");
        this.jTextArea.setCaretPosition(this.jTextArea.getText().length());
    }

    private void closeApplication() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (Main.socketClient != null) {
            Main.socketClient.close(); // Closing of the socket and interruption of sending and receiving threads.
        }
        if (Main.fileClient != null) {
            try {
                Main.fileClient.close(); // Closing of the file.
            } catch (IOException ex) {
                Logger.getLogger(WindowControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // Closing of the file.
        WindowControl.this.dispose();
    }

    // This function is called when the buttonExit is clicked.
    public void exitApplication() {
        WindowControl.this.writeInTextArea("Exit system");
        // Opening of a JOptionPane to confirm Exit.
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit the application?", "Exit IAG0010Client", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.closeApplication();
        }
    }
    
    
    // Exception handle
    
    
    // Called when a IOException for the file is raised.
    public void informDirectoryNotExist(){
        JOptionPane.showMessageDialog(this, "The previous directory doesn't exist.\nYou should select an existing directory.", "Directory doesn't exist !", JOptionPane.ERROR_MESSAGE);
    }
    
    // Called when a SocketException is raised
    public void informConnectionAbort(){
        this.informEndOfDownloading();
        JOptionPane.showMessageDialog(this, "The connection has been interrupted.\nYou should reconnect the Client to the server.", "Connection abort", JOptionPane.ERROR_MESSAGE);
    }
    
    // Called when a IOException for the socket is raised.
    // This Exception shouldn't be raised
    public void informFailedSystem(){
        this.informEndOfDownloading();
        JOptionPane.showMessageDialog(this, "Due to an issue, IAG0010Client restart.", "IAG0010Client Failed", JOptionPane.ERROR_MESSAGE);
    }
}
