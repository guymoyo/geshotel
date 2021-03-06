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
import org.adorsys.geshotel.resto.domain.ParentGroup;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ParentGroup_Roo_Entity {
    
    declare @type: ParentGroup: @Entity;
    
    @PersistenceContext
    transient EntityManager ParentGroup.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ParentGroup.id;
    
    @Version
    @Column(name = "version")
    private Integer ParentGroup.version;
    
    public Long ParentGroup.getId() {
        return this.id;
    }
    
    public void ParentGroup.setId(Long id) {
        this.id = id;
    }
    
    public Integer ParentGroup.getVersion() {
        return this.version;
    }
    
    public void ParentGroup.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ParentGroup.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ParentGroup.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ParentGroup attached = ParentGroup.findParentGroup(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ParentGroup.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ParentGroup.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ParentGroup ParentGroup.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ParentGroup merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager ParentGroup.entityManager() {
        EntityManager em = new ParentGroup().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ParentGroup.countParentGroups() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ParentGroup o", Long.class).getSingleResult();
    }
    
    public static List<ParentGroup> ParentGroup.findAllParentGroups() {
        return entityManager().createQuery("SELECT o FROM ParentGroup o", ParentGroup.class).getResultList();
    }
    
    public static ParentGroup ParentGroup.findParentGroup(Long id) {
        if (id == null) return null;
        return entityManager().find(ParentGroup.class, id);
    }
    
    public static List<ParentGroup> ParentGroup.findParentGroupEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ParentGroup o", ParentGroup.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
