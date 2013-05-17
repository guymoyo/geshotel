package org.adorsys.geshotel.resto.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.adorsys.geshotel.domain.RoleName;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.resto.work.exception.NoParentGroupFound;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findProductGroupsByProductGroupKeyEquals", "findProductGroupsByTitleEquals", "findProductGroupsByTitleLike" })
public class ProductGroup {

    @Column(name = "productGroupKeyBk", unique = true)
    private String productGroupKey;

    @NotNull
    private String title;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductType> types = new HashSet<ProductType>();

    @NotNull
    @ElementCollection
    private Set<RoleName> allowedRoles = new HashSet<RoleName>();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productGroupKey == null) ? 0 : productGroupKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProductGroup other = (ProductGroup) obj;
        if (productGroupKey == null) {
            if (other.productGroupKey != null) return false;
        } else if (!productGroupKey.equals(other.productGroupKey)) return false;
        return true;
    }

    @PrePersist
    public void prePersist() {
        this.productGroupKey = IdGenerator.generateId();
    }

    public List<ProductGroup> findAllSubGroups() {
        List<ParentGroup> resultLists = ParentGroup.findParentGroupsByProductGroupParentKeyEquals(this.productGroupKey).getResultList();
        List<ProductGroup> productGroups = new ArrayList<ProductGroup>();
        for (ParentGroup parentGroup : resultLists) {
            String groupKey = parentGroup.getProductGroupKey();
            ProductGroup singleResult = ProductGroup.findProductGroupsByProductGroupKeyEquals(groupKey).getSingleResult();
            productGroups.add(singleResult);
        }
        return productGroups;
    }

    public static List<ProductGroup> findSubGroupRecursively(ProductGroup group, List<ProductGroup> groups) {
        groups = group.findAllSubGroups();
        List<ProductGroup> subGoups = new ArrayList<ProductGroup>();
        for (ProductGroup productGroup : groups) {
            subGoups.addAll(productGroup.findAllSubGroups());
        }
        return subGoups;
    }

    public ProductGroup findParentGroup() throws NoParentGroupFound {
        List<ParentGroup> parentGroups = ParentGroup.findParentGroupsByProductGroupKeyEquals(this.productGroupKey).getResultList();
        if (parentGroups == null || parentGroups.isEmpty()) throw new NoParentGroupFound("No parent group found", this);
        ParentGroup singleResult = parentGroups.iterator().next();
        String groupKey = singleResult.getProductGroupParentKey();
        if (groupKey.equals("N/A")) {
            ProductGroup productGroup = new ProductGroup();
            productGroup.setDescription("Default productgroup");
            productGroup.setTitle("Default Product group key");
            productGroup.setProductGroupKey("N/A");
            return productGroup;
        }
        System.out.println("\n \n Parent Group Keys : " + groupKey);
        ProductGroup productGroup = ProductGroup.findProductGroupsByProductGroupKeyEquals(groupKey).getSingleResult();
        return productGroup;
    }

    public boolean hasRole(RoleName rn) {
        boolean result = false;
        for (RoleName roleName : this.allowedRoles) {
            if (roleName.equals(rn)) return true;
        }
        return result;
    }

    public boolean hasAnyRoles(Set<RoleName> roles) {
        if (roles == null || roles.isEmpty()) throw new IllegalArgumentException("Rols might not be Null");
        boolean result = false;
        for (RoleName roleName : roles) {
            if (this.hasRole(roleName)) return true;
        }
        return result;
    }

    public static List<ProductGroup> findGroupsByUserRoles(Set<RoleName> roles) {
        List<ProductGroup> allProductGroups = ProductGroup.findAllProductGroups();
        List<ProductGroup> results = new ArrayList<ProductGroup>();
        if (roles == null || roles.isEmpty()) return results;
        for (ProductGroup productGroup : allProductGroups) {
            if (productGroup.hasAnyRoles(roles)) {
                results.add(productGroup);
            }
        }
        return results;
    }

    public static List<ProductGroup> findGroupsByUser(UserAccount userAccount) {
        if (userAccount == null) throw new NullPointerException("The User Account Must Not be null");
        return findGroupsByUserRoles(userAccount.getRoleNames());
    }
}
