/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.devdrv;

import forge.bill.data.IProcess;
import forge.bill.data.SSEquipmentInfo;
import java.io.File;

/**
 *
 * @author chejf
 */
public class Device {

    private IDevDriver dev_drv = null;

    public Device(IDevDriver dev_drv) throws Exception {
        this.dev_drv = dev_drv;        
    }

    // <editor-fold defaultstate="collapsed" desc="升级"> 
    //升级
    public void UpdateFile(final File updateFile, IProcess ret) throws Exception {
        this.dev_drv.Open();
        try {
            this.dev_drv.UpdateFile(updateFile, ret);
        } finally {
            this.dev_drv.Close();
        }
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="设备信息"> 
    //获取EIA信息
    public SSEquipmentInfo GetEquipmentInfo() throws Exception{
        dev_drv.Open();
        try {
            return dev_drv.GetEquipmentInfo();
        } finally {
            dev_drv.Close();
        }
    }

    //获取EIA信息
    public void SetEquipmentInfo(SSEquipmentInfo eia) throws Exception {
        this.dev_drv.Open();
        try {
            this.dev_drv.SetEquipmentInfo(eia);
        } finally {
            this.dev_drv.Close();
        }
    }

    private byte dev_addr = -1;

    //获取设备地址
    public byte GetDevAddr() {
        return this.dev_addr;
    }

    //设置设备地址
    public void SetDevAddrNum(byte addr) throws Exception {
        this.dev_drv.Open();
        try {
            this.dev_drv.SetDevAddrNum(addr);
            this.dev_addr = addr;
        } finally {
            this.dev_drv.Close();
        }
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="内存设置"> 
    //内存类型
    public enum MEMTYPE {
        EIA,
        VPA,
        NVPA,
        MDA,
        SRA
    }

    //取内存
    public byte[] GetMEM(MEMTYPE MEM_ID, int MEM_ADDR, int MEM_Length) throws Exception {
        this.dev_drv.Open();
        try {
            return this.dev_drv.GetMEM(MEM_ID, MEM_ADDR, MEM_Length);
        } finally {
            this.dev_drv.Close();
        }
    }

    //写内存
    public void SetMem(MEMTYPE MEM_ID, int MEM_ADDR, int MEM_Length, byte[] data) throws Exception {
        this.dev_drv.Open();
        try {
            this.dev_drv.SetMem(MEM_ID, MEM_ADDR, MEM_Length, data);
        } finally {
            this.dev_drv.Close();
        }
    }
    // </editor-fold>  
}
