package be.ovam.esb.sap.client.factor;

import be.ovam.esb.sap.client.factory.CreateWbsFactory;
import be.ovam.sap.createwbs.CreateWBS;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Koen on 4/04/2014.
 */
public class CreateWbsFactoryTest {

    private CreateWbsFactory createWbsFactory;

    @Before
    public void setup(){
        createWbsFactory = new CreateWbsFactory();
        createWbsFactory.setEndpoint("http://10.1.11.115:8080/EsbSap/http/SapServices/createWBS");
    }

    @Test
    public void getPort(){
        CreateWBS port = createWbsFactory.getPort();
        Assert.assertNotNull(port);
    }

}
