package org.adorsys.geshotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.domain.Gender;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.PrePersist;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Set;
import org.adorsys.geshotel.resto.domain.Discount;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(finders = { "findCustomersByLastNameLike", "findCustomersByIdentityNumberEquals", "findCustomersByFirstNameLike" })
public class Customer {

    private String firstName;

    @NotNull
    private String lastName;
    
    private String maidenName;

    private String fullName;

    @Enumerated
    private Gender gender;

    @Column(unique = true)
    private String identityNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date delivredDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date bornDate;

    private String bornPlace;

    private String phoneNumber;

    private String email;

    private String nationality;
    
    private String residenceCountry;
    
    private String address;
    
    private String comeFrom;
    
    private String goTo;

    private String profession;
    
    private String transportMode;
    
    private String carNumber;

    private Boolean blackList;

    private BigDecimal account = BigDecimal.ZERO;

    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Discount> discounts = new HashSet<Discount>();

    @PrePersist
    private void buildFullName() {
        this.fullName = this.firstName + " " + this.lastName;
    }

    public static List<Customer> findActiveCustomerByInvoice(List<Invoice> invoices) {
        List<Customer> customers = new ArrayList<Customer>();
        for (Invoice invoice : invoices) {
            customers.add(invoice.getCustomer());
        }
        return customers;
    }

    public static TypedQuery<Customer> findCustomersByLastNameLikeByOrder(String lastName) {
        if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("The lastName argument is required");
        lastName = lastName.replace('*', '%');
        if (lastName.charAt(lastName.length() - 1) != '%') {
            lastName = lastName + "%";
        }
        EntityManager em = Customer.entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o WHERE LOWER(o.lastName) LIKE LOWER(:lastName) ORDER BY o.lastName ASC", Customer.class).setFirstResult(0).setMaxResults(10);
        q.setParameter("lastName", lastName);
        return q;
    }
    
    
    public static TypedQuery<Customer> findCustomersByFirstNameLikeByOrder(String firstName) {
        if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("The firstName argument is required");
        firstName = firstName.replace('*', '%');
     
        if (firstName.charAt(firstName.length() - 1) != '%') {
            firstName = firstName + "%";
        }
        EntityManager em = Customer.entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o WHERE LOWER(o.firstName) LIKE LOWER(:firstName) ORDER BY o.firstName ASC", Customer.class).setFirstResult(0).setMaxResults(10);
        q.setParameter("firstName", firstName);
        return q;
    }

    public boolean isCreated() {
        boolean bool = true;
        List<Customer> list = Customer.findCustomersByIdentityNumberEquals(this.identityNumber).getResultList();
        if (list == null || list.isEmpty()) bool = false;
        return bool;
    }
}
