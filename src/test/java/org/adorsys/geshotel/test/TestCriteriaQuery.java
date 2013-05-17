package org.adorsys.geshotel.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.ProductDataOnDemand;
import org.adorsys.geshotel.resto.domain.ProductGroupDataOnDemand;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.adorsys.geshotel.resto.domain.ProductTypeDataOnDemand;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*.xml"})
@Transactional
public class TestCriteriaQuery {
	public static Logger  log = Logger.getLogger("product_log");
	@Autowired
	private ProductDataOnDemand productDataOnDemand;
	@Autowired
	private ProductTypeDataOnDemand productTypeDataOnDemand;
	@Autowired
	private ProductGroupDataOnDemand productGroupDataOnDemand ;
	private List<Product> products ;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	@Before
	public void before(){
		initProducts(10);
	}

	@Test
	public void testFindAllCQ() {
		Assert.assertTrue("Not Inited", this.products.size() >= 3);
		List<Product> allProducts = Product.findAllCQ();
    	log.log(Level.INFO, allProducts.toString());
	}
	public void initProducts(int number){
		if(number <= 0) number = 3;
		this.products = new ArrayList<Product>();
		for (int i = 0; i < number; i++) {
			Product product = productDataOnDemand.getRandomProduct();
			product.setProductType(productTypeDataOnDemand.getRandomProductType());
			product.persist();
			products.add(product);
		}
	}
	public Product newProduct(ProductType type,BigDecimal amount){
		Product product = new Product();
		product.setPrice(amount);
		product.setTitle(RandomStringUtils.randomAlphabetic(6));
		product.setQty(new BigDecimal(4));
		return product;
	}
}
