package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.service.meetstaat.MeetstaatExportPdfService;
import com.itextpdf.text.DocumentException;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by koencorstjens on 14-8-13.
 */

public class MeetstaatExportPdfServiceTest extends MeetstaatExportServiceTest {

    private MeetstaatExportPdfService meetstaatExportPdfService;

    @Override
    protected void setupExport() throws IOException {
        meetstaatExportPdfService = new MeetstaatExportPdfServiceImpl();
        meetstaatExportService = meetstaatExportPdfService;
        createdFile = folder.newFile("myfile.pdf");
    }

    @Test
    public void createPdf() {
        try{
            super.create();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void createPdf2() {
        try{
            super.create2();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            Assert.fail();
        } catch (DocumentException e){
            e.printStackTrace();
            Assert.fail();
        }
    }
}
