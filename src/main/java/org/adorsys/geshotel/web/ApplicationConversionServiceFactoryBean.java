package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.BrTable;
import org.adorsys.geshotel.resto.domain.CashRegister;
import org.adorsys.geshotel.resto.domain.Procurement;
import org.adorsys.geshotel.resto.domain.ProcurementItem;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.adorsys.geshotel.resto.domain.Provider;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.adorsys.geshotel.resto.domain.Supplier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;
import org.springframework.util.Assert;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}
	
    public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new InvoiceConverter());
        registry.addConverter(new InvoiceItemConverter());
        registry.addConverter(new OtherServiceConverter());
        registry.addConverter(new ReservationConverter());
        registry.addConverter(new RoomConverter());
        registry.addConverter(new RoomCategoryConverter());
        registry.addConverter(new CustomerConverter());
        registry.addConverter(new EmployeeConverter());
        registry.addConverter(new HotelConverter());
        registry.addConverter(new PaymentConverter());
        registry.addConverter(new ServiceConverter());
        registry.addConverter(new UserAccountConverter());
        registry.addConverter(new BillOfProcurementConverter());
        registry.addConverter(new BillOfSaleConverter());
        registry.addConverter(new BrTableConverter());
        registry.addConverter(new CustomerIdentifierHistoryConverter());
        registry.addConverter(new DiscountConverter());
        registry.addConverter(new InventoryConverter());
        registry.addConverter(new InventoryItemConverter());
        registry.addConverter(new ParentGroupConverter());
        registry.addConverter(new PeriodConfConverter());
        registry.addConverter(new ProcurementItemConverter());
        registry.addConverter(new ProductConverter());
        registry.addConverter(new ProductGroupConverter());
        registry.addConverter(new ProductTypeConverter());
        registry.addConverter(new ProviderConverter());
        registry.addConverter(new SaleConverter());
        registry.addConverter(new SaleItemConverter());
//        registry.addConverter(new StringToSaleConverter());
        registry.addConverter(new StringToProductConverter());
        registry.addConverter(new CashRegisterConverter());
        
    }
	
	
	static class StringToSaleConverter implements Converter<String, Sale> {
		@Override
		public Sale convert(String saleKey) {
			System.out.println(" Sale Key  :  "+saleKey);
			Assert.notNull(saleKey, "Null value detected");
			Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getResultList().iterator().next();
			System.out.println("\n \n Sale found :"+sale.toString()+" \n ");
			return sale;
		}
	}
	
	static class StringToProductConverter implements Converter<String, Product> {

		@Override
		public Product convert(String productKey) {
			Assert.notNull(productKey, "Null param is not required");
			System.out.println("Id in the ApplicationConverterBean : "+productKey);
			try {
				Long productId = new Long(productKey);
				return Product.findProduct(productId);
			} catch (NumberFormatException e) {
				//this mean that the productKey is the Id and not.
				System.out.println("\n Unable to convert "+productKey+" to a java.lang.Long id. \n \t Converting the productKey to a Product.");
			}
			return Product.findProductsByProductKeyEquals(productKey).getSingleResult();
		}
	}

    static class SaleConverter implements Converter<Sale, String> {
        public String convert(Sale sale) {
            return new StringBuilder().append("Sale Owner : "+sale.getNote()).append(", \n").append(sale.getSaleKey()).append(" ").append(sale.getTotalAmount()).append(" ").append(sale.getTax()).append(" ").append(sale.getRecordDate()).toString();
        }
        
    }
    
    static class ProcurementConverter implements Converter<Procurement, String> {
        public String convert(Procurement procurement) {
            return new StringBuilder().append(procurement.getProcurementKey()).append(" ").append(procurement.getDateOfProcurement()).append(" ").append(procurement.getAmount()).toString();
        }
        
    }
    
    static class CashRegisterConverter implements Converter<CashRegister, String> {
        public String convert(CashRegister cashRegister) {
            return new StringBuilder().append(cashRegister.getCashRegisterKey()).append(" ").append(cashRegister.getDateOfOpening()).append(" ").append(cashRegister.getDateOfClosing()).append(" ").append(cashRegister.getCashFund()).toString();
        }
        
    }

    static class ProductTypeConverter implements Converter<ProductType, String> {
        public String convert(ProductType productType) {
            return new StringBuilder().append(productType.getTitle()).append(" : ").append(productType.getDescription()).append(" ").append(productType.getDateOfStore()).toString();
        }
    }
    static class ProductGroupConverter implements Converter<ProductGroup, String> {
        public String convert(ProductGroup productGroup) {
            return new StringBuilder().append(" ").append(productGroup.getTitle()).append(" ").append(productGroup.getDescription()).toString();
        }
        
    }
    static class ProcurementItemConverter implements Converter<ProcurementItem, String> {
        public String convert(ProcurementItem procurementItem) {
            return new StringBuilder().append(" ").append(procurementItem.getQty()).append(" ").append(procurementItem.getUnitPrice()).append(" ").append(procurementItem.getAmount()).toString();
        }
        
    }
    
    static class ProductConverter implements Converter<Product, String> {
        public String convert(Product product) {
            return new StringBuilder().append(" ").append(product.getTitle()).append(" ").append(product.getDescription()).append(" ").append(product.getPrice()).toString();
        }
        
    }
    
    static class ProviderConverter implements Converter<Provider, String> {
        public String convert(Provider provider) {
            return new StringBuilder().append(" ").append(provider.getFullName()).append(" ").append(provider.getIdentifier()).toString();
        }
        
    }
    
    static class SaleItemConverter implements Converter<SaleItem, String> {
        public String convert(SaleItem saleItem) {
            return new StringBuilder().append(" ").append(saleItem.getQty()).append(" ").append(saleItem.getUnitPrice()).append(" ").append(saleItem.getAmount()).toString();
        }
        
    }
    
    static class SupplierConverter implements Converter<Supplier, String> {
        public String convert(Supplier supplier) {
            return new StringBuilder().append(" ").append(supplier.getIdentifier()).append(" ").append(supplier.getDesignation()).append(" ").append(supplier.getPhoneNumber()).toString();
        }
        
    }

    static class BrTableConverter implements Converter<BrTable, String> {
        public String convert(BrTable brTable) {
            return new StringBuilder().append(" ").append(brTable.getTableNumber()).append(" ").append(brTable.getDescription()).toString();
        }
        
    }
}
