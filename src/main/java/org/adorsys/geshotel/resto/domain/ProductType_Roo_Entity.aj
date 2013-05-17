// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ProductType_Roo_Entity {
    
    declare @type: ProductType: @Entity;
    
    @PersistenceContext
    transient EntityManager ProductType.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ProductType.id;
    
    @Version
    @Column(name = "version")
    private Integer ProductType.version;
    
    public Long ProductType.getId() {
        return this.id;
    }
    
    public void ProductType.setId(Long id) {
        this.id = id;
    }
    
    public Integer ProductType.getVersion() {
        return this.version;
    }
    
    public void ProductType.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ProductType.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ProductType.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ProductType attached = ProductType.findProductType(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ProductType.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ProductType.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ProductType ProductType.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ProductType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager ProductType.entityManager() {
        EntityManager em = new ProductType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ProductType.countProductTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ProductType o", Long.class).getSingleResult();
    }
    
    public static ProductType ProductType.findProductType(Long id) {
        if (id == null) return null;
        return entityManager().find(ProductType.class, id);
    }
    
    public static List<ProductType> ProductType.findProductTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ProductType o", ProductType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}