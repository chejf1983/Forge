/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.form.languange;

import forge.form.common.InitPaneHelper;
import java.io.File;

/**
 *
 * @author jiche
 */
public class LanguageForm extends javax.swing.JPanel {

    /**
     * Creates new form LanguangeForm
     */
    private java.awt.Frame parent = null;

    public LanguageForm(java.awt.Frame parent) {
        this.parent = parent;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        LanguageTable = new javax.swing.JTable();
        Save = new javax.swing.JButton();
        OpenLanguageFile = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        LanguageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(LanguageTable);

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        OpenLanguageFile.setText("Open");
        OpenLanguageFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenLanguageFileActionPerformed(evt);
            }
        });

        jButton1.setText("Delet");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OpenLanguageFile, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Save))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {OpenLanguageFile, Save});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Save)
                    .addComponent(OpenLanguageFile)
                    .addComponent(jButton1)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void OpenLanguageFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenLanguageFileActionPerformed
        String filepath = InitPaneHelper.GetFilePath(".xml");
        if (filepath != null && new File(filepath).exists()) {
            this.LanguageTable.setModel(new LanguageModel(filepath));
            this.LanguageTable.updateUI();
        }
    }//GEN-LAST:event_OpenLanguageFileActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        ((LanguageModel) this.LanguageTable.getModel()).Save();
    }//GEN-LAST:event_SaveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LanguageModel model = (LanguageModel) this.LanguageTable.getModel();
        if (model != null) {
            int index = this.LanguageTable.getSelectedRow();
            if (index > 0) {
                model.DeleteRows(index);
                model.Save();
                this.LanguageTable.updateUI();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable LanguageTable;
    private javax.swing.JButton OpenLanguageFile;
    private javax.swing.JButton Save;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
