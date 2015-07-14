package com.study.common;

/**
 * Created by huichao on 2015/7/14.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图像压缩工具类,可以指定缩放质量,大小,是否等比缩放
 * @author C.H.
 *
 */
public class ImageUtil {
    /**
     * 高质量图像压缩工具类
     * @param originalFile 源文件
     * @param resizedFile 缩放后文件
     * @param newWidth 缩放后图片宽度
     * @param newHeight 缩放后图片高度
     * @param quality 缩放质量,值为0-1之间的float类型数字,值越大质量越高,文件体积越大,例如0.9f
     * @param proportion 是否等比缩放,true为等比缩放,false为非等比缩放
     * @throws IOException 抛IO异常
     */
    public static void resize(File originalFile, File resizedFile,int newWidth, int newHeight, float quality, boolean proportion) throws IOException {

        if (quality > 1 || quality < 0) {
            throw new IllegalArgumentException(
                    "质量参数必须介于0到1之间");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
        if(iWidth==-1||iHeight==-1){
            throw new IOException("原文件不存在");
        }

        if(proportion==true){
            double rate1=((double)iWidth)/((double)newWidth);
            double rate2=((double)iHeight)/((double)newHeight);
            double rate=rate1>rate2?rate1:rate2;
            newWidth=(int)(((double)iWidth)/rate);
            newHeight=(int)(((double)iHeight)/rate);
        }

        resizedImage = i.getScaledInstance(newWidth,newHeight, Image.SCALE_SMOOTH);


        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = { 0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);

        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
        param.setQuality(quality, true);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
        out.close();
    }

    /**
     * 高质量图像压缩工具类
     * @param originalFile 源文件
     * @param newWidth 缩放后图片宽度
     * @param newHeight 缩放后图片高度
     * @param quality 缩放质量,值为0-1之间的float类型数字,值越大质量越高,文件体积越大,例如0.9f
     * @param proportion 是否等比缩放,true为等比缩放,false为非等比缩放
     * @throws IOException 抛IO异常
     */
    public static byte[] resizeOUT(File originalFile,int newWidth, int newHeight, float quality, boolean proportion) throws IOException {

        if (quality > 1 || quality < 0) {
            throw new IllegalArgumentException(
                    "质量参数必须介于0到1之间");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
        if(iWidth==-1||iHeight==-1){
            throw new IOException("原文件不存在");
        }

        if(proportion==true){
            double rate1=((double)iWidth)/((double)newWidth);
            double rate2=((double)iHeight)/((double)newHeight);
            double rate=rate1>rate2?rate1:rate2;
            newWidth=(int)(((double)iWidth)/rate);
            newHeight=(int)(((double)iHeight)/rate);
        }

        resizedImage = i.getScaledInstance(newWidth,newHeight, Image.SCALE_SMOOTH);


        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = { 0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
        param.setQuality(quality, true);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);

        return out.toByteArray();
    }


}