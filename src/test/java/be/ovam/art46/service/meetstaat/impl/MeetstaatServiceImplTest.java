package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.dao.MeetstaatEenheidDao;
import be.ovam.art46.dao.MeetstaatEenheidMappingDao;
import be.ovam.art46.model.MeetstaatEenheid;
import be.ovam.art46.model.MeetstaatEenheidMapping;
import be.ovam.art46.service.BestekService;
import be.ovam.art46.service.mock.MeetstaatMock;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.MeetstaatRegel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by koencorstjens on 29-7-13.
 */
public class MeetstaatServiceImplTest {
    MeetstaatServiceImpl meetstaatService;
    MeetstaatMock meetstaatMock;
    BestekService bestekService;
    MeetstaatEenheidDao meetstaatEenheidDao;
    MeetstaatEenheidMappingDao meetstaatEenheidMappingDao;

    private Bestek bestek;

    @Before
    public void setUp(){
        meetstaatService = new MeetstaatServiceImpl();
        meetstaatMock = new MeetstaatMock();
        MeetstaatEenhedenServiceImpl meetstaatEenhedenService = new MeetstaatEenhedenServiceImpl();
        meetstaatEenheidDao = Mockito.mock(MeetstaatEenheidDao.class);
        Mockito.when(meetstaatEenheidDao.getAll()).thenReturn(new ArrayList<MeetstaatEenheid>());
        meetstaatEenheidMappingDao = Mockito.mock(MeetstaatEenheidMappingDao.class);
        meetstaatEenhedenService.setMeetstaatEenheidDao(meetstaatEenheidDao);
        meetstaatEenhedenService.setMeetstaatEenheidMappingDao(meetstaatEenheidMappingDao);
        meetstaatService.setMeetstaatEenheidService(meetstaatEenhedenService);
        bestekService = Mockito.mock(BestekService.class);
        bestek = new Bestek();
        bestek.setBtw_tarief(new BigDecimal(21));
        Mockito.when(bestekService.getBestek(12l)).thenReturn(bestek);
        meetstaatService.setBestekService(bestekService);
    }

    @Test
    public void readMeetstaatCSVMeetstaatStandaardLeeg1() throws IOException {
        Mockito.when(meetstaatEenheidDao.getAll()).thenReturn(new ArrayList<MeetstaatEenheid>());
        Mockito.when(meetstaatEenheidMappingDao.getAll()).thenReturn(new ArrayList<MeetstaatEenheidMapping>());
        File file = FileUtils.toFile(this.getClass().getResource("/MeetstaatStandaardLeeg.csv"));
        Reader reader = new FileReader(file);
        List<MeetstaatRegel> meetstaatRegelList = null;
        try{
          meetstaatRegelList = meetstaatService.readMeetstaatCSV(reader, bestek).getMeetstaatRegels();
        }catch (Exception e){
            Assert.fail("exception:" + e + e.getStackTrace());
        }
        Assert.assertEquals("1", meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals("7.2.9.1", meetstaatRegelList.get(143).getPostnr());
        Assert.assertEquals("7.2.9.2", meetstaatRegelList.get(144).getPostnr());
        Assert.assertEquals(165, meetstaatRegelList.size());
    }

    @Test
    public void readMeetstaatCSVMeetstaatStandaardLeeg2() throws IOException {
        File file = FileUtils.toFile(this.getClass().getResource("/MeetstaatStandaardLeeg.csv"));
        Reader reader = new FileReader(file);
        List<MeetstaatRegel> meetstaatRegelList = null;
        try{
            meetstaatRegelList = meetstaatService.readMeetstaatCSV(reader, bestek).getMeetstaatRegels();
        }catch (Exception e){
            Assert.fail("exception:" + e + e.getStackTrace());
        }
        for (MeetstaatRegel meetstaatRegel : meetstaatRegelList){
            Assert.assertTrue(StringUtils.isNotEmpty(meetstaatRegel.getPostnr()));
        }
    }

    @Test
    public void readWithCsvListReaderMeetstaatStandaardIngevuld() throws IOException {
        File file = FileUtils.toFile(this.getClass().getResource("/MeetstaatStandaardIngevuld.csv"));
        Reader reader = new FileReader(file);
        List<MeetstaatRegel> meetstaatRegelList = null;
        try{
            meetstaatRegelList = meetstaatService.readMeetstaatCSV(reader, bestek).getMeetstaatRegels();
        }catch (Exception e){
            Assert.fail("exception:" + e + e.getStackTrace());
        }
        Assert.assertEquals("1", meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals(214, meetstaatRegelList.size());
        Assert.assertEquals("7.2.2.6", meetstaatRegelList.get(143).getPostnr());
        Assert.assertEquals(".t", meetstaatRegelList.get(212).getPostnr());
        Assert.assertEquals(new BigDecimal(167616.4).round(new MathContext(1, RoundingMode.HALF_UP)), meetstaatRegelList.get(212).getPostTotaal().round(new MathContext(1, RoundingMode.HALF_UP)));
        Assert.assertEquals(".tbtw", meetstaatRegelList.get(213).getPostnr());
        Assert.assertEquals(new BigDecimal(202815.84).round(new MathContext(2, RoundingMode.HALF_UP)), meetstaatRegelList.get(213).getPostTotaal().round(new MathContext(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void readWithCsvListReaderMeetstaatZonderDetails() throws IOException {
        File file = FileUtils.toFile(this.getClass().getResource("/MeetstaatZonderDetails.csv"));
        Reader reader = new FileReader(file);
        List<MeetstaatRegel> meetstaatRegelList = null;
        try{
            meetstaatRegelList = meetstaatService.readMeetstaatCSV(reader, bestek).getMeetstaatRegels();
        }catch (Exception e){
            Assert.fail("exception:" + e + e.getStackTrace());
        }
    }

    @Test
    public void sorteer(){
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "4", null);
        addToList(meetstaatRegelList, "2.2", null);
        addToList(meetstaatRegelList, "2.1", null);
        addToList(meetstaatRegelList, "1.1", null);
        addToList(meetstaatRegelList, "3.1.1", null);

        meetstaatService.sort(meetstaatRegelList);

        Assert.assertEquals("1.1", meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals("2.1", meetstaatRegelList.get(1).getPostnr());
        Assert.assertEquals("2.2", meetstaatRegelList.get(2).getPostnr());
        Assert.assertEquals("3.1.1", meetstaatRegelList.get(3).getPostnr());
        Assert.assertEquals("4", meetstaatRegelList.get(4).getPostnr());
    }

    @Test
    public void bereken(){
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "10.1", null);
        addToList(meetstaatRegelList, "4", null);
        addToList(meetstaatRegelList, "4.1", null);
        addToList(meetstaatRegelList, "4.1.1",null, new BigDecimal(1), new BigDecimal(1));
        addToList(meetstaatRegelList, "4.1.2",null, new BigDecimal(1), new BigDecimal(1));
        addToList(meetstaatRegelList, "2.2", null);
        addToList(meetstaatRegelList, "2.1", null);
        addToList(meetstaatRegelList, "1.1", null, new BigDecimal(1) , new BigDecimal(1));
        addToList(meetstaatRegelList, "3.1.1", null);
        addToList(meetstaatRegelList, "10", null);

        meetstaatRegelList = meetstaatService.bereken(meetstaatRegelList);

        Assert.assertEquals("1.1",meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals("1.t",meetstaatRegelList.get(1).getPostnr());        
        Assert.assertEquals("2.1",meetstaatRegelList.get(2).getPostnr());
        Assert.assertEquals("2.2",meetstaatRegelList.get(3).getPostnr());
        Assert.assertEquals("3.1.1", meetstaatRegelList.get(4).getPostnr());
        Assert.assertEquals("4",meetstaatRegelList.get(5).getPostnr());
        Assert.assertEquals("4.1",meetstaatRegelList.get(6).getPostnr());
        Assert.assertEquals("4.1.1",meetstaatRegelList.get(7).getPostnr());
        Assert.assertEquals("4.1.2",meetstaatRegelList.get(8).getPostnr());
        Assert.assertEquals("4.1.t",meetstaatRegelList.get(9).getPostnr());
        Assert.assertEquals("4.t",meetstaatRegelList.get(10).getPostnr());
        Assert.assertEquals("10",meetstaatRegelList.get(11).getPostnr());
        Assert.assertEquals("10.1",meetstaatRegelList.get(12).getPostnr());
        Assert.assertEquals(".t",meetstaatRegelList.get(13).getPostnr());
        Assert.assertEquals(".tbtw",meetstaatRegelList.get(14).getPostnr());
        Assert.assertEquals(15, meetstaatRegelList.size());
        Assert.assertEquals(new BigDecimal(3.0) ,meetstaatRegelList.get(13).getPostTotaal());
        Assert.assertEquals(new BigDecimal(3.63).round(new MathContext(2, RoundingMode.HALF_UP)) ,meetstaatRegelList.get(14).getPostTotaal().round(new MathContext(2, RoundingMode.HALF_UP)));
        
        
        
//        Assert.assertEquals("2.1",meetstaatRegelList.get(1).getPostnr());
//        Assert.assertEquals("2.2",meetstaatRegelList.get(2).getPostnr());
//        Assert.assertEquals("3.1.1", meetstaatRegelList.get(3).getPostnr());
//        Assert.assertEquals("4",meetstaatRegelList.get(4).getPostnr());
//        Assert.assertEquals("4.1",meetstaatRegelList.get(5).getPostnr());
//        Assert.assertEquals("4.1.1",meetstaatRegelList.get(6).getPostnr());
//        Assert.assertEquals("4.1.2",meetstaatRegelList.get(7).getPostnr());
//        Assert.assertEquals("4.1.t",meetstaatRegelList.get(8).getPostnr());
//        Assert.assertEquals("4.t",meetstaatRegelList.get(9).getPostnr());
//        Assert.assertEquals("10",meetstaatRegelList.get(10).getPostnr());
//        Assert.assertEquals("10.1",meetstaatRegelList.get(11).getPostnr());
//        Assert.assertEquals(".t",meetstaatRegelList.get(12).getPostnr());
//        Assert.assertEquals(".tbtw",meetstaatRegelList.get(13).getPostnr());
//        Assert.assertEquals(14, meetstaatRegelList.size());
//        Assert.assertEquals(new BigDecimal(3.0) ,meetstaatRegelList.get(12).getPostTotaal());
//        Assert.assertEquals(new BigDecimal(3.63).round(new MathContext(2, RoundingMode.HALF_UP)) ,meetstaatRegelList.get(13).getPostTotaal().round(new MathContext(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void completeMeetstaatRegels2(){
        List<MeetstaatRegel> meetstaatRegelList = meetstaatMock.getMeetstaatRegels();
        MeetstaatRegel meetstaatRegelLeeg1 = new MeetstaatRegel();
        meetstaatRegelLeeg1.setTaak("Test taak");
        meetstaatRegelLeeg1.setDetails("beschrijving");
        meetstaatRegelLeeg1.setEenheidsprijs(new BigDecimal(1));
        meetstaatRegelLeeg1.setAantal(new BigDecimal(1));
        meetstaatRegelList.add(meetstaatRegelLeeg1);

        MeetstaatRegel meetstaatRegelLeeg2 = new MeetstaatRegel();
        meetstaatRegelLeeg2.setDetails("beschrijving");
        meetstaatRegelLeeg2.setEenheidsprijs(new BigDecimal(1));
        meetstaatRegelLeeg2.setAantal(new BigDecimal(1));
        meetstaatRegelList.add(meetstaatRegelLeeg2);

        meetstaatService.completeMeetstaatRegels(meetstaatRegelList);
        meetstaatService.sort(meetstaatRegelList);
        Assert.assertEquals("10.1",meetstaatRegelList.get(9).getPostnr());
        Assert.assertEquals("Test taak",meetstaatRegelList.get(11).getTaak());
        Assert.assertEquals("40",meetstaatRegelList.get(10).getPostnr());
        Assert.assertEquals("40.1",meetstaatRegelList.get(11).getPostnr());
        Assert.assertEquals("40.1.1",meetstaatRegelList.get(12).getPostnr());
    }


    @Test
    public void addNewMeetstaat(){
        List<MeetstaatRegel> meetstaatRegelList = meetstaatMock.getMeetstaatRegels();
        meetstaatRegelList = meetstaatService.toevoegenOnbrekendeRegels(meetstaatRegelList, bestek);
        MeetstaatRegel newMeetstaatRegel = new MeetstaatRegel();
        newMeetstaatRegel.setPostnr("4");
        newMeetstaatRegel.setTaak("NewMeetstaat");
        newMeetstaatRegel.setCrudStatus("C");

        List<MeetstaatRegel> result = meetstaatService.addNewMeetstaat(newMeetstaatRegel, meetstaatRegelList, new BigDecimal(19));
        Assert.assertEquals("4",result.get(9).getPostnr());
        Assert.assertEquals("NewMeetstaat",result.get(9).getTaak());
        Assert.assertEquals("5.1.t",result.get(14).getPostnr());
    }

    @Test
    public void toevoegenOnbrekendeRegels1(){
        List <MeetstaatRegel> meetstaatRegels = new ArrayList<MeetstaatRegel>() ;
        addToList(meetstaatRegels, "3", null);
        meetstaatRegels = meetstaatService.toevoegenOnbrekendeRegels( meetstaatRegels,bestek);
        Assert.assertEquals("1",meetstaatRegels.get(0).getPostnr());
        Assert.assertEquals("2",meetstaatRegels.get(1).getPostnr());
        Assert.assertEquals("3",meetstaatRegels.get(2).getPostnr());
        Assert.assertEquals(3,meetstaatRegels.size());
    }

    @Test
    public void toevoegenOnbrekendeRegels2(){
        List <MeetstaatRegel> meetstaatRegels = new ArrayList<MeetstaatRegel>() ;
        addToList(meetstaatRegels, "1", null);
        addToList(meetstaatRegels, "3", null);
        meetstaatRegels = meetstaatService.toevoegenOnbrekendeRegels( meetstaatRegels, bestek);
        Assert.assertEquals(3,meetstaatRegels.size());
        Assert.assertEquals("1",meetstaatRegels.get(0).getPostnr());
        Assert.assertEquals("2",meetstaatRegels.get(1).getPostnr());
        Assert.assertEquals("3",meetstaatRegels.get(2).getPostnr());
    }

    @Test
    public void toevoegenOnbrekendeRegels3(){
        List <MeetstaatRegel> meetstaatRegels = new ArrayList<MeetstaatRegel>() ;
        addToList(meetstaatRegels, "1", null);
        addToList(meetstaatRegels, "6.5", null);

        meetstaatRegels = meetstaatService.toevoegenOnbrekendeRegels( meetstaatRegels, bestek);
        Assert.assertEquals(11,meetstaatRegels.size());
        Assert.assertEquals("1",meetstaatRegels.get(0).getPostnr());
        Assert.assertEquals("2",meetstaatRegels.get(1).getPostnr());
        Assert.assertEquals("3",meetstaatRegels.get(2).getPostnr());
        Assert.assertEquals("6.5",meetstaatRegels.get(10).getPostnr());
    }

    @Test
    public void cleanUpMeetstaat(){
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "7.2", null);
        addToList(meetstaatRegelList, "7.1", null);

        meetstaatRegelList = meetstaatService.toevoegenOnbrekendeRegels(meetstaatRegelList,bestek);
        System.out.println("remove:->"+meetstaatRegelList.get(6).getPostnr());
        meetstaatRegelList.remove(5);
        System.out.println("Invoer:");
        printMeetstaatRegels(meetstaatRegelList);
        List<MeetstaatRegel> result = meetstaatService.herbereken(meetstaatRegelList, new BigDecimal(19));
        Assert.assertEquals("1",result.get(0).getPostnr());
        Assert.assertEquals("2",result.get(1).getPostnr());
        Assert.assertEquals("3",result.get(2).getPostnr());
        Assert.assertEquals("4",result.get(3).getPostnr());
        Assert.assertEquals("5",result.get(4).getPostnr());
        Assert.assertEquals("6",result.get(5).getPostnr());
        Assert.assertEquals("6.1",result.get(6).getPostnr());
        Assert.assertEquals("6.2",result.get(7).getPostnr());
        System.out.println("Results:");
        printMeetstaatRegels(meetstaatRegelList);
    }

    @Test
    public void replaceGroup(){
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "1", "Taak van postNr 1");
        addToList(meetstaatRegelList, "7.2", null);
        addToList(meetstaatRegelList, "7.1", null);
        addToList(meetstaatRegelList, "8.1", null);
        meetstaatRegelList = meetstaatService.toevoegenOnbrekendeRegels(meetstaatRegelList,bestek);
        printMeetstaatRegels(meetstaatRegelList);
        meetstaatRegelList = meetstaatService.replace(meetstaatRegelList, "7", "1", null);
        System.out.println("Resultaat:");
        printMeetstaatRegels(meetstaatRegelList);
        Assert.assertEquals("1", meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals("1.1",meetstaatRegelList.get(1).getPostnr());
        Assert.assertEquals("1.2",meetstaatRegelList.get(2).getPostnr());
        Assert.assertEquals("2",meetstaatRegelList.get(3).getPostnr());
        Assert.assertEquals("Taak van postNr 1",meetstaatRegelList.get(3).getTaak());
        Assert.assertEquals("3",meetstaatRegelList.get(4).getPostnr());
        Assert.assertEquals("4",meetstaatRegelList.get(5).getPostnr());
        Assert.assertEquals("5",meetstaatRegelList.get(6).getPostnr());
        Assert.assertEquals("6",meetstaatRegelList.get(7).getPostnr());
        Assert.assertEquals("7",meetstaatRegelList.get(8).getPostnr());
        Assert.assertEquals("8",meetstaatRegelList.get(9).getPostnr());
        Assert.assertEquals("8.1",meetstaatRegelList.get(10).getPostnr());
    }

    @Test
    public void replaceGroup1(){
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "1", "Taak van postNr 1");
        addToList(meetstaatRegelList, "1.2", "Taak van postNr 1.2");
        addToList(meetstaatRegelList, "7.2", null);
        addToList(meetstaatRegelList, "7.1", null);
        addToList(meetstaatRegelList, "8.1", null);
        meetstaatRegelList = meetstaatService.toevoegenOnbrekendeRegels(meetstaatRegelList,bestek);
        printMeetstaatRegels(meetstaatRegelList);
        meetstaatRegelList = meetstaatService.replace(meetstaatRegelList, "7", "1", null);
        System.out.println("Resultaat:");
        printMeetstaatRegels(meetstaatRegelList);
        Assert.assertEquals("1",meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals("1.1",meetstaatRegelList.get(1).getPostnr());
        Assert.assertEquals("1.2",meetstaatRegelList.get(2).getPostnr());
        Assert.assertEquals("2",meetstaatRegelList.get(3).getPostnr());
        Assert.assertEquals("Taak van postNr 1",meetstaatRegelList.get(3).getTaak());
        Assert.assertEquals("2.1",meetstaatRegelList.get(4).getPostnr());
        Assert.assertEquals("2.2",meetstaatRegelList.get(5).getPostnr());
        Assert.assertEquals("Taak van postNr 1.2",meetstaatRegelList.get(5).getTaak());
        Assert.assertEquals("3",meetstaatRegelList.get(6).getPostnr());
        Assert.assertEquals("4",meetstaatRegelList.get(7).getPostnr());
        Assert.assertEquals("5",meetstaatRegelList.get(8).getPostnr());
        Assert.assertEquals("6",meetstaatRegelList.get(9).getPostnr());
        Assert.assertEquals("7",meetstaatRegelList.get(10).getPostnr());
        Assert.assertEquals("8",meetstaatRegelList.get(11).getPostnr());
        Assert.assertEquals("8.1",meetstaatRegelList.get(12).getPostnr());
    }

    @Test
    public void replaceGroup3(){
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "1", "Taak van postNr 1");
        addToList(meetstaatRegelList, "1.2", "Taak van postNr 1.2");
        addToList(meetstaatRegelList, "7.2", "Taak van postNr 7.2");
        addToList(meetstaatRegelList, "7.1", "Taak van postNr 7.1");
        addToList(meetstaatRegelList, "8.1", "Taak van postNr 8.1");

        meetstaatRegelList = meetstaatService.toevoegenOnbrekendeRegels(meetstaatRegelList,bestek);
        printMeetstaatRegels(meetstaatRegelList);
        meetstaatRegelList = meetstaatService.replace(meetstaatRegelList, "7", "3.1", null);
        System.out.println("Resultaat:");
        printMeetstaatRegels(meetstaatRegelList);
        Assert.assertEquals("1",meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals("Taak van postNr 1",meetstaatRegelList.get(0).getTaak());

        Assert.assertEquals("1.1",meetstaatRegelList.get(1).getPostnr());
        Assert.assertEquals("1.2",meetstaatRegelList.get(2).getPostnr());
        Assert.assertEquals("Taak van postNr 1.2",meetstaatRegelList.get(2).getTaak());

        Assert.assertEquals("2",meetstaatRegelList.get(3).getPostnr());
        Assert.assertEquals("3",meetstaatRegelList.get(4).getPostnr());
        Assert.assertEquals("3.1",meetstaatRegelList.get(5).getPostnr());
        Assert.assertEquals("3.1.1",meetstaatRegelList.get(6).getPostnr());
        Assert.assertEquals("3.1.2",meetstaatRegelList.get(7).getPostnr());
        Assert.assertEquals("4",meetstaatRegelList.get(8).getPostnr());
        Assert.assertEquals("5",meetstaatRegelList.get(9).getPostnr());
        Assert.assertEquals("6",meetstaatRegelList.get(10).getPostnr());
        Assert.assertEquals("7",meetstaatRegelList.get(11).getPostnr());
        Assert.assertEquals("7.1",meetstaatRegelList.get(12).getPostnr());
    }

    @Test
    public void replaceGroup4(){
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "1", "Taak van postNr 1");
        addToList(meetstaatRegelList, "1.2", "Taak van postNr 1.2");
        addToList(meetstaatRegelList, "7.2", "Taak van postNr 7.2");
        addToList(meetstaatRegelList, "7.1", "Taak van postNr 7.1");
        addToList(meetstaatRegelList, "8.1", "Taak van postNr 8.1");

        meetstaatRegelList = meetstaatService.toevoegenOnbrekendeRegels(meetstaatRegelList,bestek);
        printMeetstaatRegels(meetstaatRegelList);
        meetstaatRegelList = meetstaatService.replace(meetstaatRegelList, "7", "100", null);
        System.out.println("Resultaat:");
        printMeetstaatRegels(meetstaatRegelList);
        Assert.assertEquals("1",meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals("Taak van postNr 1",meetstaatRegelList.get(0).getTaak());
        Assert.assertEquals("1.1",meetstaatRegelList.get(1).getPostnr());
        Assert.assertEquals("1.2",meetstaatRegelList.get(2).getPostnr());
        Assert.assertEquals("Taak van postNr 1.2",meetstaatRegelList.get(2).getTaak());
        Assert.assertEquals("2",meetstaatRegelList.get(3).getPostnr());
        Assert.assertEquals("3",meetstaatRegelList.get(4).getPostnr());
        Assert.assertEquals("4",meetstaatRegelList.get(5).getPostnr());
        Assert.assertEquals("5",meetstaatRegelList.get(6).getPostnr());
        Assert.assertEquals("6",meetstaatRegelList.get(7).getPostnr());
        Assert.assertEquals("7",meetstaatRegelList.get(8).getPostnr());
        Assert.assertEquals("7.1",meetstaatRegelList.get(9).getPostnr());
        Assert.assertEquals("8",meetstaatRegelList.get(10).getPostnr());
        Assert.assertEquals("8.1",meetstaatRegelList.get(11).getPostnr());
        Assert.assertEquals("8.2",meetstaatRegelList.get(12).getPostnr());
    }
    @Test
    public void replaceGroup5(){
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "1", "Taak van postNr 1");
        addToList(meetstaatRegelList, "1.2", "Taak van postNr 1.2");
        addToList(meetstaatRegelList, "7.2", "Taak van postNr 7.2");
        addToList(meetstaatRegelList, "7.1", "Taak van postNr 7.1");
        addToList(meetstaatRegelList, "7", "Taak van postNr 7");
        addToList(meetstaatRegelList, "8.1", "Taak van postNr 8.1");

        meetstaatRegelList = meetstaatService.toevoegenOnbrekendeRegels(meetstaatRegelList,bestek);
        printMeetstaatRegels(meetstaatRegelList);
        meetstaatRegelList = meetstaatService.replace(meetstaatRegelList, "7", "5.1.5", null);
        System.out.println("Resultaat:");
        printMeetstaatRegels(meetstaatRegelList);
        Assert.assertEquals(13, meetstaatRegelList.size());
        Assert.assertEquals("1",meetstaatRegelList.get(0).getPostnr());
        Assert.assertEquals("Taak van postNr 1",meetstaatRegelList.get(0).getTaak());
        Assert.assertEquals("1.1",meetstaatRegelList.get(1).getPostnr());
        Assert.assertEquals("1.2",meetstaatRegelList.get(2).getPostnr());
        Assert.assertEquals("Taak van postNr 1.2",meetstaatRegelList.get(2).getTaak());
        Assert.assertEquals("2",meetstaatRegelList.get(3).getPostnr());
        Assert.assertEquals("3",meetstaatRegelList.get(4).getPostnr());
        Assert.assertEquals("4",meetstaatRegelList.get(5).getPostnr());
        Assert.assertEquals("5",meetstaatRegelList.get(6).getPostnr());
        Assert.assertEquals("5.1.1",meetstaatRegelList.get(7).getPostnr());
        Assert.assertEquals("Taak van postNr 7",meetstaatRegelList.get(7).getTaak());
        Assert.assertEquals("5.1.1.1",meetstaatRegelList.get(8).getPostnr());
        Assert.assertEquals("Taak van postNr 7.1",meetstaatRegelList.get(8).getTaak());
        Assert.assertEquals("5.1.1.2",meetstaatRegelList.get(9).getPostnr());
        Assert.assertEquals("Taak van postNr 7.2",meetstaatRegelList.get(9).getTaak());
        Assert.assertEquals("6",meetstaatRegelList.get(10).getPostnr());
        Assert.assertEquals("7",meetstaatRegelList.get(11).getPostnr());
        Assert.assertEquals("7.1",meetstaatRegelList.get(12).getPostnr());
    }

    @Test
    public void replaceGroup6(){

        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        addToList(meetstaatRegelList, "1", "Taak van postNr 1");
        addToList(meetstaatRegelList, "2", "Taak van postNr 2");
        addToList(meetstaatRegelList, "3", "Taak van postNr 3");
        addToList(meetstaatRegelList, "3.1", "Taak van postNr 3.1");
        addToList(meetstaatRegelList, "4", "Taak van postNr 4");
        addToList(meetstaatRegelList, "5", "Taak van postNr 5");
        addToList(meetstaatRegelList, "5.1", "Taak van postNr 5.1");
        addToList(meetstaatRegelList, "6", "Taak van postNr 6");

        meetstaatRegelList = meetstaatService.toevoegenOnbrekendeRegels(meetstaatRegelList,bestek);
        printMeetstaatRegels(meetstaatRegelList);
        meetstaatRegelList = meetstaatService.replace(meetstaatRegelList, "5", "3", null);
        System.out.println("Resultaat:");
        printMeetstaatRegels(meetstaatRegelList);
    }


    private void addToList(List<MeetstaatRegel> meetstaatRegelList, String postnr, String taak, BigDecimal eenheidsprijs, BigDecimal aantal) {
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr(postnr);
        meetstaatRegel.setTaak(taak);
        if(eenheidsprijs!=null){
            meetstaatRegel.setType("VH");
        }
        meetstaatRegel.setEenheidsprijs(eenheidsprijs);
        meetstaatRegel.setAantal(aantal);
        meetstaatRegelList.add(meetstaatRegel);
    }

    private void addToList(List<MeetstaatRegel> meetstaatRegelList, String postnr, String taak) {
        addToList(meetstaatRegelList, postnr, taak, null, null);
    }

    private void printMeetstaatRegels(List<MeetstaatRegel> meetstaatRegelList) {
        for(MeetstaatRegel meetstaatRegel : meetstaatRegelList ){
            System.out.println(meetstaatRegel.getPostnr()+": "+meetstaatRegel.getTaak());
        }
    }
}
