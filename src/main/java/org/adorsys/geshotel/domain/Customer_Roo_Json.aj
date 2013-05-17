// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.adorsys.geshotel.domain.Customer;

privileged aspect Customer_Roo_Json {
    
    public String Customer.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Customer Customer.fromJsonToCustomer(String json) {
        return new JSONDeserializer<Customer>().use(null, Customer.class).deserialize(json);
    }
    
    public static String Customer.toJsonArray(Collection<Customer> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Customer> Customer.fromJsonArrayToCustomers(String json) {
        return new JSONDeserializer<List<Customer>>().use(null, ArrayList.class).use("values", Customer.class).deserialize(json);
    }
    
}