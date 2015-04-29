package com.tesis.app.clases;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.StringWriter;

public class CMapper {

	private static ObjectMapper m = new ObjectMapper();
    private static JsonFactory jf = new JsonFactory();
    
    public static <T> Object fromJson(String jsonAsString, Class<T> BeanClass) 
    {
        try 
        {
			return m.readValue(jsonAsString, BeanClass);
		} 
        catch (Exception e) 
		{
			return null;
		}
    }
    public static String toJson(Object Bean, boolean prettyPrint) 
    {
        StringWriter sw = new StringWriter();
        try 
        {
        	JsonGenerator jg = jf.createJsonGenerator(sw);
            if (prettyPrint) 
            {
                jg.useDefaultPrettyPrinter();
            }
            m.writeValue(jg, Bean);
		} 
        catch (Exception e) 
		{
        	sw.append(e.getMessage());
		}        
        return sw.toString();
    }
    
}
