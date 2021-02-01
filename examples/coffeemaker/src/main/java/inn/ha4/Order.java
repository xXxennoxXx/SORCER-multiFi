package inn.ha4;

import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;

public interface Order {
    Context addIngredient(Context context) throws ContextException, RemoteException;

    Context removeIngredient(Context context) throws ContextException, RemoteException;

    Context replaceIngredient(Context context) throws ContextException, RemoteException;

}
