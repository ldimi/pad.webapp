package be.ovam.art46.sap.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjectDTO {
	
    private String projectId;
    private String projectB;
    private String schuldeiser;

    private String bestekNr;

    private String dossierNr;

    private String dossier_B;

    private BigDecimal initieelBedrag;
    
    private BigDecimal afTeBoekenBedrag ; 
    
    private BigDecimal voorgesteldAfTeBoekenBedrag; 
    
    private boolean vastleggingMagAfgeboektWorden = false; 
   
    
    
	
	public ProjectDTO(String projectId, String projectB, String schuldeiser,
			BigDecimal initieelBedrag, List<SpreidingDTO> spreiding) {
		super();
		this.projectId = projectId;
		this.projectB = projectB;
		this.schuldeiser = schuldeiser;
		this.initieelBedrag = initieelBedrag;
		this.spreiding = spreiding;
	}

	public ProjectDTO() {
		super();
	}
	
	public ProjectDTO(Project project) {
		super();
		
		this.projectId = project.getProjectId();
		this.projectB = project.getProjectB();
		this.schuldeiser = project.getSchuldeiser();
		this.initieelBedrag = project.getInitieelBedrag();
	    List<Spreiding> spreiding2 = project.getSpreiding();
	
		 
		 for (Spreiding spreiding : spreiding2) {
			
			 this.spreiding.add(new SpreidingDTO(spreiding));
		}
	}

	private List<SpreidingDTO> spreiding = new ArrayList<SpreidingDTO>();

	public List<SpreidingDTO> getSpreiding() {
		return spreiding;
	}

	public void setSpreiding(List<SpreidingDTO> spreiding) {
		this.spreiding = spreiding;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectB() {
		return projectB;
	}

	public void setProjectB(String projectB) {
		this.projectB = projectB;
	}

	public String getSchuldeiser() {
		return schuldeiser;
	}

	public void setSchuldeiser(String schuldeiser) {
		this.schuldeiser = schuldeiser;
	}

	public BigDecimal getInitieelBedrag() {
		return initieelBedrag;
	}

	public void setInitieelBedrag(BigDecimal initieelBedrag) {
		this.initieelBedrag = initieelBedrag;
	}

	public BigDecimal getAfTeBoekenBedrag() {
		return afTeBoekenBedrag;
	}

	public void setAfTeBoekenBedrag(BigDecimal afTeBoekenBedrag) {
		this.afTeBoekenBedrag = afTeBoekenBedrag;
	}

	public BigDecimal getVoorgesteldAfTeBoekenBedrag() {
		return voorgesteldAfTeBoekenBedrag;
	}

	public void setVoorgesteldAfTeBoekenBedrag(
			BigDecimal voorgesteldAfTeBoekenBedrag) {
		this.voorgesteldAfTeBoekenBedrag = voorgesteldAfTeBoekenBedrag;
	}

	public boolean isVastleggingMagAfgeboektWorden() {
		return vastleggingMagAfgeboektWorden;
	}

	public void setVastleggingMagAfgeboektWorden(
			boolean vastleggingMagAfgeboektWorden) {
		this.vastleggingMagAfgeboektWorden = vastleggingMagAfgeboektWorden;
	}

    public String getBestekNr() {
        return bestekNr;
    }

    public void setBestekNr(String bestekNr) {
        this.bestekNr = bestekNr;
    }


    public String getDossierNr() {
        return dossierNr;
    }

    public void setDossierNr(String dossierNr) {
        this.dossierNr = dossierNr;
    }

    public String getDossier_B() {
        return dossier_B;
    }

    public void setDossier_B(String dossier_B) {
        this.dossier_B = dossier_B;
    }
}
