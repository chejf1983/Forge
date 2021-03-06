/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.form.main.customer;

import forge.bill.control.DevConnect.ACTION;
import forge.bill.platform.ForgeSystem;
import forge.form.common.CommInfoDialog;
import forge.form.common.MainUICenter;
import forge.form.seteia.EIASetupPane;
import forge.form.update.UpdateForm;
import java.awt.CardLayout;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import nahon.comm.event.Event;
import nahon.comm.event.EventListener;

/**
 *
 * @author jiche
 */
public class MainCustomer extends javax.swing.JFrame {

    /**
     * Creates new form CustomTool
     */
    public MainCustomer() {
//        try {
        initComponents();
        //居中显示
        setLocationRelativeTo(null);
        try {
            this.InitPane();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "系统初始化失败！ \r\n" + ex.toString());
            Logger.getGlobal().log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    private CardLayout workScreenLayout = new CardLayout();

    private void InitPane() throws Exception {

        this.setResizable(false);
        MainUICenter.GetInstance().Init(false);

        this.MainArea.setLayout(workScreenLayout);

        this.MainArea.add(UpdateForm.class.getName(), new UpdateForm(this));
        this.MainArea.add(CustomerAbout.class.getName(), new CustomerAbout());
        this.MainArea.add(EIASetupPane.class.getName(), new EIASetupPane(this));

        workScreenLayout.show(this.MainArea, CustomerAbout.class.getName());

        ForgeSystem.GetInstance().GetControlCenter().GetConnector().ActionCenter.RegeditListener(new EventListener<ACTION>() {
            @Override
            public void recevieEvent(Event<ACTION> event) {
                SwingUtilities.invokeLater(() -> {
                    if (ACTION.CONNECT == event.GetEvent()) {
//                        ConnectStateIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forge/form/resource/connect_1.png")));
                        workScreenLayout.show(MainArea, EIASetupPane.class.getName());
                    }

                    if (ACTION.DISCONNECT == event.GetEvent()) {
//                        ConnectStateIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forge/form/resource/disconnect_1.png")));
                        workScreenLayout.show(MainArea, CustomerAbout.class.getName());
                    }
                });
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainArea = new javax.swing.JPanel();
        logPane1 = new forge.form.common.LogPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        ExitMenuItem = new javax.swing.JMenuItem();
        MenuItem_Open = new javax.swing.JMenuItem();
        MenuItem_Close = new javax.swing.JMenuItem();
        Menu_eia = new javax.swing.JMenu();
        Menu_update = new javax.swing.JMenu();
        HelpMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Forge");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/forge/form/resource/FormIcon.png")));
        setLocationByPlatform(true);
        setName("Forge"); // NOI18N
        setResizable(false);

        MainArea.setPreferredSize(new java.awt.Dimension(320, 473));

        javax.swing.GroupLayout MainAreaLayout = new javax.swing.GroupLayout(MainArea);
        MainArea.setLayout(MainAreaLayout);
        MainAreaLayout.setHorizontalGroup(
            MainAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        MainAreaLayout.setVerticalGroup(
            MainAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        FileMenu.setText("设备");

        ExitMenuItem.setText("退出");
        ExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(ExitMenuItem);

        MenuItem_Open.setText("连接设备");
        MenuItem_Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_OpenActionPerformed(evt);
            }
        });
        FileMenu.add(MenuItem_Open);

        MenuItem_Close.setText("断开设备");
        MenuItem_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_CloseActionPerformed(evt);
            }
        });
        FileMenu.add(MenuItem_Close);

        jMenuBar1.add(FileMenu);

        Menu_eia.setText("设备信息");
        Menu_eia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Menu_eiaMouseClicked(evt);
            }
        });
        jMenuBar1.add(Menu_eia);

        Menu_update.setText("升级");
        Menu_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Menu_updateMouseClicked(evt);
            }
        });
        jMenuBar1.add(Menu_update);

        HelpMenu.setText("帮助");
        HelpMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HelpMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(HelpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addComponent(MainArea, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(MainArea, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_ExitMenuItemActionPerformed

    private void MenuItem_OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_OpenActionPerformed
        CommInfoDialog dilog = new CommInfoDialog(this, true);
        dilog.setVisible(true);
        if (dilog.GetIOInfo() != null) {
            ForgeSystem.GetInstance().GetControlCenter().GetConnector().SearchDevice(dilog.GetIOInfo(), dilog.GetAddr());
            String title = "Forge:";
            for (String par : dilog.GetIOInfo().par) {
                title += " " + par;
            }

            this.setTitle(title);
        }
    }//GEN-LAST:event_MenuItem_OpenActionPerformed

    private void MenuItem_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_CloseActionPerformed
        ForgeSystem.GetInstance().GetControlCenter().GetConnector().CloseDevice();
    }//GEN-LAST:event_MenuItem_CloseActionPerformed

    private void HelpMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HelpMenuMouseClicked
        workScreenLayout.show(this.MainArea, CustomerAbout.class.getName());
    }//GEN-LAST:event_HelpMenuMouseClicked

    private void Menu_eiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Menu_eiaMouseClicked
        workScreenLayout.show(this.MainArea, EIASetupPane.class.getName());
    }//GEN-LAST:event_Menu_eiaMouseClicked

    private void Menu_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Menu_updateMouseClicked
        // TODO add your handling code here:
        workScreenLayout.show(this.MainArea, UpdateForm.class.getName());
    }//GEN-LAST:event_Menu_updateMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            javax.swing.UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainCustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ExitMenuItem;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JPanel MainArea;
    private javax.swing.JMenuItem MenuItem_Close;
    private javax.swing.JMenuItem MenuItem_Open;
    private javax.swing.JMenu Menu_eia;
    private javax.swing.JMenu Menu_update;
    private javax.swing.JMenuBar jMenuBar1;
    private forge.form.common.LogPane logPane1;
    // End of variables declaration//GEN-END:variables
}
