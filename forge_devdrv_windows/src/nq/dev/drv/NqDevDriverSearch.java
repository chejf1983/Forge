/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nq.dev.drv;

import base.pro.absractio.AbstractIO;
import base.pro.absractio.IOInfo;
import comm.absractio.WAbstractIO;
import comm.absractio.WIOInfo;
import comm.win.io.WindowsIOFactory;
import forge.bill.data.SIOInfo;
import forge.bill.devdrv.IDevDriver;
import forge.bill.devdrv.IDevDriverSearch;

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
    public IDevDriver SearchDevDriver(SIOInfo ioinfo, byte dst_addr) throws Exception {
        WindowsIOFactory.InitWindowsIODriver();

        //创建IO
        WIOInfo w_io = new WIOInfo(ioinfo.iotype, ioinfo.pars);
        if (null == WindowsIOFactory.CreateIO(w_io)) {
            return null;
        }

        //转换IO接口
        AbstractIO io = new AbstractIO() {
            WAbstractIO wio = WindowsIOFactory.CreateIO(w_io);

            @Override
            public boolean IsClosed() {
                return this.wio.IsClosed();
            }

            @Override
            public void Open() throws Exception {
                this.wio.Open();
            }

            @Override
            public void Close() {
                this.wio.Close();
            }

            @Override
            public void SendData(byte[] data) throws Exception {

                this.wio.SendData(data);
                if (io_show != null) {
                    io_show.ShowData(IIOData.Dirc.Send, data, "");
                }
            }

            @Override
            public int ReceiveData(byte[] data, int timeout) throws Exception {
                int ret = this.wio.ReceiveData(data, timeout);
                if (io_show != null) {
                    byte[] tmp = new byte[ret];
                    System.arraycopy(data, 0, tmp, 0, tmp.length);
                    io_show.ShowData(IIOData.Dirc.Rec, tmp, "");
                }
                return ret;
            }

            @Override
            public IOInfo GetConnectInfo() {
                WIOInfo wiof = this.wio.GetConnectInfo();
                return new IOInfo(wiof.iotype, wiof.par);
            }

            @Override
            public int MaxBuffersize() {
                return this.wio.MaxBuffersize();
            }
        };
        //默认都可以
        return new NqDevDriver(io, (byte) 0xFE, dst_addr);
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
