package ttzv.demo.ecommerce.service;

import ttzv.demo.ecommerce.dto.Purchase;
import ttzv.demo.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

}
