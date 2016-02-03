package be.ovam.art46.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.tool.EnversSchemaGenerator;

public class EnversSchema {

	
	public static void main(String[] args) {
	  
	    Configuration hibernateConfiguration = new Configuration();
	    AuditConfiguration.getFor(hibernateConfiguration);
	    EnversSchemaGenerator esg = new EnversSchemaGenerator(hibernateConfiguration);
	    org.hibernate.tool.hbm2ddl.SchemaExport se = esg.export();
	    se.setOutputFile("sql/schema.sql");
	    se.setFormat(true);
	    se.setDelimiter(";");
	    se.drop(true, false);
	    se.create(true, false);
	}
}
