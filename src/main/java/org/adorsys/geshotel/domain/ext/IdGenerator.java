package org.adorsys.geshotel.domain.ext;
import java.util.UUID;

/**
 * The id generator is a class that generates entity id's based on
 * a synchronized time, the bean name and server id.
 *  
 * @author fpo
 *
 */
public class IdGenerator {
	
	public static final String generateId(){
		return UUID.randomUUID().toString();
	}
	
	public static  String generateBusinessKey(String prefix){
		//TODO
		return null;
	}
}
