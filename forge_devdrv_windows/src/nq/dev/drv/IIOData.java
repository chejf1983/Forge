/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nq.dev.drv;

/**
 *
 * @author chejf
 */
public interface IIOData {
    public enum Dirc{
        Send,
        Rec
    }
    
    public void ShowData(Dirc dic, byte[] buffer, String info);
}
