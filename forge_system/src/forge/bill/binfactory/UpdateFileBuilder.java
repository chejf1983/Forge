/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forge.bill.binfactory;

import forge.bill.data.UpdateFileHead;
import forge.bill.platform.ForgeSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import nahon.comm.faultsystem.LogCenter;
import nahon.comm.tool.convert.MyConvert;

/**
 *
 * @author jiche 升级文件格式
 *
 * UpdateFileHead
 *
 * file1-length
 *
 * file1
 *
 * file end mark
 *
 * file2 length
 *
 * file2
 *
 * file end mark
 *
 */
public class UpdateFileBuilder {

    // <editor-fold defaultstate="collapsed" desc="文件压缩控制接口">  
    //总共的文件大小（以FLASH_SPAN_LENGTH为单位）
    private long totalsize = 0;
    //完成大小
    private long writensize = 0;

    private boolean isprocessing = false;

    /**
     * 压缩文件
     *
     * @param input
     * @param output
     * @param fileHead
     * @return
     */
    public void CompressBinFiles(final File[] input, final File output, final UpdateFileHead fileHead) {
        //如果正在压缩升级包，抛出异常
        if (isprocessing) {
            LogCenter.Instance().SendFaultReport(Level.INFO, "正在压缩，请稍后");
            return;
        }
        //设置开始压缩状态
        this.isprocessing = true;
        this.lastpecent = 0;
        this.totalsize = 0;
        this.writensize = 0;

        //入参检查
        if (!this.checkInputPar(input, output, fileHead)) {
            this.isprocessing = false;
            return;
        }

        //统计总共文件大小(以一次缓存大小为单位)
        for (File f : input) {
            this.totalsize += (f.length() - 1 + bufferlen) / bufferlen;
        }

        //触发压缩进程
        ForgeSystem.GetInstance().systemthreadpool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建压缩文件
                    startcompressfiles(input, output, fileHead);
                    if (isprocessing) {
                        LogCenter.Instance().PrintLog(Level.INFO, "制作成功!");
                    } else {
                        LogCenter.Instance().PrintLog(Level.INFO, "制作取消!");
                    }
                } catch (Exception ex) {
                    LogCenter.Instance().SendFaultReport(Level.SEVERE, "制作失败", ex);
                } finally {
                    isprocessing = false;
                }
            }
        });
    }

    //取消制作
    public void Cancel() {
        if (isprocessing) {
            this.isprocessing = false;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="文件压缩接口">  
    //一次读取缓存大小
    private int bufferlen = 2048;

    //检查输入参数
    private boolean checkInputPar(final File[] input, final File output, final UpdateFileHead fileHead) {
        //如果原始文件为空，报错
        if (input == null || input.length == 0) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, "没有找到升级文件");
            return false;
        }

        //没有输出文件，报错
        if (output == null) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, "没有找到输出文件");
            return false;
        }

        //输入文件不能等于输出文件
        for (File ifile : input) {
            if (ifile.getAbsolutePath().contentEquals(output.getAbsolutePath())) {
                LogCenter.Instance().SendFaultReport(Level.SEVERE, "输入文件不能等于输出文件");
                return false;
            }
        }
        return true;
    }

    /**
     * 压缩文件
     */
    private void startcompressfiles(File[] inputBinFiles, File output, UpdateFileHead fileHead) throws Exception {
        /* Update File Header parameter */
        fileHead.BinFileNumbier = inputBinFiles.length;

        /* Clean output file */
        FileOutputStream outStream = new FileOutputStream(output);
        outStream.flush();

        /* input file head to new update file bin */
        outStream.write(fileHead.ToBytesArray());

        /* input all bin file to update file bin */
        for (int i = 0; i < inputBinFiles.length; i++) {
            compressfile(outStream, inputBinFiles[i]);
        }

        /* Close update file */
        outStream.close();
    }

    //压缩一个文件
    private void compressfile(FileOutputStream outStream, File srcfile) throws Exception {
        /* input file length at firtst */
        outStream.write(MyConvert.LongToByteArray(srcfile.length()));

        //创建读取文件流
        FileInputStream inputStream = new FileInputStream(srcfile);

        //如果输入可读，同时当前在工作状态下
        while (inputStream.available() != 0 && this.isprocessing) {
            //创建一个空白buffer
            byte[] tmp = new byte[bufferlen];
            //读取数据，不足一个扇区，末尾添0
            int readlen = inputStream.read(tmp);

            if (readlen == bufferlen) {
                //写数据
                outStream.write(tmp);
            } else {
                outStream.write(tmp, 0, readlen);
            }
            //增加完成度
            writensize += 1;

            int pecent = this.getpecent();
            if (pecent - this.lastpecent > 10) {
                this.lastpecent = pecent;
                LogCenter.Instance().PrintLog(Level.INFO, this.lastpecent + "%");
            }
        }

        //关闭读取文件流
        inputStream.close();

        /* stamp file endmark at last */
        outStream.write(MyConvert.IntegerToByteArray(UpdateFileHead.FileEndMark));
    }

    private int lastpecent = 0;

    private int getpecent() {
        //在工作状态下，计算工作百分比
        if (totalsize == 0) {
            return 0;
        }
        return (int) (writensize * 100 / totalsize);
    }
    // </editor-fold>
}
