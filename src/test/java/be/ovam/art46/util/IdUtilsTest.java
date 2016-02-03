package be.ovam.art46.util;

import be.ovam.util.IdUtils;
import  org.junit.Assert;
import org.junit.Test;

/**
 * Created by Koen on 11/06/2014.
 */
public class IdUtilsTest {

    @Test
    public void testIdAsLong(){
        Assert.assertEquals(new Long(123456789), IdUtils.getIdAsLong("000123456789"));
        Assert.assertEquals(new Long(123456789), IdUtils.getIdAsLong("SV-000123456789"));
        Assert.assertNull(IdUtils.getIdAsLong("SV"));
        Assert.assertEquals(new Long(0),IdUtils.getIdAsLong("SV-000"));
    }

    @Test
    public void testIdAsInteger(){
        Assert.assertEquals(new Integer(123456789), IdUtils.getIdAsInteger("000123456789"));
        Assert.assertEquals(new Integer(123456789), IdUtils.getIdAsInteger("SV-000123456789"));
        Assert.assertNull(IdUtils.getIdAsInteger("SV"));
        Assert.assertNull(IdUtils.getIdAsInteger(""));
        Assert.assertEquals(new Integer(0),IdUtils.getIdAsInteger("SV-000"));
    }
}
