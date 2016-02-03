package be.ovam.art46.service.mock;

import be.ovam.pad.model.MeetstaatRegel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by koencorstjens on 14-8-13.
 */
public class MeetstaatMock {

    public List<MeetstaatRegel> getMeetstaatRegels() {
        List<MeetstaatRegel> meetstaatRegelList = new ArrayList<MeetstaatRegel>();
        MeetstaatRegel meetstaatRegel101 = new MeetstaatRegel();
        meetstaatRegel101.setPostnr("10.1");
        meetstaatRegelList.add(meetstaatRegel101);

        MeetstaatRegel meetstaatRegel4 = new MeetstaatRegel();
        meetstaatRegel4.setPostnr("4");
        meetstaatRegelList.add(meetstaatRegel4);

        MeetstaatRegel meetstaatRegel41 = new MeetstaatRegel();
        meetstaatRegel41.setPostnr("4.1");
        meetstaatRegelList.add(meetstaatRegel41);

        MeetstaatRegel meetstaatRegel411 = new MeetstaatRegel();
        meetstaatRegel411.setPostnr("4.1.1");
        meetstaatRegel411.setEenheidsprijs(new BigDecimal(1));
        meetstaatRegel411.setAantal(new BigDecimal(1));
        meetstaatRegelList.add(meetstaatRegel411);

        MeetstaatRegel meetstaatRegel412 = new MeetstaatRegel();
        meetstaatRegel412.setPostnr("4.1.2");
        meetstaatRegel412.setEenheidsprijs(new BigDecimal(1));
        meetstaatRegel412.setAantal(new BigDecimal(1));
        meetstaatRegelList.add(meetstaatRegel412);

        MeetstaatRegel meetstaatRegel22 = new MeetstaatRegel();
        meetstaatRegel22.setPostnr("2.2");
        meetstaatRegelList.add(meetstaatRegel22);

        MeetstaatRegel meetstaatRegel2 = new MeetstaatRegel();
        meetstaatRegel2.setPostnr("2.1");
        meetstaatRegelList.add(meetstaatRegel2);

        MeetstaatRegel meetstaatRegel1 = new MeetstaatRegel();
        meetstaatRegel1.setPostnr("1.1");
        meetstaatRegel1.setEenheidsprijs(new BigDecimal(1));
        meetstaatRegel1.setAantal(new BigDecimal(1));

        meetstaatRegelList.add(meetstaatRegel1);

        MeetstaatRegel meetstaatRegel3 = new MeetstaatRegel();
        meetstaatRegel3.setPostnr("3.1.1");
        meetstaatRegelList.add(meetstaatRegel3);

        MeetstaatRegel meetstaatRegel10 = new MeetstaatRegel();
        meetstaatRegel10.setPostnr("10");
        meetstaatRegelList.add(meetstaatRegel10);

        MeetstaatRegel meetstaatRegel40 = new MeetstaatRegel();
        meetstaatRegel40.setPostnr("40");
        meetstaatRegel40.setDetails("beschrijving");
        meetstaatRegel40.setEenheidsprijs(new BigDecimal(1));
        meetstaatRegel40.setAantal(new BigDecimal(1));
        meetstaatRegelList.add(meetstaatRegel40);

        return meetstaatRegelList;
    }
}
