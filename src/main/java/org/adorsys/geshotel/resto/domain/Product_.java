package org.adorsys.geshotel.resto.domain;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public class Product_ {
	public static volatile SingularAttribute<Product, String> productKey;

	public static volatile SingularAttribute<Product, String> title;

	public static volatile SingularAttribute<Product, String> description;

	public static volatile SingularAttribute<Product, BigDecimal> price;

	public static volatile SingularAttribute<Product, ProductType> productType;

	public static volatile SetAttribute<Product, ProductGroup> groups ;

	public static volatile SetAttribute<Product, Discount> discounts ;

	public static volatile SingularAttribute<Product, BigDecimal> qty;

	public static volatile SingularAttribute<Product, BigDecimal> employeePrice;

	public static volatile SingularAttribute<Product, BarRestauState> productState;
}
