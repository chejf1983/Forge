/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.control;

import forge.bill.mode.ModeGarage;

/**
 *
 * @author chejf
 */
public class ControlCenter {

    private final ModeGarage instance;

    public ControlCenter(ModeGarage instance) {
        this.instance = instance;
    }

    private DevConfigBean config_bean;

    public DevConfigBean GetConfigure() {
        if (this.config_bean == null) {
            this.config_bean = new DevConfigBean(this.instance);
        }
        return config_bean;
    }

    private DevConnect connect_bean;

    public DevConnect GetConnector() {
        if (this.connect_bean == null) {
            this.connect_bean = new DevConnect(this.instance);
        }
        return connect_bean;
    }

    private DevDebug dev_debug;

    public DevDebug GetDebuger() {
        if (this.dev_debug == null) {
            this.dev_debug = new DevDebug(this.instance);
        }
        return this.dev_debug;
    }
    
    private UpDateBean update_bean;
    public UpDateBean GetUpdateBean(){
        if (this.update_bean == null) {
            this.update_bean = new UpDateBean(this.instance);
        }
        return this.update_bean;
    }
}
