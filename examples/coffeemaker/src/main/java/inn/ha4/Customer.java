package inn.ha4;

import sorcer.service.Mogram;
import sorcer.service.Task;

import static sorcer.co.operator.inVal;
import static sorcer.co.operator.outVal;
import static sorcer.eo.operator.*;


public class Customer {

    public Mogram createExertionBlock() throws Exception {

        Task payment = task("payment", sig("payment", PaymentService.class), context(
                inVal("method", "debitCard"),
                inVal("card/number", "1111 1111 1111 1111")
        ));

        Task order = task("order", sig("makeOrder", OrderService.class), context(
                inVal("sugar", "6"),
                inVal("milk", "4"),
                outVal("assembly")
        ));

        Task shipment = task("shipment", sig("deliver", ShipmentService.class), context(
                inVal("address/street", "Warsaw Street"),
                inVal("address/number", 69)
        ));

        return job(order, payment, shipment,
                pipe(outPoint(order, "order/price"), inPoint(payment, "payment/price")),
                pipe(outPoint(payment, "payment/status"), inPoint(shipment, "delivery/paid")));
    }
}

