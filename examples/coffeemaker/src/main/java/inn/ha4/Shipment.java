package inn.ha4;

import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;

public interface Shipment {
    Context deliver(Context context) throws ContextException, RemoteException;
}
