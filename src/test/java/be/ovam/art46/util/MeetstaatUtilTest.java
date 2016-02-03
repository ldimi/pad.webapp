package be.ovam.art46.util;

import be.ovam.pad.util.MeetstaatUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by koencorstjens on 2-8-13.
 */
public class MeetstaatUtilTest {

    @Test
    public void parseToDouble1(){
        Assert.assertEquals(100000.00, MeetstaatUtil.parseToDouble("100.000,00 €"),0);
    }

    @Test
    public void parseToDouble2(){
        Assert.assertEquals(5.0, MeetstaatUtil.parseToDouble("5,0"),0);
    }

    @Test
    public void parseToDouble3(){
        Assert.assertEquals(1000000000.00, MeetstaatUtil.parseToDouble("1.000.000.000,00 €"),0);
    }

    @Test
    public void parseToDouble4(){
        Assert.assertEquals(15000.00, MeetstaatUtil.parseToDouble("15.000"),0);
    }

    @Test
    public void parseToDouble5(){
        Assert.assertEquals(15.00, MeetstaatUtil.parseToDouble("15.00"),0);
    }

    @Test
    public void isVolgendPostnr(){
        Assert.assertTrue(MeetstaatUtil.isVolgendPostnr("1.1","1.2"));
    }

    @Test
    public void isVolgendPostnrFalse(){
        Assert.assertFalse(MeetstaatUtil.isVolgendPostnr("1.1","1.3"));
    }

    @Test
    public void isVolgendPostnrExtraLevelTrue(){
        Assert.assertTrue(MeetstaatUtil.isVolgendPostnr("1.1","1.1.1"));
    }

    @Test
    public void isVolgendPostnrExtraLevelFalse(){
        Assert.assertFalse(MeetstaatUtil.isVolgendPostnr("1.1","1.1.2"));
    }

    @Test
    public void isVolgendPostnrTrue1(){
        Assert.assertTrue(MeetstaatUtil.isVolgendPostnr("1.1.5.9","2"));
    }

    @Test
    public void isVolgendPostnrFalse1(){
        Assert.assertFalse(MeetstaatUtil.isVolgendPostnr("1.1.5.9","3"));
    }

    @Test
    public void isVolgendPostnrFalse2(){
        Assert.assertFalse(MeetstaatUtil.isVolgendPostnr("6.10.2.3", "6.10.2.3"));
    }
    @Test
    public void isVolgendPostnrFalse3(){
        Assert.assertFalse(MeetstaatUtil.isVolgendPostnr("3.2","3"));
    }
    @Test
    public void isVolgendPostnrFalse4(){
        Assert.assertFalse(MeetstaatUtil.isVolgendPostnr("3.2","2.2"));
    }
    @Test
    public void isVolgendPostnrTrue5(){
        Assert.assertTrue(MeetstaatUtil.isVolgendPostnr("6.9","6.10"));
    }
    @Test
    public void isVolgendPostnrFalse5(){
        Assert.assertFalse(MeetstaatUtil.isVolgendPostnr("3.9","4.1"));
    }
    @Test
    public void isVolgendPostnrFalse6(){
        Assert.assertFalse(MeetstaatUtil.isVolgendPostnr("2","3.1"));
    }

    @Test
    public void isVolgendPostnrTrue6(){
        Assert.assertTrue(MeetstaatUtil.isVolgendPostnr("2.8","3"));
    }

    @Test
    public void isVolgendPostnrTrue7(){
        Assert.assertTrue(MeetstaatUtil.isVolgendPostnr("2.8.1","3"));
    }

    @Test
    public void isVolgendPostnrTrue8(){
        Assert.assertTrue(MeetstaatUtil.isVolgendPostnr("2.8.1","2.9"));
    }

    @Test
    public void herstelVolgendPostnr1(){
        Assert.assertEquals("2", MeetstaatUtil.herstelPostnr("1.1.5.9","3"));
    }

    @Test
    public void herstelVolgendPostnr2(){
        Assert.assertEquals("3", MeetstaatUtil.herstelPostnr("2.1.5.9","3"));
    }

    @Test
    public void herstelVolgendPostnr3(){
        Assert.assertEquals("2.1.6", MeetstaatUtil.herstelPostnr("2.1.5.9","2.1.7"));
    }

    @Test
    public void herstelVolgendPostnr4(){
        Assert.assertEquals("2.1.6.1", MeetstaatUtil.herstelPostnr("2.1.6","2.1.7.1"));
    }

    @Test
    public void herstelVolgendPostnr5(){
        Assert.assertEquals("2.1.7", MeetstaatUtil.herstelPostnr("2.1.6","2.1.6"));
    }

    @Test
    public void herstelVolgendPostnr6(){
        Assert.assertEquals("2.1.6.1", MeetstaatUtil.herstelPostnr("2.1.6","2.1.6.2"));
    }


    @Test
    public void herstelVolgendPostnr7(){
        Assert.assertEquals("2.3", MeetstaatUtil.herstelPostnr("2.2","3.1"));
    }

    @Test
    public void herstelVolgendPostnr8(){
        Assert.assertEquals("4", MeetstaatUtil.herstelPostnr("3.2","3"));
    }

    @Test
    public void herstelVolgendPostnr9(){
        Assert.assertEquals("2.1", MeetstaatUtil.herstelPostnr("2","3.1"));
    }

    @Test
    public void herstelVolgendPostnr10(){
        Assert.assertEquals("7.4.1", MeetstaatUtil.herstelPostnr("7.4","6.4.1"));
    }

    @Test
    public void herstelVolgendPostnr11(){
        Assert.assertEquals("2", MeetstaatUtil.herstelPostnr("1","3"));
    }

    @Test
    public void herstelVolgendPostnr12(){
        Assert.assertEquals("6.2", MeetstaatUtil.herstelPostnr("6.1","7.2"));
    }

    @Test
    public void berekenVorigPostNr(){
        Assert.assertEquals("1",MeetstaatUtil.berekenVorigPostNr("2"));
    }
    @Test
    public void berekenVorigPostNr2(){
        Assert.assertEquals("6.5.1",MeetstaatUtil.berekenVorigPostNr("6.5.2"));
    }

    @Test
    public void berekenVorigPostNr3(){
        Assert.assertEquals("6.5",MeetstaatUtil.berekenVorigPostNr("6.5.1"));
    }
    @Test
    public void berekenVorigPostNr4(){
        Assert.assertEquals("6.9",MeetstaatUtil.berekenVorigPostNr("6.10"));
    }

    @Test
    public void getRootPostnr1(){
        Assert.assertEquals(6,MeetstaatUtil.getRootPostnr("6.10"));
    }
    @Test
    public void getRootPostnr2(){
        Assert.assertEquals(0,MeetstaatUtil.getRootPostnr("t"));
    }
    @Test
    public void getRootPostnr3(){
        Assert.assertEquals(0,MeetstaatUtil.getRootPostnr(".t"));
    }
    @Test
    public void getRootPostnr4(){
        Assert.assertEquals(6,MeetstaatUtil.getRootPostnr("6"));
    }
    @Test
    public void getRootPostnr5(){
        Assert.assertEquals(6,MeetstaatUtil.getRootPostnr("6.8.9.0"));
    }


}
