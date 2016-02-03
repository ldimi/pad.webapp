package be.ovam.art46.setup;

import javassist.bytecode.stackmap.BasicBlock;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by Koen on 10/02/14.
 */
public final class HSQLSchemaCreator implements InitializingBean {

    /**
     * schema name.
     */
    private String[] schemas;

    /**
     * data source.
     */
    private DataSource dataSource;

    // setters and getters
    public String[] getSchemas() {
        return schemas;
    }

    public void setSchemas(String[] schemas) {
        this.schemas = schemas;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Create schema.
     *
     * @throws Exception any exception
     */
    public void afterPropertiesSet() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        for(String schema : schemas){
            try{
                jdbcTemplate.execute("CREATE SCHEMA " + schema + " AUTHORIZATION DBA");
            }catch (Exception e){
                System.out.println(e.getMessage() +": "+ e.getStackTrace());
            }
        }
    }

}
