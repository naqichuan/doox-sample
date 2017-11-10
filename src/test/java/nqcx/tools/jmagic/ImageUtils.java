/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.jmagic;

import magick.CompressionType;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import java.awt.*;
import java.io.File;

/**
 * @author naqichuan 17/11/9 10:23
 */
public class ImageUtils {

    private final static int DEFAULT_QUALITY = 90;
//

    /**
     * 处理图片
     */
    public static void resizePng(File source, File dest, double width, double height, int quality)
            throws MagickException {
        System.setProperty("jmagick.systemclassloader", "no");

        MagickImage sourceImage = null;
        MagickImage destImage = null;
        try {
            ImageInfo info = new ImageInfo(source.getPath());
            //设置图片质量
            info.setQuality(quality > 0 ? quality : DEFAULT_QUALITY);
            sourceImage = new MagickImage(info);
            Dimension dimension = sourceImage.getDimension();

            Double sourceWidth = dimension.getWidth();
            Double sourceHeight = dimension.getHeight();
            Double destWidth = null;
            Double destHeight = null;

            if (!dest.getParentFile().exists())
                dest.getParentFile().mkdirs();

            if (width == 0 && height == 0) {
                width = sourceWidth;
                height = sourceHeight;
            }
            if (sourceWidth > sourceHeight) {
                destWidth = width;
                destHeight = width / sourceWidth * sourceHeight;
                if (destHeight > height && height != 0) {
                    destHeight = height;
                    destWidth = height / sourceHeight * sourceWidth;
                }
            } else {
                destHeight = height;
                destWidth = height / sourceHeight * sourceWidth;
                if (destWidth > width || height == 0) {
                    destWidth = width;
                    destHeight = width / sourceWidth * sourceHeight;
                }
            }
            destImage = sourceImage.scaleImage(destWidth.intValue(), destHeight.intValue());
            destImage.setImageFormat("png");
            // .sharpenImage(SHARPEN_IMG_RADUIS, SHARPEN_IMG_SIGMA);
            //水平分辨率DPI
//            destImage.setXResolution(100);
            //垂直分辨率DPI
//            destImage.setYResolution(100);
            destImage.profileImage("*", null);
            //设置图片压缩
            destImage.setCompression(CompressionType.JPEGCompression);
            //设置对比度
//            destImage.contrastImage(true);
            destImage.setFileName(dest.getPath());
            destImage.writeImage(info);
            info = null;
        } finally {
            if (destImage != null) {
                destImage.destroyImages();
            }
            if (sourceImage != null) {
                sourceImage.destroyImages();
            }
        }
    }

    /**
     * @param str
     * @return
     */
    private final static boolean hasUnicode(String str) {
        return str.getBytes().length != str.length();
    }
}
