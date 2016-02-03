/*
 * Filename: GoedkeuringForm.java
 * Creator: torghal
 * Created: Jan 1, 2005
 *
 * Purpose:
 * 
 *
 */
package be.ovam.art46.struts.actionform;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;


/**
 * @author torghal
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PlanningGoedkeuringForm extends ValidatorForm
{
    private String datumGoedkeuring;
    private String opnameJaar;
    private String dossierTypeIvs;
    private String[] deleteRamingen;

    
    
    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		deleteRamingen = new String[0];
		super.reset(mapping, request);
	}
	public String getDatumGoedkeuring()
    {
        return datumGoedkeuring;
    }   
    public void setDatumGoedkeuring(String datumGoedkeuring)
    {
        this.datumGoedkeuring = datumGoedkeuring;
    }
    
    public String getOpnameJaar()
    {
        return opnameJaar;
    }
    public void setOpnameJaar(String opnameJaar)
    {
        this.opnameJaar = opnameJaar;
    }
	public String getDossierTypeIvs() {
		return dossierTypeIvs;
	}
	public void setDossierTypeIvs(String dossierTypeIvs) {
		this.dossierTypeIvs = dossierTypeIvs;
	}	
	public String[] getDeleteRamingen() {
		return deleteRamingen;
	}
	public void setDeleteRamingen(String[] deleteRamingen) {
		this.deleteRamingen = deleteRamingen;
	}
	
	
}
