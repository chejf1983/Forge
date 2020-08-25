/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.devdrv;

import nahon.comm.io.IOInfo;


/**
 *
 * @author chejf
 */
public interface IDevDriverSearch {
    IDevDriver SearchDevDriver(IOInfo ioinfo, byte dst_addr) throws Exception;
}
