package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.dao.MeetstaatEenheidDao;
import be.ovam.art46.dao.MeetstaatEenheidMappingDao;
import be.ovam.art46.model.*;
import be.ovam.art46.service.BestekService;
import be.ovam.art46.service.meetstaat.MeetstaatExportService;
import be.ovam.art46.service.meetstaat.MeetstaatService;
import be.ovam.art46.service.meetstaat.response.ResponseReadMeetstaatCSV;
import be.ovam.art46.service.mock.MeetstaatMock;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.GenericRegel;
import be.ovam.pad.model.MeetstaatRegel;

import com.itextpdf.text.DocumentException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koen Corstjens on 30-8-13.
 */
public abstract class MeetstaatExportServiceTest {
    protected MeetstaatMock meetstaatMock;
    protected MeetstaatService meetstaatService;
    protected MeetstaatExportService meetstaatExportService;
    protected BestekService bestekService;
    protected MeetstaatEenhedenServiceImpl meetstaatEenhedenService;

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    protected File createdFile;

    @Before
    public void setup() throws IOException {
        setupExport();
        meetstaatMock = new MeetstaatMock();        
        meetstaatService = Mockito.mock(MeetstaatService.class);
        bestekService = Mockito.mock(BestekService.class);
        meetstaatExportService.setMeetstaatService(meetstaatService);
        meetstaatExportService.setBestekService(bestekService);
        meetstaatEenhedenService = new MeetstaatEenhedenServiceImpl();
        MeetstaatEenheidDao meetstaatEenheidDao = Mockito.mock(MeetstaatEenheidDao.class);
        MeetstaatEenheidMappingDao meetstaatEenheidMappingDao = Mockito.mock(MeetstaatEenheidMappingDao.class);
        Mockito.when(meetstaatEenheidDao.getAll()).thenReturn(new ArrayList<MeetstaatEenheid>());
        Mockito.when(meetstaatEenheidMappingDao.getAll()).thenReturn(new ArrayList<MeetstaatEenheidMapping>());
        meetstaatEenhedenService.setMeetstaatEenheidMappingDao(meetstaatEenheidMappingDao);
        meetstaatEenhedenService.setMeetstaatEenheidDao(meetstaatEenheidDao);
    }

    protected abstract void setupExport() throws IOException;

    public void create() throws FileNotFoundException {
        List<MeetstaatRegel> meetstaatRegelList = meetstaatMock.getMeetstaatRegels();
        Mockito.when(meetstaatService.getAll(2l)).thenReturn(meetstaatRegelList);
        Bestek bestek = new Bestek();
        bestek.setBestek_nr("testbestekNr");
        bestek.setOmschrijving("omschrijving bestek");
        Mockito.when(bestekService.getBestek(2l)).thenReturn(bestek);
        FileOutputStream outputStream = new FileOutputStream(createdFile);
        meetstaatExportService.createFinalExport(2l, outputStream);
    }

    public void create2() throws DocumentException, FileNotFoundException {
        File file = FileUtils.toFile(this.getClass().getResource("/MeetstaatStandaardLeeg.csv"));
        Reader reader = new FileReader(file);
        List<GenericRegel> meetstaatRegelList = new ArrayList<GenericRegel>();
        try{
            MeetstaatServiceImpl meetstaatServiceImpl = new MeetstaatServiceImpl();
            BestekService bestekService = Mockito.mock(BestekService.class);
            Mockito.when(bestekService.getBestek(Mockito.anyLong())).thenReturn(new Bestek());
            meetstaatServiceImpl.setBestekService(bestekService);
            meetstaatServiceImpl.setMeetstaatEenheidService(meetstaatEenhedenService);

            meetstaatServiceImpl.setMeetstaatEenheidService(meetstaatEenhedenService);
            Bestek bestek = new Bestek();
            bestek.setBestek_id(121l);
            ResponseReadMeetstaatCSV responseReadMeetstaatCSV = meetstaatServiceImpl.readMeetstaatCSV(reader, bestek);
            List<MeetstaatRegel> meetstaatRegels = responseReadMeetstaatCSV.getMeetstaatRegels();
            for(MeetstaatRegel meetstaatRegel : meetstaatRegels){
                meetstaatRegelList.add(meetstaatRegel);
            }
        }catch (Exception e){
            Assert.fail("exception:" + e + e.getStackTrace());
        }

        FileOutputStream outputStream = new FileOutputStream(createdFile);
        meetstaatExportService.createMeetstaatExport("SV100505 Afvalverwijdering ", outputStream, meetstaatRegelList, new BigDecimal(21), true);
        System.out.println(folder.getRoot());
    }
}
