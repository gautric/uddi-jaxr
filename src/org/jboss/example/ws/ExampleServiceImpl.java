/*
 * JBoss, Home of Professional Open Source
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */ 
package org.jboss.example.ws;

import java.rmi.RemoteException;

/**
 * @author <a href="mailto:noel.rocher@jboss.org">Noel Rocher</a>
 *
 */
public class ExampleServiceImpl implements ExampleService
{
	/* (non-Javadoc)
	 * @see org.jboss.example.ws.ExampleService#multiplyByTwo(int)
	 */
	public int multiplyByTwo(int in_value) throws RemoteException
	{
		
		System.out.println("Hello");
		return 2 * in_value;
	}
}
