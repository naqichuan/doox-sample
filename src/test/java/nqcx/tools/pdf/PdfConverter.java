/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.pdf;

import com.itextpdf.io.image.ImageType;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * pdf 转换器
 *
 * @author naqichuan 17/11/7 11:13
 */
public class PdfConverter {


    private String fileName;

    public void run(String[] args) throws IOException {
        if (!processArguments(args)) {
            System.err.println("args error");
            return;
        }

        File pdf = new File(fileName);
        if (!pdf.exists()) {
            System.err.println("File '" + pdf.getCanonicalPath() + "' not exists!");
            return;
        }
        System.out.println("File name: " + pdf.getCanonicalPath() + ", size: " + pdf.length());

        // ++++++++++++++++++++++++++++
        String bbb = "/Users/nqcx/Downloads/bbb";
        File bbbDir = new File(bbb);
        if (!bbbDir.exists())
            return;
        File[] files = bbbDir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f != null && f.exists() && f.isFile())
                    f.delete();
            }
        }
        // ++++++++++++++++++++++++++++

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(fileName));
        PdfObject obj;
        PdfStream stream;
        for (int i = 1; i <= pdfDoc.getNumberOfPdfObjects(); i++) {
            obj = pdfDoc.getPdfObject(i);
            if (obj == null || !obj.isStream())
                continue;

            stream = (PdfStream) obj;
            System.out.println(stream.getAsName(PdfName.Subtype));

            if (!PdfName.Image.equals(stream.getAsName(PdfName.Subtype)))
                continue;
//            if (!PdfName.DCTDecode.equals(stream.getAsName(PdfName.Filter)))
//                continue;

            PdfImageXObject image = new PdfImageXObject(stream);
            BufferedImage bi = image.getBufferedImage();
            if (bi == null)
                continue;

            ImageType imageType = image.identifyImageType();
            File imgFile = new File(bbb + "/" + i + "." + imageType.name().toString().toLowerCase());

            BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
            newBi.getGraphics().drawImage(bi, 0, 0, null);
            ImageIO.write(newBi, "PNG", imgFile);

            System.out.println("Write file " + imgFile.getCanonicalPath() + ", " + imgFile.length());

        }
        pdfDoc.close();
    }


    /**
     * 处理参数
     *
     * @param args 参数表
     * @return boolean
     */
    private boolean processArguments(String[] args) {
        if (args == null || args.length < 1) {
            return false;
        }

        fileName = args[0];

        return true;
    }


    public static void main(String[] args) throws IOException {
        new PdfConverter().run(args);

        System.exit(0);
    }
}
