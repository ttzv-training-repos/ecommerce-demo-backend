package ttzv.demo.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttzv.demo.ecommerce.dto.Purchase;
import ttzv.demo.ecommerce.dto.PurchaseResponse;
import ttzv.demo.ecommerce.entity.Customer;
import ttzv.demo.ecommerce.entity.Order;
import ttzv.demo.ecommerce.entity.OrderItem;
import ttzv.demo.ecommerce.repository.CustomerRepository;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        String orderTrackingNumber = generateTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());
        Customer customer = purchase.getCustomer();
        String customerEmail = customer.getEmail();
        Customer customerFromDb = customerRepository.findByEmail(customerEmail);
        if (customerFromDb != null) {
            customer = customerFromDb;
        }
        customer.add(order);
        customerRepository.save(customer);
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
