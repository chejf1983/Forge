/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.platform;

import forge.bill.control.ControlCenter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import forge.bill.mode.ModeGarage;
import nahon.comm.faultsystem.LogCenter;

/**
 *
 * @author jiche
 */
public class ForgeSystem {

    public ExecutorService systemthreadpool;     //进程池  
    private ModeGarage mode_instance;

    private static ForgeSystem Instance;

    public static ForgeSystem GetInstance() {
        if (Instance == null) {
            Instance = new ForgeSystem();
        }
        return Instance;
    }

    private ForgeSystem() {
    }

    public void initSystem() throws Exception {
        //创建系统进程池（100）
        this.systemthreadpool = Executors.newFixedThreadPool(20);

        //初始化配置文件（200）
        SystemConfig.GetInstance().LoadConfig();

        //设备控制器（500）
        this.mode_instance = new ModeGarage();
        
        LogCenter.Instance().SetLogPath("./log");
    }

    //控制中心
    private ControlCenter control_instance;

    public ControlCenter GetControlCenter() {
        if (this.control_instance == null) {
            this.control_instance = new ControlCenter(this.mode_instance);
        }

        return this.control_instance;
    }
}
