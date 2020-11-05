/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.mode;

import forge.bill.devdrv.Device;
import forge.bill.devdrv.IDevDriver;
import forge.bill.devdrv.IDevDriverSearch;
import java.util.concurrent.locks.ReentrantLock;
import nahon.comm.io.IOInfo;

/**
 *
 * @author chejf
 */
public class DevManager {

    private static IDevDriverSearch instance;

    public static void SetDrvierBin(IDevDriverSearch search) {
        instance = search;
    }

    private final ReentrantLock managerlock = new ReentrantLock();

    // <editor-fold defaultstate="collapsed" desc="搜索设备"> 
    //搜索设备
    public void SearchDevice(IOInfo ioinfo, byte dst_addr) throws Exception {
        managerlock.lock();
        try {
            //删除当前设备
            this.ClearDevice();
            //检查驱动
            if (instance == null) {
                throw new Exception("没有加载驱动");
            }
            //搜索设备
            IDevDriver devnode = instance.SearchDevDriver(ioinfo, dst_addr);
            if (devnode != null) {
                //初始化设备控制器
                this.current_dev = new Device(devnode);
            }
        } finally {
            this.managerlock.unlock();
        }
    }

    //清除设备
    public void ClearDevice() throws Exception {
        if (this.IsDeviceApplied()) {
            throw new Exception("设备正在运行，请先关闭");
        }
        this.current_dev = null;
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="申请设备"> 
    private Device current_dev;
    private final ReentrantLock applylock = new ReentrantLock();

    //是否又设备被锁定
    public boolean IsDeviceApplied() {
        return this.applylock.isLocked();
    }

    public boolean IsFindDev(){
        return this.current_dev != null;
    }
    
    //申请设备
    public Device ApplyDevice() throws Exception {
        if (this.IsDeviceApplied()) {
            throw new Exception("当前设备忙");
        }

        if (this.current_dev == null) {
            throw new Exception("没有连接设备");
        }

        managerlock.lock();
        try {
            this.applylock.lock();
            return this.current_dev;
        } finally {
            this.managerlock.unlock();
        }
    }

    //释放设备
    public void FreeDevice() {
        managerlock.lock();
        try {
            this.applylock.unlock();
        } finally {
            this.managerlock.unlock();
        }
    }
    // </editor-fold>  

}
