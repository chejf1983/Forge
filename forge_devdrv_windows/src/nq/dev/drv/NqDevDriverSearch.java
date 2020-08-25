/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nq.dev.drv;

import comm.win.io.WindowsIOFactory;
import forge.bill.devdrv.IDevDriver;
import forge.bill.devdrv.IDevDriverSearch;
import nahon.comm.io.IOInfo;

/**
 *
 * @author chejf
 */
public class NqDevDriverSearch implements IDevDriverSearch {

    private static IIOData io_show = null;

    public static void RegisterIoShow(IIOData list) {
        io_show = list;
    }

    @Override
    public IDevDriver SearchDevDriver(IOInfo ioinfo, byte dst_addr) throws Exception {
        WindowsIOFactory.InitWindowsIODriver();

        //默认都可以
        return new NqDevDriver(WindowsIOFactory.CreateIO(ioinfo), (byte) 0xFE, dst_addr);
        //打开IO口
//        io.Open();
//        try {
//            //向0地址读取EIA信息，读到就表示有设备连接
//            MIGPEia eia = new MIGPEia(new MIGP_CmdSend(io, (byte) 0xFE, (byte) dst_addr));
//            eia.GetEquipmentInfo_Fast(500);
//            return new NqDevDriver(io, (byte) 0xFE, dst_addr);
//        } catch (Exception ex) {
//            //有异常，表示没有设备
//            return null;
//        } finally {
//            //默认IO口关闭
//            io.Close();
//        }

    }
}
