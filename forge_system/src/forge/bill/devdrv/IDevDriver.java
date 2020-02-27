/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.devdrv;

import forge.bill.data.IProcess;
import forge.bill.data.SSEquipmentInfo;
import forge.bill.devdrv.Device.MEMTYPE;
import java.io.File;

/**
 *
 * @author chejf
 */
public interface IDevDriver {

    public boolean IsOpened();

    public void Open() throws Exception;

    public void Close();

    //升级
    public void UpdateFile(final File updateFile, IProcess ret) throws Exception;

    //获取EIA信息
    public SSEquipmentInfo GetEquipmentInfo() throws Exception;

    //获取EIA信息
    public void SetEquipmentInfo(SSEquipmentInfo eia) throws Exception;

    //设置设备地址
    public void SetDevAddrNum(byte addr) throws Exception;
         
    //取内存
    public byte[] GetMEM(MEMTYPE MEM_ID, int MEM_ADDR, int MEM_Length) throws Exception;
    
    //写内存
    public void SetMem(MEMTYPE MEM_ID, int MEM_ADDR, int MEM_Length, byte[] data) throws Exception;
}
