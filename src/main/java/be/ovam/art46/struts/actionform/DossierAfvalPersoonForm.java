/*
 * Filename: DossierAfvalPersoonForm.java
 * Creator: torghal
 * Created: Apr 6, 2005
 *
 * Purpose:
 * 
 *
 */
package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author torghal
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DossierAfvalPersoonForm extends ActionForm
{
    private String persoon_id;
    private String dossier_id;
    private String naam;
    private String adres;
    private String stad;
    private String postcode;
    private String tel;
    private String eigenaar_s = "0";
    private String gebruiker_s = "0";
    
    
    
    public String getAdres()
    {
        return adres;
    }
    public void setAdres(String adres)
    {
        this.adres = adres;
    }
    public String getDossier_id()
    {
        return dossier_id;
    }
    public void setDossier_id(String dossier_id)
    {
        this.dossier_id = dossier_id;
    }
    public String getEigenaar_s()
    {
        return eigenaar_s;
    }
    public void setEigenaar_s(String eigenaar_s)
    {
        this.eigenaar_s = eigenaar_s;
    }
    public String getGebruiker_s()
    {
        return gebruiker_s;
    }
    public void setGebruiker_s(String gebruiker_s)
    {
        this.gebruiker_s = gebruiker_s;
    }
    public String getNaam()
    {
        return naam;
    }
    public void setNaam(String naam)
    {
        this.naam = naam;
    }
    public String getPersoon_id()
    {
        return persoon_id;
    }
    public void setPersoon_id(String persoon_id)
    {
        this.persoon_id = persoon_id;
    }
    public String getPostcode()
    {
        return postcode;
    }
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }
    public String getStad()
    {
        return stad;
    }
    public void setStad(String stad)
    {
        this.stad = stad;
    }
    public String getTel()
    {
        return tel;
    }
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    
    
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        setEigenaar_s("0");
        setGebruiker_s("0");
    }
}
