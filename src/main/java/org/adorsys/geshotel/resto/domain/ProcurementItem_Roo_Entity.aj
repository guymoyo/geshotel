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
import org.adorsys.geshotel.resto.domain.ProcurementItem;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ProcurementItem_Roo_Entity {
    
    declare @type: ProcurementItem: @Entity;
    
    @PersistenceContext
    transient EntityManager ProcurementItem.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ProcurementItem.id;
    
    @Version
    @Column(name = "version")
    private Integer ProcurementItem.version;
    
    public Long ProcurementItem.getId() {
        return this.id;
    }
    
    public void ProcurementItem.setId(Long id) {
        this.id = id;
    }
    
    public Integer ProcurementItem.getVersion() {
        return this.version;
    }
    
    public void ProcurementItem.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ProcurementItem.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ProcurementItem.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ProcurementItem attached = ProcurementItem.findProcurementItem(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ProcurementItem.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ProcurementItem.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ProcurementItem ProcurementItem.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ProcurementItem merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager ProcurementItem.entityManager() {
        EntityManager em = new ProcurementItem().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ProcurementItem.countProcurementItems() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ProcurementItem o", Long.class).getSingleResult();
    }
    
    public static List<ProcurementItem> ProcurementItem.findAllProcurementItems() {
        return entityManager().createQuery("SELECT o FROM ProcurementItem o", ProcurementItem.class).getResultList();
    }
    
    public static ProcurementItem ProcurementItem.findProcurementItem(Long id) {
        if (id == null) return null;
        return entityManager().find(ProcurementItem.class, id);
    }
    
    public static List<ProcurementItem> ProcurementItem.findProcurementItemEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ProcurementItem o", ProcurementItem.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
