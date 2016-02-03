package be.ovam.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Koen on 11/06/2014.
 */
public class IdUtils {

    public static Long getIdAsLong(String id){
        if(StringUtils.isEmpty(id)){
            return null;
        }
        id = id.replaceAll("[^0-9]+", "");
        id = id.replaceFirst("^0+(?!$)", "");
        if(StringUtils.isEmpty(id)){
            return null;
        }
        return new Long(id);
    }

    public static Integer getIdAsInteger(String id){
        if(StringUtils.isEmpty(id)){
            return null;
        }
        id = id.replaceAll("[^0-9]+", "");
        id = id.replaceFirst("^0+(?!$)", "");
        if(StringUtils.isEmpty(id)){
            return null;
        }
        return new Integer(id);
    }
}
