package forge.bill.control;

import forge.bill.data.SSEquipmentInfo;
import forge.bill.devdrv.Device;
import forge.bill.mode.ModeGarage;
import java.util.logging.Level;
import nahon.comm.faultsystem.LogCenter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chejf
 */
public class DevConfigBean {

    private final ModeGarage instance;

    DevConfigBean(ModeGarage instance) {
        this.instance = instance;
    }

    //当前设备信息
    public SSEquipmentInfo GetDevInfo() {
        try {
            SSEquipmentInfo eia = this.instance.GetManager().ApplyDevice().GetEquipmentInfo();
            LogCenter.Instance().PrintLog(Level.INFO, "找到设备" + eia.DeviceName);
            return eia;
        } catch (Exception ex) {
            LogCenter.Instance().PrintLog(Level.INFO, "没有找到设备");
            return new SSEquipmentInfo();
        } finally {
            this.instance.GetManager().FreeDevice();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="记录路径"> 
    public String GetRecordPath() {
        return this.instance.GetRecorder().GetRecordPath();
    }

    public void SetRecordPath(String path) {
        this.instance.GetRecorder().SetRecordPath(path);
        LogCenter.Instance().PrintLog(Level.INFO, "修改路径至:" + path);
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="设置设备信息"> 
    public boolean SetDevEia(SSEquipmentInfo eia, String info) {
        try {
            Device devcie = this.instance.GetManager().ApplyDevice();
            devcie.SetEquipmentInfo(eia);
            LogCenter.Instance().PrintLog(Level.INFO, "设置" + eia.BuildSerialNum + "成功");

            this.instance.GetRecorder().AddRecord(eia, info);
            LogCenter.Instance().PrintLog(Level.INFO, "保存记录成功");
            return true;
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.WARNING, ex);
            return false;
        } finally {
            this.instance.GetManager().FreeDevice();
        }
    }

    public byte GetDevAddr() {        
        return -1;        
    }

    public void SetDevAddr(byte addr) {
        if(addr <= 0){
            LogCenter.Instance().SendFaultReport(Level.WARNING, "地址必须大于0");
            return;
        }
        try {
            Device device  = this.instance.GetManager().ApplyDevice();
            device.SetDevAddrNum(addr);
            LogCenter.Instance().PrintLog(Level.INFO, "设置地址" + addr + "成功");
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.WARNING, ex);
        } finally {
            this.instance.GetManager().FreeDevice();
        }
    }
    // </editor-fold> 
}
