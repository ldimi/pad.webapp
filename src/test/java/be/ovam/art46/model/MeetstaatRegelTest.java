package be.ovam.art46.model;

import be.ovam.pad.model.MeetstaatRegel;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by koencorstjens on 29-7-13.
 */
public class MeetstaatRegelTest {

    @Test
    public void compareBigger(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1");
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("1.1");
        Assert.assertEquals(1,meetstaatRegel2.compareTo(meetstaatRegel));
    }
    @Test
    public void compareToEqual(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1");
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("1");
        Assert.assertEquals(0,meetstaatRegel2.compareTo(meetstaatRegel));
    }
    @Test
    public void compareSmaller(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1.1");
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("1");
        Assert.assertEquals(-1,meetstaatRegel2.compareTo(meetstaatRegel));
    }

    @Test
    public void compareBigger2(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1.1");
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("1.2");
        Assert.assertEquals(1,meetstaatRegel2.compareTo(meetstaatRegel));
    }

    @Test
    public void compareBigger3(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("2.1");
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("1.1");
        Assert.assertEquals(-1,meetstaatRegel2.compareTo(meetstaatRegel));
    }

    @Test
    public void compareBiggerBasic(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("2");
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("1");
        Assert.assertEquals(-1,meetstaatRegel2.compareTo(meetstaatRegel));
    }
    @Test
    public void compareBiggerBasic2(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1");
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("2");
        Assert.assertTrue(meetstaatRegel2.compareTo(meetstaatRegel)>0);
    }

    @Test
    public void compareBigger4(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("2.1");
        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("4");
        Assert.assertTrue(meetstaatRegel2.compareTo(meetstaatRegel)>0);
    }

    @Test
    public void getRegelTotaal(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setAantal(new BigDecimal(10));
        meetstaatRegel.setEenheidsprijs(new BigDecimal(25.1));
        Assert.assertEquals(new BigDecimal(251).toBigInteger(), meetstaatRegel.getRegelTotaal().toBigInteger());
    }
    @Test
    public void getRegelTotaalNull1(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        String string= null;
        meetstaatRegel.setAantalString(string);
        meetstaatRegel.setEenheidsprijs(new BigDecimal(25.1));
        Assert.assertNull(meetstaatRegel.getRegelTotaal());
    }
    @Test
    public void getRegelTotaalNull2(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setAantal(new BigDecimal(10));
        String string= null;
        meetstaatRegel.setEenheidsprijsString(string);
        Assert.assertNull(meetstaatRegel.getRegelTotaal());
    }
    @Test
    public void getDetailtotaal1(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1.1.1");
        meetstaatRegel.setAantal(new BigDecimal(10));
        meetstaatRegel.setEenheidsprijs(new BigDecimal(10));
        Assert.assertEquals(new BigDecimal(100),meetstaatRegel.getDetailTotaal());
    }
    @Test
    public void getDetailtotaal2(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1.1.1.1.1.1.1");
        meetstaatRegel.setAantal(new BigDecimal(10));
        meetstaatRegel.setEenheidsprijs(new BigDecimal(10));
        Assert.assertEquals(new BigDecimal(100),meetstaatRegel.getDetailTotaal());
        Assert.assertNull(meetstaatRegel.getSubTotaal());
        Assert.assertNull(meetstaatRegel.getPostTotaal());
    }

    @Test
    public void getDetailtotaalNull(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1.1");
        meetstaatRegel.setAantal(new BigDecimal(10));
        meetstaatRegel.setEenheidsprijs(new BigDecimal(10));
        Assert.assertNull(meetstaatRegel.getDetailTotaal());
        Assert.assertEquals(new BigDecimal(100), meetstaatRegel.getSubTotaal());
        Assert.assertNull(meetstaatRegel.getPostTotaal());
    }

    @Test
    public void setPostnr1(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("3.5.1.");
        Assert.assertEquals("3.5.1", meetstaatRegel.getPostnr());
    }
    @Test
    public void setPostnr2(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("3.5.1");
        Assert.assertEquals("3.5.1", meetstaatRegel.getPostnr());
    }
    @Test
    public void setPostnr3(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("3,5,1,");
        Assert.assertEquals("3.5.1", meetstaatRegel.getPostnr());
    }

    @Test
    public void setPostNr1(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr(" 1.2");
        Assert.assertEquals("1.2", meetstaatRegel.getPostnr());
    }

    @Test
    public void setPostNr2(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr(" 1,2");
        Assert.assertEquals("1.2", meetstaatRegel.getPostnr());
    }

    @Test
    public void setPostNr3(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr("1,2 ");
        Assert.assertEquals("1.2", meetstaatRegel.getPostnr());
    }

    @Test
    public void setPostNr4(){
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        meetstaatRegel.setPostnr(" 1.2 ");
        Assert.assertEquals("1.2", meetstaatRegel.getPostnr());
    }

}
