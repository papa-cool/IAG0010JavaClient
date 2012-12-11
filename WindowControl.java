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
public class WindowControl extends JFrame{
    
    private JTextArea jTextArea = new JTextArea("Welcome in IAG0010JavaClient\n", 15,30);
    private JScrollPane jScrollPane = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JButton buttonConnect = new JButton("Connect");
    private JButton buttonDisconnect = new JButton("Disconnect");
    private JPanel panelConnection = new JPanel();
    private JButton buttonOpen = new JButton("Open");
    private JButton buttonClose = new JButton("Close");
    private JPanel panelFile = new JPanel();
    private JButton buttonExit = new JButton("Exit");
    
    
    public WindowControl(){
        // Configuration fenêtre
        this.setTitle("IAG0010Client");
        this.setSize(500,750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE );
        this.addWindowListener( new WindowAdapter(){
            public void windowClosing(WindowEvent e){ exitApplication(); }
        });
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        
        // Button Listener
        
        
        buttonConnect.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println("Connect");
                Main.socketClient = new SocketClient();
                if (Main.socketClient.isConnected()){
                    buttonConnect.setEnabled(false);
                    buttonDisconnect.setEnabled(true);
                    Main.receivingThread = new ReceivingThread();
                    Main.sendingThread = new SendingThread();
                }
            }
        });
        buttonDisconnect.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println("Diconnect");
                WindowControl.this.writeInTextArea("Disconnection");
                Main.socketClient.close();
                if (Main.socketClient.isConnected()){
                    WindowControl.this.writeInTextArea("Client is disconnected from the server.");
                    buttonDisconnect.setEnabled(false);
                    buttonConnect.setEnabled(true);
                }
            }
        });
        buttonOpen.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println("Open");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose the target directory to download the file !");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showOpenDialog(WindowControl.this);
                System.out.println("The downloaded file will be save in the following adress :\n"+fileChooser.getSelectedFile()+File.separator+"TTU.doc");
                WindowControl.this.writeInTextArea("The downloaded file will be save in the following adress :\n"+fileChooser.getSelectedFile()+File.separator+"TTU.doc");
                Main.fileClient = new FileClient(fileChooser.getSelectedFile()+File.separator+"TTU.doc");
                buttonConnect.setEnabled(true);
                buttonOpen.setEnabled(false);
            }
        });
        buttonClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println("Close");
                Main.fileClient.closeBufferedWriter();
                buttonClose.setEnabled(false);
            }
        });
        buttonExit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                exitApplication();
            }
        });
        
        buttonConnect.setEnabled(false);
        buttonDisconnect.setEnabled(false);
        buttonClose.setEnabled(false);
        
        
        // Template
        
        
        jTextArea.setPreferredSize(new Dimension(500, 500));
        jScrollPane.setViewportView(jTextArea);
        jTextArea.setLineWrap(true);
        this.getContentPane().add(jScrollPane);
        
        panelConnection.setLayout(new FlowLayout(FlowLayout.CENTER,50,5));
        panelConnection.setBorder(BorderFactory.createTitledBorder("Connection"));
        panelConnection.add(buttonConnect);
        panelConnection.add(buttonDisconnect);
        this.getContentPane().add(panelConnection);
        
        panelFile.setLayout(new FlowLayout(FlowLayout.CENTER,50,5));
        panelFile.setBorder(BorderFactory.createTitledBorder("Data File"));
        panelFile.add(buttonOpen);
        panelFile.add(buttonClose);
        this.getContentPane().add(panelFile);
        
        this.getContentPane().add(buttonExit);
        
        this.pack();
        this.setVisible(true);
    }
    
    public void informEndOfDownloading(){
        buttonConnect.setEnabled(true);
        buttonDisconnect.setEnabled(false);
        buttonClose.setEnabled(true);
                
    }
    
    public void writeInTextArea(String addString) {
        System.out.println("String add in the text area.");
        this.jTextArea.append(addString+"\n");
        this.jTextArea.setCaretPosition(this.jTextArea.getText().length());
    }
    
    public void exitApplication() {
        System.out.println("Exit");
            WindowControl.this.writeInTextArea("Exit system");
            int result = JOptionPane.showConfirmDialog(WindowControl.this,"Are you sure you want to exit the application?","Exit IAG0010Client",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
               WindowControl.this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               if (Main.socketClient != null)
                    Main.socketClient.close();
               WindowControl.this.dispose();
        }
    }
}

