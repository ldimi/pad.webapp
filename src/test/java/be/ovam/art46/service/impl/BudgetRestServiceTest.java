package be.ovam.art46.service.impl;

import be.ovam.art46.model.rest.SchuldvorderingBudget;
import be.ovam.art46.util.OvamCalenderUtils;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Koen on 1/07/2014.
 */
public class BudgetRestServiceTest {

    BudgetRestServiceImpl budgetRestService = new BudgetRestServiceImpl();

    @Test
    public void verzend(){
        budgetRestService.setRestTemplate(new RestTemplate());
        budgetRestService.setUrlSchuldvorderingIn("http://localhost:8180/budget/rest/schuldvordering/in.do");
        SchuldvorderingBudget schuldvorderingBudget = new SchuldvorderingBudget();
        schuldvorderingBudget.setPadNr("SV-000129");
        schuldvorderingBudget.setBetaalDatum(Calendar.getInstance());
        schuldvorderingBudget.setBedrag(new BigDecimal(0));
        schuldvorderingBudget.setVanDatum(OvamCalenderUtils.dateToCalendar(new Date()));
        schuldvorderingBudget.setTotDatum(OvamCalenderUtils.dateToCalendar(new Date()));
        budgetRestService.verzend(schuldvorderingBudget);
    }


}
