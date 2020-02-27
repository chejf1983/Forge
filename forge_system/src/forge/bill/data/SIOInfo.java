/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.data;

/**
 *
 * @author chejf
 */
public class SIOInfo {

    public SIOInfo(String type, String... pars) {
        this.iotype = type;
        this.pars = pars;
    }

    public String iotype;
    public String[] pars;

    public boolean CompareTo(SIOInfo info) {
        if (this.iotype.contentEquals(info.iotype) && info.pars.length == this.pars.length) {
            for (int i = 0; i < pars.length; i++) {
                if (!this.pars[i].contentEquals(info.pars[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
