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
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.springframework.transaction.annotation.Transactional;

privileged aspect KitchenOrder_Roo_Entity {
    
    declare @type: KitchenOrder: @Entity;
    
    @PersistenceContext
    transient EntityManager KitchenOrder.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long KitchenOrder.id;
    
    @Version
    @Column(name = "version")
    private Integer KitchenOrder.version;
    
    public Long KitchenOrder.getId() {
        return this.id;
    }
    
    public void KitchenOrder.setId(Long id) {
        this.id = id;
    }
    
    public Integer KitchenOrder.getVersion() {
        return this.version;
    }
    
    public void KitchenOrder.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void KitchenOrder.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void KitchenOrder.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            KitchenOrder attached = KitchenOrder.findKitchenOrder(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void KitchenOrder.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void KitchenOrder.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public KitchenOrder KitchenOrder.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        KitchenOrder merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager KitchenOrder.entityManager() {
        EntityManager em = new KitchenOrder().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long KitchenOrder.countKitchenOrders() {
        return entityManager().createQuery("SELECT COUNT(o) FROM KitchenOrder o", Long.class).getSingleResult();
    }
    
    public static List<KitchenOrder> KitchenOrder.findAllKitchenOrders() {
        return entityManager().createQuery("SELECT o FROM KitchenOrder o", KitchenOrder.class).getResultList();
    }
    
    public static KitchenOrder KitchenOrder.findKitchenOrder(Long id) {
        if (id == null) return null;
        return entityManager().find(KitchenOrder.class, id);
    }
    
    public static List<KitchenOrder> KitchenOrder.findKitchenOrderEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM KitchenOrder o", KitchenOrder.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}