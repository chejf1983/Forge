/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.platform;

/**
 *
 * @author chejf
 */
public class PropertiesNode {

    public String Key;
    public String DefaultValue;

    public PropertiesNode(String key, String value) {
        this.Key = key;
        this.DefaultValue = value;
    }
}
