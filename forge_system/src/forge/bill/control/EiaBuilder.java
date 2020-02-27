/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.control;

import forge.bill.platform.SystemConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import nahon.comm.faultsystem.LogCenter;

/**
 *
 * @author Administrator
 */
public class EiaBuilder {

    private static EiaBuilder instance;

    private EiaBuilder() {

    }

    public static EiaBuilder GetInstance() {
        if (instance == null) {
            instance = new EiaBuilder();
            try {
                instance.LoadInfo();
            } catch (IOException ex) {
                LogCenter.Instance().SendFaultReport(Level.SEVERE, "读取配置文件失败!");
            }
        }
        return instance;
    }

    private ArrayList<devinfo> infolist = new ArrayList();

    public ArrayList<devinfo> GetList() {
        return this.infolist;
    }

    private void LoadInfo() throws IOException {
        File configfile = new File(SystemConfig.ConfigPath + "/DevConfig.cfg");
        if (!configfile.exists()) {
            configfile.createNewFile();
            BufferedWriter reader = new BufferedWriter(new FileWriter(configfile));
            reader.write("NAH GCS300H 05030");
            reader.close();
            this.infolist.add(new devinfo("NAH", "GCS300H", "05030"));
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(configfile));
            String new_line;
            while ((new_line = reader.readLine()) != null) {
                String[] element = new_line.split(" ");
                if (element.length == 3) {
                    this.infolist.add(new devinfo(element[0], element[1], element[2]));
                }
            }
            reader.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="备注记录更新">
    //创建新的序列号
    public String BuildSerialID(String devname) {
        devinfo tmp = null;
        for (int i = 0; i < this.infolist.size(); i++) {
            if (this.infolist.get(i).devname.contentEquals(devname)) {
                tmp = this.infolist.get(i);
                break;
            }
        }
        
        if (tmp == null) {
            return null;
        }

        //创建序列号 公司编号（3）设备编号（5）年份（2）随机码（6）
        String sid = tmp.devcompany + tmp.devnum + new SimpleDateFormat("yyMM").format(new Date()) + String.format("%04d", 0);
        return sid;
    }

    // </editor-fold> 
    
    public class devinfo {

        public String devcompany;
        public String devname;
        public String devnum;

        public devinfo(String devcompany, String devname, String devnum) {
            this.devcompany = devcompany;
            this.devname = devname;
            this.devnum = devnum;
        }
    }
    /*
    private static ArrayList<devinfo> devinfolist
            = new ArrayList<devinfo>(Arrays.asList(
                    new devinfo(OSA_Turb, "OSA-Turb", "03001"),
                    new devinfo(OSA_TS, "OSA-TS", "03002"),
                    new devinfo(OSA__ChlA, "OSA-ChlA", "03003"),
                    new devinfo(OSA_Cyano_I, "OSA-Cyano I", "03004"),
                    new devinfo(OSA_Oil_I, "OSA-Oil I", "03005"),
                    new devinfo(OSA_FDO, "OSA-FDO", "03006"),
                    new devinfo(ISA_Ammo_I, "ISA-Ammo I", "03010"),
                    new devinfo(ISA_Ammo_II, "ISA-Ammo II", "03011"),
                    new devinfo(ESA_pH, "ESA-pH", "03020"),
                    new devinfo(ESA_DO, "ESA-DO", "03021"),
                    new devinfo(ESA_EC_I, "ESA-EC I", "03022"),
                    new devinfo(ESA_EC_II, "ESA-EC II", "03023"),
                    new devinfo(ESA_Chlori, "ESA-Chlori", "03024"),
                    new devinfo(DSR100, "DSR100", "03081"),
                    new devinfo(DSR200, "DSR200", "03091"),
                    new devinfo(GCS300S, "GCS300S", "05030"),
                    new devinfo(GCS300H, "GCS300H", "05031"),
                    new devinfo(GCS100_OEM, "GCS100_OEM", "05010")));*/
}
