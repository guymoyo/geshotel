package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.Assert;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import java.math.BigDecimal;

import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.resto.domain.ProductType;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.TypedQuery;
import org.adorsys.geshotel.resto.domain.Discount;
import org.adorsys.geshotel.resto.work.exception.NoParentGroupFound;
import javax.validation.constraints.Min;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.utility.Contract;
import org.hibernate.ejb.metamodel.SingularAttributeImpl;

import javax.persistence.Enumerated;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findProductsByTitleEquals",
		"findProductsByProductKeyEquals", "findProductsByQty" })
public class Product {
	public static Logger log = Logger.getLogger("product_log");
	@Column(name = "productKeyBk", unique = true)
	private String productKey;

	@NotNull
	@Column(unique = true)
	private String title;

	private String description;

	@NotNull
	private BigDecimal price;

	@NotNull
	@ManyToOne
	private ProductType productType;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ProductGroup> groups = new HashSet<ProductGroup>();

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Discount> discounts = new HashSet<Discount>();

	@Min(0L)
	private BigDecimal qty;

	private BigDecimal employeePrice;

	@Enumerated
	private BarRestauState productState;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((productKey == null) ? 0 : productKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productKey == null) {
			if (other.productKey != null)
				return false;
		} else if (!productKey.equals(other.productKey))
			return false;
		return true;
	}

	@PrePersist
	public void prePersist() {
		this.productKey = IdGenerator.generateId();
		this.productState = BarRestauState.OPENED;
	}

	@PreUpdate
	public void preUpdate() {
		this.productState = BarRestauState.OPENED;
	}

	public boolean hasProductGroup(ProductGroup group) {
		if (group == null)
			throw new NullPointerException("the Product Group Must not be null");
		if (this.groups == null || this.groups.isEmpty())
			return false;
		for (ProductGroup productGroup : this.groups) {
			if (productGroup.equals(group))
				return true;
		}
		return false;
	}

	public boolean hasAnyProductGroup(Collection<ProductGroup> groups) {
		if (groups == null || groups.isEmpty())
			return false;
		for (ProductGroup productGroup : groups) {
			if (this.hasProductGroup(productGroup))
				return true;
		}
		return false;
	}

	public static List<Product> findProductByProductGroup(ProductGroup group) {
		List<ProductGroup> allSubGroups = group.findAllSubGroups();
		List<Product> results = new ArrayList<Product>();
		allSubGroups.add(group);
		List<Product> allProducts = Product.findAllProducts();
		if (allProducts.isEmpty())
			return results;
		for (Product product : allProducts) {
			if (product.hasAnyProductGroup(allSubGroups)) {
				results.add(product);
				break;
			}
		}
		return results;
	}

	public static List<Product> findProductsByProductGroups(
			Collection<ProductGroup> groups) {
		if (groups == null || groups.isEmpty())
			throw new NullPointerException("Groups Might not be Null");
		List<Product> results = new ArrayList<Product>();
		for (ProductGroup productGroup : groups) {
			results.addAll(findProductByProductGroup(productGroup));
		}
		return results;
	}

	public static List<Product> findProductsByUser(UserAccount userAccount) {
		List<ProductGroup> groupsByUser = ProductGroup
				.findGroupsByUser(userAccount);
		return Product.findProductsByProductGroups(groupsByUser);
	}

	public static List<Product> findValidProductsByUser(
			UserAccount userAccount, BigDecimal qty) {
		List<Product> result = new ArrayList<Product>();
		List<Product> productsByUser = findProductsByUser(userAccount);
		for (Product product : productsByUser) {
			if (!(product.getQty().doubleValue() <= qty.doubleValue()))
				result.add(product);
		}
		return result;
	}

	public static void findValidProductsByUserSQL(UserAccount userAccount,
			BigDecimal qty) {
		String request = "SELECT * FROM Product AS P WHERE p.groups ";
	}

	public static List<Product> findBarProduct() {
		List<Product> allProducts = Product.findAllProducts();
		List<Product> barProducts = new ArrayList<Product>();
		for (Product product : allProducts) {
			if (product.isBarProduct())
				barProducts.add(product);
		}
		return barProducts;
	}

	public static List<Product> findRestoProducts() {
		ProductGroup restoGroup = ProductGroup
				.findProductGroupsByTitleEquals("RESTO").getResultList()
				.iterator().next();
		return findProductByProductGroup(restoGroup);
	}

	public static List<Product> findProductsByQtyGreatterThan(BigDecimal qty) {
		if (qty == null)
			throw new IllegalArgumentException("The qty argument is required");
		EntityManager em = Product.entityManager();
		TypedQuery<Product> q = em
				.createQuery("SELECT o FROM Product AS o WHERE o.qty >= :qty",
						Product.class);
		q.setParameter("qty", qty);
		return q.getResultList();
	}

	public boolean isBarProduct() {
		ProductGroup barGroup = ProductGroup
				.findProductGroupsByTitleEquals("BAR").getResultList()
				.iterator().next();
		for (ProductGroup productGroup : groups) {
			if (productGroup.equals(barGroup))
				return true;
		}
		return false;
	}

	public boolean isRestauProduct() {
		List<ProductGroup> resultList = ProductGroup.findProductGroupsByTitleLike("REST").getResultList();
		if (resultList == null || resultList.isEmpty())
			return false;
		ProductGroup barGroup = resultList.iterator().next();
		for (ProductGroup productGroup : groups) {
			if (productGroup.equals(barGroup))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param parentGroup
	 *            : the group whose we want to find all parent group
	 * @param highGroup
	 *            The highest group where to stop the recursion.
	 * @throws NoParentGroupFound
	 */
	public static List<ProductGroup> findParentGroupRecursively(
			ProductGroup productGroup, ProductGroup highGroup,
			List<ProductGroup> productGroups) throws NoParentGroupFound {
		Assert.notNull(productGroup, "The Product Group should not be null");
		Assert.notNull(highGroup, "The Product Group should not be null");
		Assert.notNull(productGroups, "The Product Group should not be null");
		if (!productGroup.equals(highGroup)) {
			ProductGroup parentGroup = productGroup.findParentGroup();
			if (parentGroup.equals(highGroup)) {
				return productGroups;
			} else {
				productGroups.add(parentGroup);
				return findParentGroupRecursively(parentGroup, highGroup,
						productGroups);
			}
		}
		return productGroups;
	}

	public void newInventoryItem() {
		InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setDesignation(title);
		inventoryItem.setEntry(qty);
		inventoryItem.setFinalStock(qty);
	}

	public static List<Product> findAllProducts() {
		BarRestauState barRestauState = BarRestauState.DISABLED;
		TypedQuery<Product> typedQuery = entityManager()
				.createQuery(
						"SELECT o FROM Product o WHERE o.productState != :barRestauState",
						Product.class);
		typedQuery.setParameter("barRestauState", barRestauState);
		return typedQuery.getResultList();
	}

	public static List<Product> findAll() {
		return entityManager().createQuery("SELECT o FROM Product o ",
				Product.class).getResultList();
	}

	public static List<Product> findAllCQ(){
    	CriteriaBuilder builder = entityManager().getCriteriaBuilder();
    	CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
    	Root<Product> productRoot = criteria.from(Product.class);
    	criteria.where(builder.equal(productRoot.get(Product_.productState), BarRestauState.OPENED),
    			builder.equal(productRoot.get(Product_.title),"Bailleys"));
    	List<Product> resultList = entityManager().createQuery(criteria).getResultList();
    	return resultList;
    }
}
