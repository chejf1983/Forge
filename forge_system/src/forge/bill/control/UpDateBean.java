/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.control;

import forge.bill.binfactory.UpdateFileBuilder;
import forge.bill.data.IProcess;
import forge.bill.devdrv.Device;
import forge.bill.mode.ModeGarage;
import forge.bill.platform.ForgeSystem;
import java.io.File;
import java.util.logging.Level;
import nahon.comm.faultsystem.LogCenter;

/**
 *
 * @author chejf
 */
public class UpDateBean {

    private final ModeGarage instance;

    UpDateBean(ModeGarage instance) {
        this.instance = instance;
    }

    private boolean isupdating = false;

    public void UpdateFile(File updateFile) {
        if (this.isupdating) {
            LogCenter.Instance().SendFaultReport(Level.WARNING, "正在升级，请稍后");
            return;
        }
        this.isupdating = true;
        LogCenter.Instance().PrintLog(Level.INFO, "开始升级");
        //触发压缩进程
        ForgeSystem.GetInstance().systemthreadpool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Device device = instance.GetManager().ApplyDevice();
                    device.UpdateFile(updateFile, new IProcess() {
                        private int lastpecent = 0;

                        @Override
                        public void SetPecent(float pecent) {
                            if (pecent * 100 - this.lastpecent > 5) {
                                this.lastpecent = (int) (pecent * 100);
                                LogCenter.Instance().PrintLog(Level.INFO, this.lastpecent + "%");
                            }
                        }
                    });
                    LogCenter.Instance().SendFaultReport(Level.INFO, "升级成功");
                } catch (Exception ex) {
                    LogCenter.Instance().SendFaultReport(Level.WARNING, "升级失败!", ex);
                } finally {
                    instance.GetManager().FreeDevice();
                    isupdating = false;
                }
            }
        });
    }

    private UpdateFileBuilder updatebin;

    public UpdateFileBuilder GetUpdateBuilder() {
        if (updatebin == null) {
            updatebin = new UpdateFileBuilder();
        }
        return this.updatebin;
    }
}
