package be.ovam.art46.util;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import java.io.*;
import java.util.List;

/**
 * Created by Koen on 25/04/2014.
 */
public class PdfMerger {
    
    public static void merge(InputStream source1, InputStream source2, OutputStream output) {
        try {
            PDFMergerUtility merger = new PDFMergerUtility();
            merger.addSource(source1);
            merger.addSource(source2);
            OutputStream bufferedOutputStream = new BufferedOutputStream(output);
            merger.setDestinationStream(bufferedOutputStream);
            merger.mergeDocuments();
        } catch (COSVisitorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
