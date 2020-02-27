/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.mode;

/**
 *
 * @author chejf
 */
public class ModeGarage {

    private DevManager manager;

    public DevManager GetManager() {
        if (this.manager == null) {
            this.manager = new DevManager();
        }
        return this.manager;
    }
    private Recorder recorder;

    public Recorder GetRecorder() {
        if (this.recorder == null) {
            this.recorder = new Recorder();
        }
        return this.recorder;
    }
}
