/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IAG0010JavaClient;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Emmanuel
 */
public class MainPanel extends javax.swing.JPanel {
    
    private JButton m_OKButton = new JButton("OK");
    private JButton m_CancelButton = new JButton("Cancel");
    private JLabel m_NumberLabel = new JLabel("Number:");
    private JLabel m_MonthLabel = new JLabel("Month:");
    private JLabel m_YearLabel = new JLabel("Year:");
    private JScrollPane m_ListScrollPane;
    private JList m_List;
    private JTextField m_CardNumber = new JTextField();
    private JTextField m_MonthValue = new JTextField();
    private JTextField m_YearValue = new JTextField();
    private JPanel m_CardPanel = new JPanel();
    private JPanel m_ExpiresPanel = new JPanel();
    private JRadioButton m_MasterCard = new JRadioButton("Master Card");
    private JRadioButton m_Visa = new JRadioButton("Visa");
    private JRadioButton m_AmericanExpress = new JRadioButton("American Express");
     
    public MainPanel() {
        String Tours[] = {"23.08 Prantsusmaa, Nizza 670 EUR", "23.08 Horvaatia, Istra poolsaar 855 EUR",
            "24.08 Kreeka, Kos 530 EUR", 
            "25.08 Hispaania, Mallorca, Santa Ponsa 495 EUR", "25.08 Kreeka, Rhodos 585 EUR",
            "25.08 Kreeka, Lefkas 715 EUR" 
        };
        m_List = new JList(Tours);
        m_List.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting())
                    System.out.println(m_List.getSelectedIndex());
            }
        });
        m_List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_ListScrollPane = new JScrollPane(m_List);
        
        m_CardPanel.setBorder(BorderFactory.createTitledBorder("Card"));
        m_CardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        m_CardPanel.add(m_MasterCard);
        m_CardPanel.add(m_Visa);
        m_CardPanel.add(m_AmericanExpress);
        ButtonGroup cards = new ButtonGroup();
        cards.add(m_MasterCard);
        cards.add(m_Visa);
        cards.add(m_AmericanExpress);
        
        JPanel NumberPanel = new JPanel();
        NumberPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        NumberPanel.add(m_NumberLabel);
        m_CardNumber.setPreferredSize(new Dimension(200, 20));
        NumberPanel.add(m_CardNumber);
        
        JPanel ExpireLeft = new JPanel();
        JPanel ExpireRight = new JPanel();      
        ExpireLeft.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        ExpireLeft.add(m_MonthLabel);
        m_MonthValue.setPreferredSize(new Dimension(50, 20));
        ExpireLeft.add(m_MonthValue);
        ExpireRight.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        ExpireRight.add(m_YearLabel);
        m_YearValue.setPreferredSize(new Dimension(50, 20));
        ExpireRight.add(m_YearValue);
        m_ExpiresPanel.setBorder(BorderFactory.createTitledBorder("Expires"));
        m_ExpiresPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 5));
        m_ExpiresPanel.add(ExpireLeft);
        m_ExpiresPanel.add(ExpireRight);
        
        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 5));
        m_OKButton.setPreferredSize(new Dimension(80, 20));
        ButtonPanel.add(m_OKButton);
        m_CancelButton.setPreferredSize(new Dimension(80, 20));
        ButtonPanel.add(m_CancelButton);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(m_ListScrollPane);
        add(m_CardPanel);
        add(NumberPanel);
        add(m_ExpiresPanel);
        add(ButtonPanel);
    }

     // Next code is created by NetBeans, here we do not use it
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
