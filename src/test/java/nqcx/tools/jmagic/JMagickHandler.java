///*
// * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
// * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
// * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
// */
//
//package nqcx.tools.jmagic;
//
//import java.awt.Dimension;
//import java.io.File;
//import org.apache.commons.io.FileUtils;
//import com.gif4j.GifDecoder;
//import com.gif4j.GifEncoder;
//import com.gif4j.GifImage;
//import com.gif4j.GifTransformer;
//import magick.ImageInfo;
//import magick.MagickException;
//import magick.MagickImage;
//import org.nqcx.commons.util.StringUtils;
//
///**
// * @author naqichuan 17/11/9 10:00
// */
//public class JMagickHandler {
//    private final static int DEFAULT_QUALITY = 90;
//    protected JMagickHandler(){}
//    private final static ThreadLocal<MyMagickImage> tLocal = new ThreadLocal<MyMagickImage>();
//    private String lastSrc = null;
//    /**
//     * 清除线程本地存储信息
//     */
//    private synchronized MyMagickImage getMagickImage(String src) throws Exception {
//        MyMagickImage mi = tLocal.get();
//        if(mi != null && StringUtils.equals(src, lastSrc))
//            return mi;
//        else if(mi != null)
//            tLocal.remove();
//        this.lastSrc = src;
//        ImageInfo info = new ImageInfo(src);
//        mi = new MyMagickImage(info);
//        tLocal.set(mi);
//        return mi;
//    }
//    @Override
//    public void cleanup() {
//        MyMagickImage mi = tLocal.get();
//        if(mi != null)
//            mi.destroyImages();
//        tLocal.remove();
//    }
//    @Override
//    public ImageExtInfo getImageInfo(String src) throws Exception {
//        try{
//            MagickImage image = this.getMagickImage(src);
//            ImageExtInfo ext = new ImageExtInfo();
//            Dimension dim = image.getDimension();
//            ext.setWidth((int)dim.getWidth());
//            ext.setHeight((int)dim.getHeight());
//            ext.setSize(image.sizeBlob());
//            ext.setAnimated(image.isAnimatedImage());
//            return ext;
//        }catch(MagickException e){
//            return new PureJavaHandler().getImageInfo(src);
//        }
//    }
//    private final static boolean hasUnicode(String str){
//        return str.getBytes().length != str.length();
//    }
//    @Override
//    public void resize(String src, String dest, int width, int height, int quality) throws Exception {
//        boolean u_src = hasUnicode(src);
//        MyMagickImage image;
//        File srctmp = null;
//        if(u_src){
//            srctmp = File.createTempFile("jmagick_s_" + src.hashCode(), null);
//            FileUtils.copyFile(new File(src), srctmp);
//            image = this.getMagickImage(srctmp.getAbsolutePath());
//        }
//        else
//            image = this.getMagickImage(src);
//        image.getImageInfo().setQuality((quality>0)?quality:DEFAULT_QUALITY);
//        MagickImage scaledimage = null;
//        try{
//            if(image.isAnimatedImage()){
//                GifImage gifImage = GifDecoder.decode(new File(src));
//                GifImage newGif = GifTransformer.resize(gifImage, width, height, false);
//                GifEncoder.encode(newGif, new File(dest));
//            }
//            else{//others
//                scaledimage = image.scaleImage(width, height);
//                scaledimage.setImageFormat("JPEG");
//                scaledimage.profileImage("*", null);
//                boolean u_dest = hasUnicode(dest);
//                if(u_dest){
//                    File tmp = File.createTempFile("jmagick_d_" + dest.hashCode(), null);
//                    scaledimage.setFileName(tmp.getAbsolutePath());
//                    scaledimage.writeImage(image.getImageInfo());
//                    FileUtils.copyFile(tmp, new File(dest));
//                    FileUtils.forceDelete(tmp);
//                }
//                else{
//                    scaledimage.setFileName(dest);
//                    scaledimage.writeImage(image.getImageInfo());
//                }
//            }
//        }finally{
//            if(srctmp != null) FileUtils.forceDelete(srctmp);
//            if(scaledimage != null)
//                scaledimage.destroyImages();
//        }
//    }
//    @Override
//    public void rotate(String src, String dest, double degrees) throws Exception {
//        boolean u_src = hasUnicode(src);
//        MyMagickImage image;
//        File srctmp = null;
//        if(u_src){
//            srctmp = File.createTempFile("jmagick_s_" + src.hashCode(), null);
//            FileUtils.copyFile(new File(src), srctmp);
//            image = this.getMagickImage(srctmp.getAbsolutePath());
//        }
//        else
//            image = this.getMagickImage(src);
//        try{
//            MagickImage rotateImg = image.rotateImage(degrees);
//            rotateImg.profileImage("*", null);
//
//            boolean u_dest = hasUnicode(dest);
//            if(u_dest){
//                File tmp = File.createTempFile("jmagick_d_" + dest.hashCode(), null);
//                rotateImg.setFileName(tmp.getAbsolutePath()); // convert to png
//                rotateImg.writeImage(image.getImageInfo());
//                rotateImg.destroyImages();
//                FileUtils.copyFile(tmp, new File(dest));
//                FileUtils.forceDelete(tmp);
//            }
//            else{
//                rotateImg.setFileName(dest); // convert to png
//                rotateImg.writeImage(image.getImageInfo());
//                rotateImg.destroyImages();
//            }
//        }finally{
//            if(srctmp != null)
//                FileUtils.forceDelete(srctmp);
//        }
//    }
//    private static class MyMagickImage extends MagickImage {
//        private ImageInfo imageInfo;
//        public ImageInfo getImageInfo() {
//            return imageInfo;
//        }
//        public MyMagickImage(ImageInfo info) throws MagickException{
//            super(info);
//            this.imageInfo = info;
//        }
//    }
//    @Override
//    public void cropImage(String src, int width, int height, int point,
//                          int point2, String dst) throws Exception {
//        // TODO Auto-generated method stub
//    }
//}
