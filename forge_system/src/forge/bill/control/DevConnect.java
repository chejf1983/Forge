/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.control;

import forge.bill.mode.ModeGarage;
import forge.bill.platform.SystemConfig;
import java.util.logging.Level;
import nahon.comm.event.EventCenter;
import nahon.comm.faultsystem.LogCenter;
import nahon.comm.io.IOInfo;

/**
 *
 * @author chejf
 */
public class DevConnect {

    private final ModeGarage instance;
    private IOInfo lastioinfo;
    private byte lastbyte = -1;

    public enum ACTION {
        CONNECT,
        DISCONNECT
    }
    public EventCenter<ACTION> ActionCenter = new EventCenter();

    DevConnect(ModeGarage instance) {
        this.instance = instance;
    }

    public void SearchDevice() {
        //读取IO类型
        String iotype = SystemConfig.GetInstance().GetValue(SystemConfig.IOType);

        //读取长度
        int parlen = 0;
        byte addr = (byte) 0;
        try {
            parlen = Integer.valueOf(SystemConfig.GetInstance().GetValue(SystemConfig.ParLen));
            addr = Byte.valueOf(SystemConfig.GetInstance().GetValue(SystemConfig.DevAddr));
        } catch (Exception ex) {
            LogCenter.Instance().PrintLog(Level.WARNING, "参数个数不正确", ex);
        }

        //读取参数个数
        String[] pars = new String[parlen];
        for (int i = 0; i < parlen; i++) {
            pars[i] = SystemConfig.GetInstance().GetValue(SystemConfig.Par[i]);
        }

        //保存最后配置
        this.lastioinfo = new IOInfo(iotype, pars);

        //搜索设备
        this.SearchDevice(lastioinfo, addr);
    }

    public void SearchDevice(IOInfo ioinfo, byte devaddr) {
        try {
            this.instance.GetManager().SearchDevice(ioinfo, devaddr);

            //更新连接信息
            if (this.lastioinfo == null || !this.lastioinfo.equalto(ioinfo)) {
                //保存IO类型
                SystemConfig.GetInstance().SetValue(
                        SystemConfig.IOType, ioinfo.iotype);

                //保存IO类型
                SystemConfig.GetInstance().SetValue(
                        SystemConfig.ParLen, String.valueOf(ioinfo.par.length));

                //保存IO参数
                for (int i = 0; i < ioinfo.par.length; i++) {
                    SystemConfig.GetInstance().SetValue(
                            SystemConfig.Par[i], ioinfo.par[i]);
                }

                //保存配置文件
                SystemConfig.GetInstance().SaveToFile();
                this.lastioinfo = ioinfo;
            }

            //更新地址信息
            if (this.lastbyte != devaddr) {
                this.lastbyte = devaddr;
                //保存IO类型
                SystemConfig.GetInstance().SetValue(
                        SystemConfig.DevAddr, String.valueOf(this.lastbyte));

                //保存配置文件
                SystemConfig.GetInstance().SaveToFile();
            }

            this.ActionCenter.CreateEvent(this.instance.GetManager().IsFindDev() ? ACTION.CONNECT : ACTION.DISCONNECT);
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.WARNING, "搜索失败 ", ex);
        }
    }

    public void CloseDevice() {
        try {
            this.instance.GetManager().ClearDevice();
            this.ActionCenter.CreateEvent(ACTION.DISCONNECT);

            LogCenter.Instance().PrintLog(Level.WARNING, "断开连接 ");
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, ex);
        }
    }
}
