package org.jboss.example.ws;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ExampleService extends Remote
{

	public abstract int multiplyByTwo(int in_value) throws RemoteException;

}