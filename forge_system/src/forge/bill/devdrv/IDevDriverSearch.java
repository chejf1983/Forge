/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.devdrv;

import forge.bill.data.SIOInfo;

/**
 *
 * @author chejf
 */
public interface IDevDriverSearch {
    IDevDriver SearchDevDriver(SIOInfo ioinfo, byte dst_addr) throws Exception;
}
