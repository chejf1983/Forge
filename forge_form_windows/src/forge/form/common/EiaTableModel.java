/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.form.common;

import forge.bill.data.SSEquipmentInfo;
import forge.bill.platform.SystemConfig;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jiche
 */
public class EiaTableModel extends AbstractTableModel {

    private String[] ColumNames = {"EIA信息", "值"};
    private String[] names = {"设备名称：", "生产序列:", "生产日期:", "硬件版本:", "软件版本:"};
    private SSEquipmentInfo eiainfo;

//    public final static int DeviceName_Length = 0x10;
//    public final static int Hardversion_Length = 0x01;
//    public final static int SoftwareVersion_Length = 0x04;
//    public final static int BuildSerialNum_Length = 0x20;
//    public final static int BuildDate_Length = 0x10;
//    public final static int DevCatalog_Length = 0x02;
//    public final static int DevType_Length = 0x02;

    public EiaTableModel(SSEquipmentInfo eiainfo) {
        if (eiainfo == null) {
            this.eiainfo = new SSEquipmentInfo();
        } else {
            this.eiainfo = eiainfo;
        }
    }

    public SSEquipmentInfo GetEIAInfo() {
        return this.eiainfo;
    }

    @Override
    public int getRowCount() {
        return this.names.length;
    }

    @Override
    public int getColumnCount() {
        return this.ColumNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.ColumNames[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //禁止编辑标志判断,同时标题不能修改
        //设备名称可以修改
        if (rowIndex == 0 || rowIndex == 2) {
            return true;
        }

        if (rowIndex == 1) {
            //内部版本才可以修改设备序列号
            if (Boolean.valueOf(SystemConfig.GetInstance().GetValue(SystemConfig.InternalFlag))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return names[rowIndex];
        } else {
            switch (rowIndex) {
                case 0:
                    return eiainfo.DeviceName;
                case 1:
                    return eiainfo.BuildSerialNum;
                case 2:
                    return eiainfo.BuildDate;
                case 3:
                    return eiainfo.Hardversion;
                case 4:
                    return eiainfo.SoftwareVersion;
            }
            defualt:
            return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
        } else {
            try {
                int length = 0;
                switch (rowIndex) {
                    case 0:
                        eiainfo.DeviceName = aValue.toString();
                        break;
                    case 1:
                        eiainfo.BuildSerialNum = aValue.toString();
                        break;
                    case 2:
                        eiainfo.BuildDate = aValue.toString().trim();
                        break;
                }
            } catch (Exception ex) {
            }
        }
    }
}
