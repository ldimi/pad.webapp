package be.ovam.art46.util;

import be.ovam.pad.util.BigDecimalFormatter;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by Koen on 25/07/2014.
 */
public class BigDecimalFormatterTest {
    @Test
    public void test(){
        System.out.println(BigDecimalFormatter.formatCurencyFull(new BigDecimal(200000.2)));
    }

}
