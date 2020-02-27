/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.form.common;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author jiche
 */
public abstract class InitPaneHelper {
//    public static void InitAppArea(JPanel Area, JComponent obj) {
//        GroupLayout mainPanelLayout = new GroupLayout(Area);
//        Area.removeAll();
//
//        if (obj != null) {
//            obj.setSize(Area.getSize());
//            Area.add(obj);
//
//            mainPanelLayout.setHorizontalGroup(
//                    mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(mainPanelLayout.createSequentialGroup()
//                    .addComponent(obj)));
//            mainPanelLayout.setVerticalGroup(
//                    mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(mainPanelLayout.createSequentialGroup()
//                    .addComponent(obj)));
//        }
//
//        Area.setLayout(mainPanelLayout);
//    }    
    
    private static String LastPath = "";
    public static String GetFilePath(final String filend) {
        JFileChooser dialog = new JFileChooser(LastPath);
        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialog.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return filend;
            }

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                return f.getName().endsWith(filend);
            }
        });

        int result = dialog.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile().getPath();
        } else {
            return null;
        }
    }
    
    public static String GetDirPath(){
        JFileChooser dialog = new JFileChooser(LastPath);
        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int result = dialog.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile().getPath();            
        } else {
            return null;
        }
    }
}
