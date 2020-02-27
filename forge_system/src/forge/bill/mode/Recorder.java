/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.mode;

import forge.bill.data.SSEquipmentInfo;
import forge.bill.platform.SystemConfig;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author chejf
 */
public class Recorder {

    // <editor-fold defaultstate="collapsed" desc="保存目录"> 
    public Recorder() {
        this.record_path = SystemConfig.GetInstance().GetValue(SystemConfig.LogPath);
        this.CheckPath(record_path);
    }
    private String record_path = "./";

    public String GetRecordPath() {
        return this.record_path;
    }

    public void SetRecordPath(String path) {
        this.record_path = path;
        this.CheckPath(record_path);
    }

    private void CheckPath(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            return;
        } else {
            dir.mkdir();
        }
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="添加记录"> 
    private WritableWorkbook workbook;
    private WritableSheet sheet;

    private String file_name;

    private String[] column_name = {"设备名称", "序列号", "日期", "备注1"};
    private int[] column_len = {0, 0, 0, 0};

    private int tableStart_column = 1;//新table的column启始位置
    private int tableStart_row = 1;   //新table的row启始位置

    public void AddRecord(SSEquipmentInfo eia, String info) throws Exception {
        if (tableStart_row == 1) {            
            if (!record_path.endsWith("/"));
            {
                this.record_path += "/";
            }
            this.file_name = record_path + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".xls";
            //创建excel
            this.workbook = Workbook.createWorkbook(new File(file_name));
            //创建新的sheet
            this.sheet = workbook.createSheet("设备记录", 0);
            this.WriterColumnName(column_name);
        } else {            
            Workbook book = Workbook.getWorkbook(new File(file_name));
            this.workbook = Workbook.createWorkbook(new File(file_name), book);
            this.sheet = this.workbook.getSheet(0);
        }

        this.AddRow(eia.DeviceName, eia.BuildSerialNum, eia.BuildDate, info);
        workbook.write();
        workbook.close();

        //刷新文件
        if (this.tableStart_row > 100) {
            this.tableStart_row = 1;
        }
    }

    private void WriterColumnName(String... pars) throws Exception {
        WritableCellFormat wcf = this.createCellFormat();

        for (int i = 0; i < pars.length; i++) {
            //写入column名称
            jxl.write.Label column_name = new jxl.write.Label(tableStart_column + i,
                    tableStart_row, pars[i], wcf);
            this.sheet.addCell(column_name);
        }
        this.tableStart_row++;
    }

    private void AddRow(Object... pars) throws Exception {
        WritableCellFormat wcf = this.createCellFormat();
        for (int column = 0; column < pars.length; column++) {
            //填入内容
            Object value = pars[column];

            if (value == null) {
                jxl.write.Label data = new jxl.write.Label(tableStart_column + column,
                        tableStart_row, "null", wcf);
                this.sheet.addCell(data);
            } else if (java.lang.Number.class.isAssignableFrom(value.getClass())) {
                jxl.write.Number data = new jxl.write.Number(tableStart_column + column,
                        tableStart_row, Double.valueOf(value.toString()), wcf);
                this.sheet.addCell(data);
            } else {
                jxl.write.Label data = new jxl.write.Label(tableStart_column + column,
                        tableStart_row, value.toString(), wcf);
                this.sheet.addCell(data);
            }

            if (this.column_len[column] < value.toString().length()) {
                this.column_len[column] = value.toString().length();
                sheet.setColumnView(tableStart_column + column, this.column_len[column] + 5);
            }
        }

        tableStart_row++;
    }

    //创建cell 格式
    private WritableCellFormat createCellFormat() throws Exception {
        WritableCellFormat wcf = new WritableCellFormat();
        wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        wcf.setAlignment(jxl.format.Alignment.CENTRE);

        return wcf;
    }
    // </editor-fold> 
}
