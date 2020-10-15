/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.form.seteia;

import forge.bill.control.DevConfigBean;
import forge.bill.control.DevConnect.ACTION;
import forge.bill.data.SSEquipmentInfo;
import forge.bill.control.EiaBuilder;
import forge.form.common.EiaTableModel;
import forge.bill.platform.ForgeSystem;
import forge.bill.platform.SystemConfig;
import forge.form.common.InitPaneHelper;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import nahon.comm.event.Event;
import nahon.comm.event.EventListener;
import nahon.comm.faultsystem.LogCenter;

/**
 *
 * @author jiche
 */
public class EIASetupPane extends javax.swing.JPanel {

    /**
     * Creates new form UpdateForm
     */
    private java.awt.Frame log = null;

    private DevConfigBean configer = ForgeSystem.GetInstance().GetControlCenter().GetConfigure();

    public EIASetupPane(java.awt.Frame parent) {
        this.log = parent;

        initComponents();

        //if(SystemConfig.)
        //内部版本才可以修改设备序列号
        if (Boolean.valueOf(SystemConfig.GetInstance().GetValue(SystemConfig.InternalFlag))) {
            initInternalForm();
        } else {
            initCustomeForm();
        }

        this.InitEvent();
    }

    private JComboBox devnamelist;
    private String lastdevname = "";

    //初始化内部版本界面
    private void initInternalForm() {
        //设置默认设备名称下拉列表
        devnamelist = new JComboBox();
        for (EiaBuilder.devinfo tmpinfo : EiaBuilder.GetInstance().GetList()) {
            devnamelist.addItem(tmpinfo.devname);
        }

        //设置设备名称可以修改
        devnamelist.setEditable(true);
        devnamelist.addItemListener((ItemEvent e) -> {
            //如果设备名称改变了
            if (!devnamelist.getEditor().getItem().toString().contentEquals(lastdevname)) {
                //重新选择设备名称后,更新设备表格中设备名称
                SSEquipmentInfo eia = ((EiaTableModel) devEiaTable.getModel()).GetEIAInfo();
                //更新设备名称
                eia.DeviceName = devnamelist.getSelectedItem().toString();
                lastdevname = eia.DeviceName;
                //更新创建日期
                eia.BuildDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                //创建序列号
                String newerianum = EiaBuilder.GetInstance().BuildSerialID(lastdevname);
                if (newerianum != null) {
                    eia.BuildSerialNum = newerianum;
                }
                //更新界面
                UpdateEia(eia);
            }
        });

        //更新eia信息框
        this.devEiaTable = new JTable() {
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (row == 0 && column == 1) {
                    return new DefaultCellEditor(devnamelist);
                } else {
                    return super.getCellEditor(row, column);
                }

            }
        };
//        this.devEiaTable.setTableHeader(null);
//        Table_data.getTableHeader().setResizingAllowed(false);   // 不允许拉伸
        this.devEiaTable.getTableHeader().setReorderingAllowed(false); //不允许拖拽
        this.eiaTablePane.setViewportView(devEiaTable);

        this.createPopupMenu();
        this.devEiaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    //通过点击位置找到点击为表格中的行
                    int focusedRowIndex = devEiaTable.rowAtPoint(evt.getPoint());
                    if (focusedRowIndex < 3) {
                        index = focusedRowIndex;
                        //将表格所选项设为当前右键点击的行
                        devEiaTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                        //弹出菜单

                        m_popupMenu.show(devEiaTable, evt.getX(), evt.getY());
                    }
                }

            }
        });
    }

    //初始化外部版本
    private void initCustomeForm() {
        this.devEiaTable.setTableHeader(null);
        this.CheckBox_AddNum.setVisible(false);
    }

    private void UpdateEia(SSEquipmentInfo model) {
        this.devEiaTable.setModel(new EiaTableModel(model));
        JTableHeader header = devEiaTable.getTableHeader();
        TableColumn column = devEiaTable.getColumnModel().getColumn(0);
        header.setResizingColumn(column); // 名称
        column.setWidth(90);
    }

    //更新EIA信息
    private void InitEvent() {
        ForgeSystem.GetInstance().GetControlCenter().GetConnector().ActionCenter.RegeditListener(new EventListener<ACTION>() {
            @Override
            public void recevieEvent(Event<ACTION> event) {
                SwingUtilities.invokeLater(() -> {
                    if (event.GetEvent() == ACTION.CONNECT) {
                        SSEquipmentInfo eia = configer.GetDevInfo();
                        UpdateEia(eia);
                        lastdevname = eia.DeviceName;
                    } else {
                        UpdateEia(null);
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

        jScrollPane3 = new javax.swing.JScrollPane();
        InfoList = new javax.swing.JList();
        Button_OpenDir = new javax.swing.JButton();
        Button_Set = new javax.swing.JButton();
        TextField_company = new javax.swing.JTextField();
        TextField_DevAddr = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        CheckBox_AddNum = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        DevicePanel = new javax.swing.JPanel();
        eiaTablePane = new javax.swing.JScrollPane();
        devEiaTable = new javax.swing.JTable();
        Button_SetDir = new javax.swing.JButton();
        Button_read = new javax.swing.JButton();

        InfoList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(InfoList);

        Button_OpenDir.setText("打开目录");
        Button_OpenDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_OpenDirActionPerformed(evt);
            }
        });

        Button_Set.setText("设置EIA");
        Button_Set.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_SetActionPerformed(evt);
            }
        });

        TextField_DevAddr.setText("1");

        jLabel3.setText("备注：");

        CheckBox_AddNum.setText("序列号自增");

        jButton1.setText("设置地址");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        DevicePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("设备信息"));

        devEiaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "EIA信息", "值"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        eiaTablePane.setViewportView(devEiaTable);

        javax.swing.GroupLayout DevicePanelLayout = new javax.swing.GroupLayout(DevicePanel);
        DevicePanel.setLayout(DevicePanelLayout);
        DevicePanelLayout.setHorizontalGroup(
            DevicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(eiaTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        DevicePanelLayout.setVerticalGroup(
            DevicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(eiaTablePane, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
        );

        Button_SetDir.setText("设置目录");
        Button_SetDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_SetDirActionPerformed(evt);
            }
        });

        Button_read.setText("读取EIA");
        Button_read.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_readActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DevicePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Button_OpenDir)
                            .addComponent(Button_SetDir, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(TextField_DevAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(CheckBox_AddNum)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextField_company)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Button_read, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Button_Set, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(DevicePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(TextField_company, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_read, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(Button_SetDir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_Set, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CheckBox_AddNum))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextField_DevAddr)
                    .addComponent(Button_OpenDir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9))
        );
    }// </editor-fold>//GEN-END:initComponents

    JPopupMenu m_popupMenu;
    private int index = 0;

    private void createPopupMenu() {
        m_popupMenu = new JPopupMenu();

        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("粘贴");
        delMenItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            //该操作需要做的事
            Clipboard clipboard = getToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(this);
            DataFlavor flavor = DataFlavor.stringFlavor;
            if (contents.isDataFlavorSupported(flavor)) {
                try {
                    String str = (String) contents.getTransferData(flavor);
                    SSEquipmentInfo tmpeia = ((EiaTableModel) devEiaTable.getModel()).GetEIAInfo();
                    switch (index) {
                        case 0:
                            tmpeia.DeviceName = str;
                            break;
                        case 1:
                            tmpeia.BuildSerialNum = str;
                            break;
                        case 2:
                            tmpeia.BuildDate = str;
                            break;
                    }
                    UpdateEia(tmpeia);
                } catch (UnsupportedFlavorException | IOException ex) {
                    Logger.getLogger(EIASetupPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        m_popupMenu.add(delMenItem);
    }

    //下发eia，并且记录出场信息
    private void Button_SetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_SetActionPerformed
        //获取新的eia信息
        if (this.devEiaTable.getCellEditor() != null) {
            this.devEiaTable.getCellEditor().stopCellEditing();
        }
        //连接状态下才可以下发设备信息和设备地址
        SSEquipmentInfo eia = ((EiaTableModel) this.devEiaTable.getModel()).GetEIAInfo();
        if (eia.BuildSerialNum.length() < 16) {
            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "序列号长度不足16,是否需要下发?", "", JOptionPane.YES_NO_OPTION)) {
                return;
            }
        }

        if (configer.SetDevEia(eia, TextField_company.getText())) {
            //自动增加数字
            if (CheckBox_AddNum.isSelected()) {
                try {
                    SSEquipmentInfo tmpeia = ((EiaTableModel) devEiaTable.getModel()).GetEIAInfo();
                    String slast3 = tmpeia.BuildSerialNum.substring(tmpeia.BuildSerialNum.length() - 3);
                    int ilast3 = Integer.valueOf(slast3);
                    ilast3 = (ilast3 + 1) % 1000;
                    tmpeia.BuildSerialNum = tmpeia.BuildSerialNum.substring(0, tmpeia.BuildSerialNum.length() - 3)
                            + String.format("%03d", ilast3);
                    UpdateEia(tmpeia);
                } catch (Exception ex) {
                    //this.log.PrintLog("序列号无法自增! " + ex.getMessage());
                }
            }
            //this.log.PrintLog("设置设备信息成功!");

        }
    }//GEN-LAST:event_Button_SetActionPerformed

    private void Button_OpenDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_OpenDirActionPerformed
        try {
            File path = new File(this.configer.GetRecordPath());
            // TODO add your handling code here:
            Runtime.getRuntime().exec("explorer.exe " + path.getCanonicalPath());
        } catch (IOException ex) {
            Logger.getLogger(EIASetupPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Button_OpenDirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        byte addr = 0;
        try {
            addr = Byte.valueOf(this.TextField_DevAddr.getText());
        } catch (NumberFormatException ex) {
            LogCenter.Instance().SendFaultReport(Level.WARNING, "输入地址异常!");
            return;
        }

        configer.SetDevAddr(addr);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Button_SetDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_SetDirActionPerformed
        String dirpath = InitPaneHelper.GetDirPath();
        if (dirpath != null) {
            configer.SetRecordPath(dirpath);
        }
    }//GEN-LAST:event_Button_SetDirActionPerformed

    public void ReadEIA() {
        Button_readActionPerformed(null);
    }

    private void Button_readActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_readActionPerformed
        SSEquipmentInfo eia = configer.GetDevInfo();
        UpdateEia(eia);
        lastdevname = eia.DeviceName;
    }//GEN-LAST:event_Button_readActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_OpenDir;
    private javax.swing.JButton Button_Set;
    private javax.swing.JButton Button_SetDir;
    private javax.swing.JButton Button_read;
    private javax.swing.JCheckBox CheckBox_AddNum;
    private javax.swing.JPanel DevicePanel;
    private javax.swing.JList InfoList;
    private javax.swing.JTextField TextField_DevAddr;
    private javax.swing.JTextField TextField_company;
    private javax.swing.JTable devEiaTable;
    private javax.swing.JScrollPane eiaTablePane;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
