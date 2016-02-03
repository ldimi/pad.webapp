package be.ovam.art46.sap.model;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(schema="SAP",name="SPREIDING")
@Audited
@AuditTable(schema="SAP",value="SPREIDING_HISTORIEK" )
public class Spreiding implements Comparable<Spreiding>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	
	@Override
	public String toString() {
		return "Spreiding [jaar=" + jaar + ", bedrag=" + bedrag + "]";
	}

	 
	
	private Integer jaar; 
	
	
	private BigDecimal bedrag;

	@ManyToOne(fetch=FetchType.LAZY,targetEntity=Project.class)
	@JoinColumn(name="PROJECT_ID")
	private Project project; 

	public Integer getJaar() {
		return jaar;
	}

	public void setJaar(Integer jaar) {
		this.jaar = jaar;
	}

	public BigDecimal getBedrag() {
		return bedrag;
	}

	public void setBedrag(BigDecimal bedrag) {
		this.bedrag = bedrag;
	}

	public int compareTo(Spreiding o) {
		// TODO Auto-generated method stub
		return this.jaar.compareTo(o.getJaar());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	

	
	
	
	}
