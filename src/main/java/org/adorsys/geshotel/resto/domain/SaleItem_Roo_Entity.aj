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
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SaleItem_Roo_Entity {
    
    declare @type: SaleItem: @Entity;
    
    @PersistenceContext
    transient EntityManager SaleItem.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long SaleItem.id;
    
    @Version
    @Column(name = "version")
    private Integer SaleItem.version;
    
    public Long SaleItem.getId() {
        return this.id;
    }
    
    public void SaleItem.setId(Long id) {
        this.id = id;
    }
    
    public Integer SaleItem.getVersion() {
        return this.version;
    }
    
    public void SaleItem.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void SaleItem.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void SaleItem.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SaleItem attached = SaleItem.findSaleItem(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void SaleItem.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void SaleItem.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public SaleItem SaleItem.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SaleItem merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager SaleItem.entityManager() {
        EntityManager em = new SaleItem().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SaleItem.countSaleItems() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SaleItem o", Long.class).getSingleResult();
    }
    
    public static List<SaleItem> SaleItem.findAllSaleItems() {
        return entityManager().createQuery("SELECT o FROM SaleItem o", SaleItem.class).getResultList();
    }
    
    public static SaleItem SaleItem.findSaleItem(Long id) {
        if (id == null) return null;
        return entityManager().find(SaleItem.class, id);
    }
    
    public static List<SaleItem> SaleItem.findSaleItemEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SaleItem o", SaleItem.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
