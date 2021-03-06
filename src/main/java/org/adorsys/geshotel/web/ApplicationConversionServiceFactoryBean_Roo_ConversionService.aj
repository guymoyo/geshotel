// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.web;

import java.lang.String;
import org.adorsys.geshotel.booking.domain.Deposit;
import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceItem;
import org.adorsys.geshotel.booking.domain.OtherService;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.booking.domain.Retrait;
import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.booking.domain.RoomCategory;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.Employee;
import org.adorsys.geshotel.domain.Hotel;
import org.adorsys.geshotel.domain.Payment;
import org.adorsys.geshotel.domain.Service;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.resto.domain.BarRestauInvoice;
import org.adorsys.geshotel.resto.domain.BarRestauPayment;
import org.adorsys.geshotel.resto.domain.BillOfProcurement;
import org.adorsys.geshotel.resto.domain.BillOfSale;
import org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory;
import org.adorsys.geshotel.resto.domain.Discount;
import org.adorsys.geshotel.resto.domain.Inventory;
import org.adorsys.geshotel.resto.domain.InventoryItem;
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.adorsys.geshotel.resto.domain.KitchenOrderItem;
import org.adorsys.geshotel.resto.domain.ParentGroup;
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.springframework.core.convert.converter.Converter;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.DepositConverter implements Converter<Deposit, String> {
        public String convert(Deposit deposit) {
            return new StringBuilder().append(deposit.getLabel()).append(" ").append(deposit.getDepositvalue()).append(" ").append(deposit.getDateDeposit()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.InvoiceConverter implements Converter<Invoice, String> {
        public String convert(Invoice invoice) {
            return new StringBuilder().append(invoice.getInvoiceAmount()).append(" ").append(invoice.getValueAdddedTax()).append(" ").append(invoice.getOtherTaxes()).append(" ").append(invoice.getAmountDue()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.InvoiceItemConverter implements Converter<InvoiceItem, String> {
        public String convert(InvoiceItem invoiceItem) {
            return new StringBuilder().append(invoiceItem.getItemNumber()).append(" ").append(invoiceItem.getItemName()).append(" ").append(invoiceItem.getQty()).append(" ").append(invoiceItem.getUnitPrice()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.OtherServiceConverter implements Converter<OtherService, String> {
        public String convert(OtherService otherService) {
            return new StringBuilder().append(otherService.getName()).append(" ").append(otherService.getAmount()).append(" ").append(otherService.getServiceDesc()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.ReservationConverter implements Converter<Reservation, String> {
        public String convert(Reservation reservation) {
            return new StringBuilder().append(reservation.getCheckInDate()).append(" ").append(reservation.getCheckOutDate()).append(" ").append(reservation.getAmount()).append(" ").append(reservation.getReduction()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.RetraitConverter implements Converter<Retrait, String> {
        public String convert(Retrait retrait) {
            return new StringBuilder().append(retrait.getLabel()).append(" ").append(retrait.getRetraitvalue()).append(" ").append(retrait.getDateRetrait()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.RoomConverter implements Converter<Room, String> {
        public String convert(Room room) {
            return new StringBuilder().append(room.getRoomNumber()).append(" ").append(room.getPhoneNumber()).append(" ").append(room.getCoordinates()).append(" ").append(room.getCapacity()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.RoomCategoryConverter implements Converter<RoomCategory, String> {
        public String convert(RoomCategory roomCategory) {
            return new StringBuilder().append(roomCategory.getTitle()).append(" ").append(roomCategory.getDescription()).append(" ").append(roomCategory.getPrice()).append(" ").append(roomCategory.getPriceMin()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.CustomerConverter implements Converter<Customer, String> {
        public String convert(Customer customer) {
            return new StringBuilder().append(customer.getFirstName()).append(" ").append(customer.getLastName()).append(" ").append(customer.getMaidenName()).append(" ").append(customer.getFullName()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.EmployeeConverter implements Converter<Employee, String> {
        public String convert(Employee employee) {
            return new StringBuilder().append(employee.getFullName()).append(" ").append(employee.getIdentifier()).append(" ").append(employee.getBornDate()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.HotelConverter implements Converter<Hotel, String> {
        public String convert(Hotel hotel) {
            return new StringBuilder().append(hotel.getName()).append(" ").append(hotel.getAddress()).append(" ").append(hotel.getCodePostal()).append(" ").append(hotel.getCity()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.PaymentConverter implements Converter<Payment, String> {
        public String convert(Payment payment) {
            return new StringBuilder().append(payment.getDatePayemment()).append(" ").append(payment.getAmount()).append(" ").append(payment.getNote()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.ServiceConverter implements Converter<Service, String> {
        public String convert(Service service) {
            return new StringBuilder().append(service.getTitle()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.UserAccountConverter implements Converter<UserAccount, String> {
        public String convert(UserAccount userAccount) {
            return new StringBuilder().append(userAccount.getIdentifier()).append(" ").append(userAccount.getPassword()).append(" ").append(userAccount.getLastName()).append(" ").append(userAccount.getEntryDate()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.BarRestauInvoiceConverter implements Converter<BarRestauInvoice, String> {
        public String convert(BarRestauInvoice barRestauInvoice) {
            return new StringBuilder().append(barRestauInvoice.getBarRestauInvoiceKey()).append(" ").append(barRestauInvoice.getCreationDate()).append(" ").append(barRestauInvoice.getInvoicePath()).append(" ").append(barRestauInvoice.getNote()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.BarRestauPaymentConverter implements Converter<BarRestauPayment, String> {
        public String convert(BarRestauPayment barRestauPayment) {
            return new StringBuilder().append(barRestauPayment.getAmountDue()).append(" ").append(barRestauPayment.getAmountIn()).append(" ").append(barRestauPayment.getAmountOut()).append(" ").append(barRestauPayment.getNote()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.BillOfProcurementConverter implements Converter<BillOfProcurement, String> {
        public String convert(BillOfProcurement billOfProcurement) {
            return new StringBuilder().append(billOfProcurement.getSellKey()).append(" ").append(billOfProcurement.getTotalAmount()).append(" ").append(billOfProcurement.getTax()).append(" ").append(billOfProcurement.getDiscount()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.BillOfSaleConverter implements Converter<BillOfSale, String> {
        public String convert(BillOfSale billOfSale) {
            return new StringBuilder().append(billOfSale.getSellKey()).append(" ").append(billOfSale.getTotalAmount()).append(" ").append(billOfSale.getTax()).append(" ").append(billOfSale.getDiscount()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.CustomerIdentifierHistoryConverter implements Converter<CustomerIdentifierHistory, String> {
        public String convert(CustomerIdentifierHistory customerIdentifierHistory) {
            return new StringBuilder().append(customerIdentifierHistory.getHistoryKey()).append(" ").append(customerIdentifierHistory.getIdentifierValue()).append(" ").append(customerIdentifierHistory.getStoringDate()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.DiscountConverter implements Converter<Discount, String> {
        public String convert(Discount discount) {
            return new StringBuilder().append(discount.getTitle()).append(" ").append(discount.getPercentage()).append(" ").append(discount.getAmount()).append(" ").append(discount.getValidFrom()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.InventoryConverter implements Converter<Inventory, String> {
        public String convert(Inventory inventory) {
            return new StringBuilder().append(inventory.getTitle()).append(" ").append(inventory.getInventoryDate()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.InventoryItemConverter implements Converter<InventoryItem, String> {
        public String convert(InventoryItem inventoryItem) {
            return new StringBuilder().append(inventoryItem.getDesignation()).append(" ").append(inventoryItem.getEntry()).append(" ").append(inventoryItem.getSales()).append(" ").append(inventoryItem.getInitialStock()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.KitchenOrderConverter implements Converter<KitchenOrder, String> {
        public String convert(KitchenOrder kitchenOrder) {
            return new StringBuilder().append(kitchenOrder.getDateOfOrdering()).append(" ").append(kitchenOrder.getKitchenOrderPath()).append(" ").append(kitchenOrder.getKitchenOrderNumber()).append(" ").append(kitchenOrder.getKitchenOrderKey()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.KitchenOrderItemConverter implements Converter<KitchenOrderItem, String> {
        public String convert(KitchenOrderItem kitchenOrderItem) {
            return new StringBuilder().append(kitchenOrderItem.getKitchenOrderItemKey()).append(" ").append(kitchenOrderItem.getQty()).append(" ").append(kitchenOrderItem.getDateOfOrdering()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.ParentGroupConverter implements Converter<ParentGroup, String> {
        public String convert(ParentGroup parentGroup) {
            return new StringBuilder().append(parentGroup.getParentGroupKey()).append(" ").append(parentGroup.getProductGroupKey()).append(" ").append(parentGroup.getProductGroupParentKey()).append(" ").append(parentGroup.getDescription()).toString();
        }
        
    }
    
    static class org.adorsys.geshotel.web.ApplicationConversionServiceFactoryBean.PeriodConfConverter implements Converter<PeriodConf, String> {
        public String convert(PeriodConf periodConf) {
            return new StringBuilder().append(periodConf.getTitle()).append(" ").append(periodConf.getValidFrom()).append(" ").append(periodConf.getValidTo()).append(" ").append(periodConf.getOpeningTime()).toString();
        }
        
    }
    
}
