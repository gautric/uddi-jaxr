/*
 * JBoss, Home of Professional Open Source
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.example;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.FindQualifier;
import javax.xml.registry.RegistryService;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.Stub;

import org.jboss.example.ws.ExampleService;
import org.jboss.ws.core.jaxrpc.client.ServiceFactoryImpl;

/**
 * A client that is using the entity name and service name to
 * retrieve access points to invoke a web service
 * 
 * @author <a href="mailto:noel.rocher@jboss.org">Noel Rocher</a>
 */
public class Client
{
   private final static Collection FIND_QUALIFIERS;

   private Connection connection = null;

   private RegistryService registry = null;

   private BusinessLifeCycleManager blcm = null;

   private BusinessQueryManager bqm = null;

   private Key businessEntityKey = null;

   private Key businessServiceKey = null;

   // init findQualifier
   static
   {
      // Setting FindQualifiers to 'exactNameMatch'
      FIND_QUALIFIERS = new ArrayList(1);
      FIND_QUALIFIERS.add(FindQualifier.CASE_SENSITIVE_MATCH);;
   }

   /**
    * arg0 = inquiry URL
    * arg1 = publish URL // required but not used
    * arg2 = business Entity name
    * arg3 = business Service name
    */
   public static void main(String[] args)
   {
      Client app = null;
      try
      {
         // replace null by blank
         for (int i = 0; i < args.length; i++)
            args[i] = args[i].replaceAll("null", "");

         app = new Client();
         app.init(args[0], args[1], args[2], args[3]);
         app.process();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * initialize
    * @param in_inquiryURL
    * @param in_businessEntityName
    * @param in_businessServiceName
    */
   public void init(String in_inquiryURL, String in_publishURL, String in_businessEntityName,
         String in_businessServiceName) throws Exception
   {
      System.setProperty("javax.xml.registry.ConnectionFactoryClass",
            "org.apache.ws.scout.registry.ConnectionFactoryImpl");
      System.setProperty("juddi.proxy.transportClass", "org.jboss.jaxr.juddi.transport.SaajTransport");

      // connection
      Properties props = new Properties();
      props.setProperty("javax.xml.registry.queryManagerURL", in_inquiryURL);
      props.setProperty("javax.xml.registry.lifeCycleManagerURL", in_publishURL);
      ConnectionFactory factory = ConnectionFactory.newInstance();
      factory.setProperties(props);
      this.connection = factory.createConnection();
      this.registry = connection.getRegistryService();
      this.blcm = this.registry.getBusinessLifeCycleManager();
      this.bqm = this.registry.getBusinessQueryManager();

      this.initServiceKey(in_businessEntityName, in_businessServiceName);
   }

   /**
    * access each web services found in the UDDI Registry
    * @throws Exception
    */
   public void process() throws Exception
   {
      String[] accessPoints = getAccessPoints();

      if (accessPoints != null && accessPoints.length > 0)
      {

         ServiceFactoryImpl factory = (ServiceFactoryImpl) ServiceFactory.newInstance();
         QName serviceName = new QName("http://org.jboss.example/ExampleService", "ExampleService");
         URL jaxRpcMapping = new File("output/generated/jaxrpc-mapping.xml").toURL();
         URL wsdlLocation = new URL(accessPoints[0] + "?wsdl");
         Service service = factory.createService(wsdlLocation, serviceName, jaxRpcMapping);
         ExampleService endpoint = (ExampleService) service.getPort(ExampleService.class);

         for (int i = 0; i < accessPoints.length; i++)
         {
            Stub stub = (Stub) endpoint;
            stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, accessPoints[i]);

            System.out
                  .println(" access \"" + accessPoints[i] + "\" multiply 100 by 2 = " + endpoint.multiplyByTwo(100));
         }
      }
      else
      {
         System.out.println("No access point available");
      }
   }

   private String[] getAccessPoints() throws Exception
   {
      String[] result = null;
      int i = 0;

      // Find the  binding template
      BulkResponse response = bqm.findServiceBindings(this.businessServiceKey, FIND_QUALIFIERS, null, null);

      if (response != null && response.getCollection().size() > 0)
      {
         ServiceBinding bindingReturned = null;
         result = new String[response.getCollection().size()];
         Iterator iterator = response.getCollection().iterator();
         while (iterator.hasNext())
         {
            bindingReturned = (ServiceBinding) iterator.next();
            result[i] = bindingReturned.getAccessURI();
            i++;
         }
      }
      else
      {
         throw new Exception("No Binding Template found.");
      }

      return result;
   }

   private void initServiceKey(String in_businessEntityName, String in_businessServiceName) throws Exception
   {
      // Find the  BE
      Collection names = new ArrayList(1);
      names.add(in_businessEntityName);
      BulkResponse response = bqm.findOrganizations(FIND_QUALIFIERS, names, null, null, null, null);

      if (response != null && response.getCollection().size() > 0)
      {
         Organization result = (Organization) response.getCollection().iterator().next();
         this.businessEntityKey = result.getKey();
      }
      else
      {
         throw new Exception("No business entity named \"" + in_businessEntityName + "\"");
      }

      // Find the  service
      names.clear();
      names.add(in_businessServiceName);

      response = bqm.findServices(this.businessEntityKey, FIND_QUALIFIERS, names, null, null);

      if (response != null && response.getCollection().size() > 0)
      {
         javax.xml.registry.infomodel.Service result = (javax.xml.registry.infomodel.Service) response.getCollection()
               .iterator().next();
         this.businessServiceKey = result.getKey();
      }
      else
      {
         throw new Exception("No business service named \"" + in_businessServiceName + "\"");
      }
   }

}
