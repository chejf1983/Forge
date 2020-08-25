/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nq.dev.drv;

import base.migp.impl.MIGPBoot;
import base.migp.impl.MIGPEia;
import base.migp.mem.*;
import base.migp.node.MIGP_CmdSend;
import base.pro.data.EquipmentInfo;
import forge.bill.data.IProcess;
import forge.bill.data.SSEquipmentInfo;
import forge.bill.devdrv.Device;
import forge.bill.devdrv.IDevDriver;
import java.io.File;
import nahon.comm.io.AbstractIO;
import nq.dev.drv.update.UpdateBean;

/**
 *
 * @author chejf
 */
public class NqDevDriver implements IDevDriver {

    private final MIGP_CmdSend migpsend;
    private final int def_timeout = 300;
    public final static int DeviceName_Length = 0x10;
//    public final static int Hardversion_Length = 0x01;
//    public final static int SoftwareVersion_Length = 0x04;
    public final static int BuildSerialNum_Length = 0x10;
//    public final static int BuildDate_Length = 0x10;
//    public final static int DevCatalog_Length = 0x02;
//    public final static int DevType_Length = 0x02;

    NqDevDriver(AbstractIO physicalInterface, byte localAddr, byte dstAddr) {
        this.migpsend = new MIGP_CmdSend(physicalInterface, localAddr, dstAddr);
    }

    @Override
    public boolean IsOpened() {
        if (this.migpsend == null) {
            return false;
        }
        return !this.migpsend.GetIO().IsClosed();
    }

    @Override
    public void Open() throws Exception {
        this.migpsend.GetIO().Open();
    }

    @Override
    public void Close() {
        this.migpsend.GetIO().Close();
    }

    @Override
    public void UpdateFile(File updateFile, IProcess ret) throws Exception {
        new UpdateBean(this.migpsend).UpdateFile(updateFile, ret);
    }

    @Override
    public SSEquipmentInfo GetEquipmentInfo() throws Exception {
        EquipmentInfo eia = new MIGPEia(this.migpsend).GetEquipmentInfo(def_timeout);
        SSEquipmentInfo meia = new SSEquipmentInfo();
        meia.BuildDate = eia.BuildDate;
        meia.BuildSerialNum = eia.BuildSerialNum;
        meia.DeviceName = eia.DeviceName;
        meia.Hardversion = eia.Hardversion;
        meia.SoftwareVersion = eia.SoftwareVersion;
        return meia;
    }

    @Override
    public void SetEquipmentInfo(SSEquipmentInfo eia) throws Exception {
        EquipmentInfo meia = new EquipmentInfo();
        meia.BuildDate = eia.BuildDate;
        meia.BuildSerialNum = eia.BuildSerialNum;
        meia.DeviceName = eia.DeviceName;
        meia.Hardversion = eia.Hardversion;
        meia.SoftwareVersion = eia.SoftwareVersion;
        if (meia.DeviceName.length() > DeviceName_Length) {
            throw new Exception("设备名称超过长度" + DeviceName_Length);
        }
        if (meia.BuildSerialNum.length() > BuildSerialNum_Length) {
            throw new Exception("序列号超过长度" + BuildSerialNum_Length);
        }
        new MIGPEia(this.migpsend).SetEquipmentInfo(meia, def_timeout);
    }

    @Override
    public void SetDevAddrNum(byte addr) throws Exception {
        //new MIGPEia(this.migpsend).SetDevAddr(addr, def_timeout);
        new MIGPBoot(this.migpsend).SetDevNum(addr);
    }

    @Override
    public byte[] GetMEM(Device.MEMTYPE MEM_ID, int MEM_ADDR, int MEM_Length) throws Exception {
        //MIGP_CmdSend.GETEIA;
        MEM memid = new EIA(0, 0);
        switch (MEM_ID) {
            case EIA:
                memid = new EIA(MEM_ADDR, MEM_Length);
                break;
            case VPA:
                memid = new VPA(MEM_ADDR, MEM_Length);
                break;
            case NVPA:
                memid = new NVPA(MEM_ADDR, MEM_Length);
                break;
            case MDA:
                memid = new MDA(MEM_ADDR, MEM_Length);
                break;
            case SRA:
                memid = new SRA(MEM_ADDR, MEM_Length);
                break;
            default:
                throw new AssertionError(MEM_ID.name());

        }
        return this.migpsend.GetMEM(memid, memid.length, 3, def_timeout);
    }

    @Override
    public void SetMem(Device.MEMTYPE MEM_ID, int MEM_ADDR, int MEM_Length, byte[] data) throws Exception {
        //MIGP_CmdSend.GETEIA;
        MEM memid = new EIA(0, 0);
        switch (MEM_ID) {
            case EIA:
                memid = new EIA(MEM_ADDR, MEM_Length);
                break;
            case VPA:
                memid = new VPA(MEM_ADDR, MEM_Length);
                break;
            case NVPA:
                memid = new NVPA(MEM_ADDR, MEM_Length);
                break;
            case MDA:
                memid = new MDA(MEM_ADDR, MEM_Length);
                break;
            case SRA:
                memid = new SRA(MEM_ADDR, MEM_Length);
                break;
            default:
                throw new AssertionError(MEM_ID.name());
        }
        this.migpsend.SetMEM(memid, memid.length, data, 3, def_timeout);
    }

}
