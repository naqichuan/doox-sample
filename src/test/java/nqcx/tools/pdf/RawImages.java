/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.pdf;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.File;

/**
 * @author naqichuan 17/11/8 22:34
 */
public class RawImages {

    public static void main(String[] args) throws Exception {
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

        new RawImages().manipulatePdf(bbb + "/cmp_raw_images.pdf");

    }

    protected void manipulatePdf(String dest) throws Exception {

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());

        Image gray = new Image(ImageDataFactory.create(1, 1, 1, 8, new byte[]{(byte) 0x80}, null));
        gray.scaleToFit(30, 30);

        Image red = new Image(ImageDataFactory.create(1, 1, 3, 8,
                new byte[]{(byte) 255, (byte) 0, (byte) 0}, null));
        red.scaleToFit(30, 30);

        Image green = new Image(ImageDataFactory.create(1, 1, 3, 8,
                new byte[]{(byte) 0, (byte) 255, (byte) 0}, null));
        green.scaleToFit(30, 30);

        Image blue = new Image(ImageDataFactory.create(1, 1, 3, 8,
                new byte[]{(byte) 0, (byte) 0, (byte) 255}, null));
        blue.scaleToFit(30, 30);

        Image cyan = new Image(ImageDataFactory.create(1, 1, 4, 8,
                new byte[]{(byte) 255, (byte) 0, (byte) 0, (byte) 0}, null));
        cyan.scaleToFit(30, 30);

        Image magenta = new Image(ImageDataFactory.create(1, 1, 4, 8,
                new byte[]{(byte) 0, (byte) 255, (byte) 0, (byte) 0}, null));
        magenta.scaleToFit(30, 30);

        Image yellow = new Image(ImageDataFactory.create(1, 1, 4, 8,
                new byte[]{(byte) 0, (byte) 0, (byte) 255, (byte) 0}, null));
        yellow.scaleToFit(30, 30);

        Image black = new Image(ImageDataFactory.create(1, 1, 4, 8,
                new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 255}, null));
        black.scaleToFit(30, 30);

        Image pic = new Image(ImageDataFactory.create("/Users/nqcx/Downloads/8_reduce.png"));


        doc.add(gray);
        doc.add(red);
        doc.add(green);
        doc.add(blue);
        doc.add(cyan);
        doc.add(magenta);
        doc.add(yellow);
        doc.add(black);

        doc.add(pic);

        doc.close();
    }
}
