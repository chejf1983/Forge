/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.form.common;

import forge.bill.mode.DevManager;
import forge.bill.platform.ForgeSystem;
import forge.bill.platform.SystemConfig;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import nahon.comm.event.Event;
import nahon.comm.faultsystem.LogCenter;
import nq.dev.drv.NqDevDriverSearch;

/**
 *
 * @author chejf
 */
public class MainUICenter {

    private MainUICenter() {
    }

    private static MainUICenter instance;

    public static MainUICenter GetInstance() {
        if (instance == null) {
            instance = new MainUICenter();
        }
        return instance;
    }

    public void Init(boolean isinternal) throws Exception {
        //初始化系统
        ForgeSystem.GetInstance().initSystem();
        SystemConfig.GetInstance().SetValue(SystemConfig.InternalFlag, String.valueOf(isinternal));

        LogCenter.Instance().RegisterFaultEvent(new nahon.comm.event.EventListener<Level>() {
            @Override
            public void recevieEvent(Event<Level> event) {
                if (event.GetEvent() == Level.INFO) {
                    JOptionPane.showMessageDialog(null, event.Info().toString(), "提示", INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, event.Info().toString(), "错误信息", ERROR_MESSAGE);
                }
            }
        });

        DevManager.SetDrvierBin(new NqDevDriverSearch());
    }
}
