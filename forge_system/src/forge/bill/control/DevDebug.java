/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.control;

import forge.bill.devdrv.Device;
import forge.bill.devdrv.Device.MEMTYPE;
import forge.bill.mode.ModeGarage;
import java.util.logging.Level;
import nahon.comm.faultsystem.LogCenter;

/**
 *
 * @author chejf
 */
public class DevDebug {

    private final ModeGarage instance;

    DevDebug(ModeGarage instance) {
        this.instance = instance;
    }

    public void SetMEM(MEMTYPE MEMID, int addr, int len, byte[] data) {
        try {
            Device device = this.instance.GetManager().ApplyDevice();
            device.SetMem(MEMID, addr, len, data);
            LogCenter.Instance().PrintLog(Level.INFO, "设置内存" + MEMID + "成功");
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.WARNING, ex);
        } finally {
            this.instance.GetManager().FreeDevice();
        }
    }

    public byte[] GetMEM(MEMTYPE MEMID, int addr, int len) {
        try {
            Device device = this.instance.GetManager().ApplyDevice();        
            return device.GetMEM(MEMID, addr, len);
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.WARNING, ex);
            return null;
        } finally {
            this.instance.GetManager().FreeDevice();
        }
    }
}
