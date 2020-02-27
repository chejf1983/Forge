/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.platform;

import forge.bill.data.SIOInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import nahon.comm.faultsystem.LogCenter;

/**
 *
 * @author jiche
 */
public class SystemConfig {

    // <editor-fold defaultstate="collapsed" desc="配置列表"> 
    public static final String ConfigFile = "SystemConfig.xml"; //文件名
    public static final String ConfigPath = "./DevConfig";      //文件夹

    public static PropertiesNode InternalFlag = new PropertiesNode("IsInternal", "false");
    public static PropertiesNode DevAddr = new PropertiesNode("DevAddr", "0");
    public static PropertiesNode IOType = new PropertiesNode("IOType", "USB");
    public static PropertiesNode ParLen = new PropertiesNode("ParLen", "0");
    public static PropertiesNode[] Par = new PropertiesNode[]{
        new PropertiesNode("Par0", ""),
        new PropertiesNode("Par1", ""),
        new PropertiesNode("Par2", ""),
        new PropertiesNode("Par3", ""),
        new PropertiesNode("Par4", ""),
        new PropertiesNode("Par5", ""),};
    public static PropertiesNode LogPath = new PropertiesNode("LogPath", "./log");
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="配置文件接口"> 
    private final Properties instance;

    private SystemConfig() {
        this.instance = new Properties();
    }
    private static SystemConfig config_instance;

    public static SystemConfig GetInstance() {
        if (config_instance == null) {
            config_instance = new SystemConfig();
        }
        return config_instance;
    }

    //初始化，配置文件
    public void LoadConfig() {
        //创建系统配置文件目录
        File f = new File(ConfigPath);

        // 创建文件夹
        if (!f.exists()) {
            f.mkdirs();
        }

        try {
            //初始化配置文件(文件夹+文件名）
            File file = new File(ConfigPath + "/" + ConfigFile);
            if (!file.exists()) {
                file.createNewFile();
                new Properties().storeToXML(new FileOutputStream(file), "");
//            file = new File(ConfigPath + "/" + ConfigFile);
            }
            //加载配置内容
            this.instance.loadFromXML(new FileInputStream(file));
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, "配置文件读取失败:" , ex);
        }
    }

    //保存文件
    public void SaveToFile() throws Exception {
        this.SaveToFile(ConfigPath, ConfigFile);
    }

    //保存到文件
    public void SaveToFile(String path, String filename) throws Exception {
        //保存配置文件(文件夹+文件名）
        File file = new File(path + "/" + filename);
        this.instance.storeToXML(new FileOutputStream(file), "");
    }

    public String GetValue(PropertiesNode node) {
        if (node == null) {
            return "";
        }
        
        return this.instance.getProperty(node.Key, node.DefaultValue);
    }
    
    public void SetValue(PropertiesNode node, String value){
        this.instance.setProperty(node.Key, value);
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="配置变量">
    //从配置文件中读取默认io信息
    public SIOInfo GetDefaultIO() {
        //如果没有IO类型，表示没有保存任何信息，返回空
        String iotype = this.instance.getProperty("netType", "");
        if (iotype.equals("")) {
            return null;
        }

        //读取IO参数
        ArrayList<String> pars = new ArrayList();
        for (int i = 0; i < 10; i++) {
            String tmp = this.instance.getProperty("par" + i, "");
            if (tmp.equals("")) {
                break;
            } else {
                pars.add(tmp);
            }
        }

        //返回IO信息
        //SIOInfo par = new SIOInfo(iotype, pars.toArray(new String[0]));
        return null;
    }

    //保存默认io信息到配置文件
    public void SaveDefaultIO(SIOInfo par) {
        if (par == null) {
            //保存IO类型
            instance.setProperty("netType", "");
            return;
        }

        //保存IO类型
        instance.setProperty("netType", par.iotype.toString());
        //保存IO参数
        for (int i = 0; i < par.pars.length; i++) {
            instance.setProperty("par" + i, par.pars[i].toString());
        }
    }

    //设备地址
    public byte GetAddr() {
        return Byte.valueOf(this.instance.getProperty("devaddr", "0"));
    }

    public void SaveAddr(byte addr) {
        //保存IO类型
        instance.setProperty("devaddr", String.valueOf(addr));
    }

    //EIA备注记录是否用本地DB服务器
    public boolean IsLocalEiaDB() {
        return Boolean.valueOf(this.instance.getProperty("LOCALENABLE", "true"));
    }

    public void SetLocalEiaDB(boolean value) {
        this.instance.setProperty("LOCALENABLE", String.valueOf(value));
    }

    public String GetExcelPath() {
        return this.instance.getProperty("DIRPATH", "./information");
    }

    public void SetExcelPath(String dirpath) {
        this.instance.setProperty("DIRPATH", dirpath);
    }

    public String[] GetServerDBPar() {
        String[] par = new String[3];
        //par[0] = ForgeSystem.GetInstance().systemConfig.instance.getProperty("SERVERDB", "jdbc:mysql://192.168.1.110/nahon?characterEncoding=utf8");
        //par[1] = ForgeSystem.GetInstance().systemConfig.instance.getProperty("SERVERUSER", "nahon");
        //par[2] = ForgeSystem.GetInstance().systemConfig.instance.getProperty("SERVERPASS", "nahon");
        return par;
    }

    public void SetServeDBPar(String url, String user, String password) {
        this.instance.setProperty("SERVERDB", url);
        this.instance.setProperty("SERVERUSER", user);
        this.instance.setProperty("SERVERPASS", password);
    }
    
    public String GetVersion(){
        return this.instance.getProperty("VER", "1.0.0.0");
    }
    // </editor-fold> 
}
