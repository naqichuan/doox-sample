/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.pdf;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.image.ImageType;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.element.Image;
import nqcx.tools.jmagic.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * pdf 转换器
 *
 * @author naqichuan 17/11/7 11:13
 */
public class PdfConverter {

    public static final float FACTOR = 0.5f;

    private String fileName;

    public void run(String[] args) throws Exception {
        if (!processArguments(args)) {
            System.err.println("args error");
            return;
        }

        File pdf = new File(fileName);
        if (!pdf.exists()) {
            System.err.println("File '" + pdf.getCanonicalPath() + "' not exists!");
            return;
        }

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

        PdfWriter writer = new PdfWriter(bbb + "/" + 1 + ".pdf", new WriterProperties().setFullCompressionMode(true));
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(fileName), writer);
        int numberOfPages = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            this.resizePage(bbbDir, pdfDoc, pdfDoc.getPage(i), i);
        }

//        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
//        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
//
//        List<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, Integer>>> toc = new ArrayList<>();
//        toc.add(new AbstractMap.SimpleEntry("a", new AbstractMap.SimpleEntry("aaa", 10)));
//        toc.add(new AbstractMap.SimpleEntry("b", new AbstractMap.SimpleEntry("aaa", 10)));
//        toc.add(new AbstractMap.SimpleEntry("c", new AbstractMap.SimpleEntry("aaa", 10)));
//        toc.add(new AbstractMap.SimpleEntry("d", new AbstractMap.SimpleEntry("aaa", 10)));
//        toc.add(new AbstractMap.SimpleEntry("3", new AbstractMap.SimpleEntry("aaa", 10)));
//
//        // create table of contents
//        int startToc = numberOfPages;
//        Paragraph p = new Paragraph().setFont(bold)
//                .add("Table of Contents").setDestination("toc");
//
//        Document document = new Document(pdfDoc);
//        document.setTextAlignment(TextAlignment.JUSTIFIED)
//                .setHyphenation(new HyphenationConfig("en", "uk", 3, 3))
//                .setFont(font)
//                .setFontSize(11);
//        document.add(p);
//
//
//        toc.remove(0);
//        List<TabStop> tabstops = new ArrayList();
//        tabstops.add(new TabStop(580, TabAlignment.RIGHT, new DottedLine()));
//        for (AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, Integer>> entry : toc) {
//
//            AbstractMap.SimpleEntry<String, Integer> text = entry.getValue();
//            p = new Paragraph()
//                    .addTabStops(tabstops)
//                    .add(text.getKey())
//                    .add(new Tab())
//                    .add(String.valueOf(text.getValue()))
//                    .setAction(PdfAction.createGoTo(entry.getKey()));
//            document.add(p);
//        }
//        int tocPages = numberOfPages - startToc;
//
//        // reorder pages
//        PdfPage page;
//        for (int i = 0; i <= tocPages; i++) {
//            page = pdfDoc.removePage(startToc + i);
//            pdfDoc.addPage(i + 1, page);
//        }
//
//        // add page labels
//        pdfDoc.getPage(1)
//                .setPageLabel(PageLabelNumberingStyleConstants.LOWERCASE_ROMAN_NUMERALS, null, 1);
//        pdfDoc.getPage(2 + tocPages)
//                .setPageLabel(PageLabelNumberingStyleConstants.DECIMAL_ARABIC_NUMERALS, null, 1);

        pdfDoc.close();
    }

    /**
     * 变更页面大小
     *
     * @param dir
     * @param page
     * @param index
     */
    private void resizePage(File dir, PdfDocument pdfDoc, PdfPage page, int index) throws Exception {
        if (page == null)
            return;

        PdfDictionary dic = page.getPdfObject();

        PdfArray media = dic.getAsArray(PdfName.CropBox);
        if (media == null)
            media = dic.getAsArray(PdfName.MediaBox);

        float h = 780f;
        float f = h / media.getAsNumber(3).floatValue();
        float w = media.getAsNumber(2).floatValue() * f;

        PdfArray crop = new PdfArray();
        crop.add(0, new PdfNumber(0f));
        crop.add(1, new PdfNumber(0f));
//        crop.add(2, new PdfNumber(media.getAsNumber(2).floatValue() * FACTOR));
//        crop.add(3, new PdfNumber(media.getAsNumber(3).floatValue() * FACTOR));
        crop.add(2, new PdfNumber(w));
        crop.add(3, new PdfNumber(h));

        System.out.println("page: " + index + ", size: " + media.get(2) + "x" + media.get(3) + " => " + crop.get(2) + "x" + crop.get(3)); // ++++++++++

        page.put(PdfName.MediaBox, crop);
        page.put(PdfName.CropBox, crop);

        new PdfCanvas(page.newContentStreamBefore(), new PdfResources(), pdfDoc)
                .writeLiteral(String.format("\nq %s 0 0 %s 0 0 cm\nq\n", f, f));
        new PdfCanvas(page.newContentStreamAfter(), new PdfResources(), pdfDoc)
                .writeLiteral("\nQ\nQ\n");

        PdfDictionary resources = dic.getAsDictionary(PdfName.Resources);
        PdfDictionary xobjects = resources.getAsDictionary(PdfName.XObject);
        int i = 0;
        for (Iterator<PdfName> it = xobjects.keySet().iterator(); it.hasNext(); ) {
            this.resizeImage(dir, xobjects.getAsStream(it.next()), index, ++i);
        }
    }

    /**
     * @param dir
     * @param stream
     * @param pageIdx
     * @param objIdx
     * @throws IOException
     */
    private void resizeImage(File dir, PdfStream stream, int pageIdx, int objIdx) throws Exception {
        PdfImageXObject image = new PdfImageXObject(stream);
        BufferedImage bi = image.getBufferedImage();
        if (bi == null)
            return;


        float scale = FACTOR + 0.1f;
        int width = (int) (bi.getWidth() * scale);
        int height = (int) (bi.getHeight() * scale);

//        int width = 748;
//        float scale = (float) width / (float) bi.getWidth();
//        int height = (int) (bi.getHeight() * scale);

//        int width = bi.getWidth();
//        int height = bi.getHeight();
//        float scale = 1f;
        if (width <= 0 || height <= 0)
            return;

        System.out.println("\timage: " + objIdx + ", size: " + bi.getWidth() + "x" + bi.getHeight() + " => " + width + "x" + height); // ++++++++++

//        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);
//        AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
//        Graphics2D g = newImage.createGraphics();
//        g.drawRenderedImage(bi, at);
//        ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
//        ImageIO.write(newImage, "png", imgBytes);
//
//        Image img = new Image(ImageDataFactory.create(imgBytes.toByteArray()));
//        replaceStream(stream, img.getXObject().getPdfObject());


        ImageType imageType = image.identifyImageType();
        File imgFile = new File(dir.getCanonicalPath() + "/" + pageIdx + "_" + objIdx + "." + image.identifyImageFileExtension());
        ImageIO.write(bi, imageType.name().toString(), imgFile);

        File reduceImgFile = new File(dir.getCanonicalPath() + "/" + pageIdx + "_" + objIdx + "_reduce." + image.identifyImageFileExtension());
//        Thumbnails.of(imgFile).scale(FACTOR).toFile(reduceImgFile);
        ImageUtils.resizePng(imgFile, reduceImgFile, bi.getWidth() * scale, 0, 60);

        Image img = new Image(makeImage(reduceImgFile.getCanonicalPath()));
        replaceStream(stream, img.getXObject().getPdfObject());

    }

    /**
     * @param orig
     * @param stream
     * @throws IOException
     */
    private void replaceStream(PdfStream orig, PdfStream stream) throws IOException {
        orig.clear();
        orig.setData(stream.getBytes());
        for (PdfName name : stream.keySet()) {
            orig.put(name, stream.get(name));
        }
    }

    /**
     * @param image
     * @return
     * @throws IOException
     */
    private ImageData makeImage(String image) throws IOException {
        BufferedImage bi = ImageIO.read(new File(image));
        System.out.println("Image name : " + image + ", size: " + bi.getWidth() + ", " + bi.getHeight()); //++++++++++++++
        BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        newBi.getGraphics().drawImage(bi, 0, 0, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBi, "png", baos);

        return ImageDataFactory.create(baos.toByteArray());
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


    public static void main(String[] args) throws Exception {
        new PdfConverter().run(args);

        System.exit(0);
    }
}
