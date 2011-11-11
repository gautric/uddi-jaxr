/*
 * JBoss, Home of Professional Open Source
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */ 
package org.jboss.example.uddi.publisher;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.factory.WSDLFactory;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.FindQualifier;
import javax.xml.registry.RegistryService;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.ServiceBinding;

import org.jboss.logging.Logger;

/**
 * 
 * Publish a simple WSDL in an UDDI Registry as described in 
 * (<a href="http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm">Using WSDL in a UDDI Registry, Version 2.0.2</a>
 * 
 * LIMITATIONS OF THIS EXAMPLE
 * - simple WSDL = no import, 1 portType, 1 binding with protocol SOAP and transport HTTP
 * - access point = we assume the wsdlURL is built as "accessPointURL" + "?wsdl" 
 * - Canonicals tModels described in appendix B of the above document should be inserted 
 *   in the UDDI Registry (see canonicalTModels.ddl at the root of this project) 
 * 
 * @author <a href="mailto:noel.rocher@jboss.org">Noel Rocher</a>
 *
 */
public class SimpleWSDLPublisher
{	

	private final static Collection FIND_QUALIFIERS;

	
	private Connection         connection = null;
	private RegistryService      registry = null;
	private BusinessLifeCycleManager blcm = null;
	private BusinessQueryManager      bqm = null;
	private String                wsdlURL = "";
	private Definition               wsdl = null;
	private String     businessEntityName = "";
	
	// utility class
	WSDLToUDDI wsdlToUddi = null;
	
	// provide logging
	private final Logger log = Logger.getLogger(SimpleWSDLPublisher.class);

	
	// init findQualifier
	static
	{
		// Setting FindQualifiers to 'exactNameMatch'
		FIND_QUALIFIERS = new ArrayList(1);
		FIND_QUALIFIERS.add(FindQualifier.CASE_SENSITIVE_MATCH);;
	}
	
	

	/**
	 * arg0 = inquiry URL
	 * arg1 = publish URL
	 * arg2 = user id
	 * arg3 = password
	 * arg4 = wsdl URL
	 * arg5 = business Entity name
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{		
		if (args.length != 6)
		{
			System.out.println("Error 6 args expected got " + args.length);
			for (int i=0;i<args.length;i++) System.out.println(args[i]);
			System.out.println("SimpleWSDLPublisher usage :");
			System.out.println("\tjava -cp \"<classpath>\" SimpleWSDLPublisher inquiryURL publishURL userid password wsdlURL businessEntityName");
			System.out.println("\tType \"null\" for an empty string.");
		}
		else
		{
			try
			{
				SimpleWSDLPublisher app = new SimpleWSDLPublisher();	

				// replace null by blank
				for (int i=0;i<args.length;i++) args[i]=args[i].replaceAll("null","");

				app.log.info("pulishing :\n" + 
						"\t uddi inquiry URL = " + args[0] + "\n" +
						"\t uddi publish URL = " + args[1] + "\n" +
						"\t uddi user id     = " + args[2] + "\n" +
						"\t uddi password    = " + args[3] + "\n" +
						"\t wsdl URL         = " + args[4] + "\n" +
						"\t business entity  = " + args[5] + "\n ...");
				
				System.setProperty("javax.xml.registry.ConnectionFactoryClass", "org.apache.ws.scout.registry.ConnectionFactoryImpl");
				System.setProperty("juddi.proxy.transportClass", "org.jboss.jaxr.juddi.transport.SaajTransport");

				// connection
				Properties props = new Properties();
				props.setProperty("javax.xml.registry.queryManagerURL", args[0]);
				props.setProperty("javax.xml.registry.lifeCycleManagerURL", args[1]);
				ConnectionFactory factory = ConnectionFactory.newInstance();
				factory.setProperties(props);
				app.setConnection( factory.createConnection()  );

				// credential
				PasswordAuthentication passwdAuth = new PasswordAuthentication(args[2], args[3].toCharArray());
				Set creds = new HashSet();
				creds.add(passwdAuth);
				app.getConnection().setCredentials(creds);
				
				app.setWsdlURL(args[4]);
				app.setWsdl( WSDLFactory.newInstance().newWSDLReader().readWSDL(args[4])  );
				app.setBusinessEntityName(args[5]);
				app.init();
				app.publish();
				System.out.println("Done.");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	
	// CTOR: set the system property for the connection factory
	public SimpleWSDLPublisher()
	{
//	      System.setProperty("javax.xml.registry.ConnectionFactoryClass",
//          "org.apache.ws.scout.registry.ConnectionFactoryImpl");
	}
	
	/**
	 * initialize some useful attributes
	 * @throws Exception
	 */
	public void init() throws Exception
	{
		this.registry = connection.getRegistryService();
		this.blcm = this.registry.getBusinessLifeCycleManager();
		this.bqm = this.registry.getBusinessQueryManager();
		this.wsdlToUddi = new WSDLToUDDI(bqm, blcm);
	}
	
	/**
	 * create the current wsdl as a businessService in the current business entity
	 * @throws Exception
	 */
	public void publish() throws Exception
	{

		Organization org = this.findOrCreateOrganization(this.businessEntityName);
		javax.xml.registry.infomodel.Service service = this.findOrCreateService(org);

		Concept portType=this.findOrCreatePortType();
		Concept binding=this.findOrCreateBinding(portType);
//		ServiceBinding serviceBinding = this.findOrCreateServiceBinding(service, binding, portType);
		this.findOrCreateServiceBinding(service, binding, portType);

//		service.addServiceBinding(serviceBinding);
//		org.addService(service);
//		Collection orgs = new ArrayList(1);
//		orgs.add(org);
//		this.blcm.saveOrganizations(orgs);
	}


	
	
	
	
	/**
	 * find or create the Port Type and return it
	 * @throws Exception
	 */
	private Concept findOrCreatePortType() throws Exception
	{
		Concept result=null;
		
		log.info("Port Type ...");
		
		// first obtain a concept from the wsdl (assuming only one porttype)
		Object key = wsdl.getPortTypes().keySet().iterator().next();
		result = wsdlToUddi.getPortTypeConcept((PortType)wsdl.getPortTypes().get(key), this.wsdlURL);
		Collection names = new ArrayList(1);
		names.add(result.getName().getLocalizedStrings().iterator().next().toString());

		BulkResponse response = bqm.findConcepts(FIND_QUALIFIERS, names, result.getClassifications(), null, null);
		
		if (response != null && response.getCollection().size() > 0)
		{
			result = (Concept)response.getCollection().iterator().next();
			log.info("Port Type already exists. Key = " + result.getKey() + "\".");
		}
		else
		{
			log.info("No PortType \"" + (String)names.iterator().next() + "\" found. Will be created.");
			Collection concepts = new ArrayList(1);
			concepts.add(result);
			response = blcm.saveConcepts( concepts );
			if (response != null && response.getCollection().size() > 0)
			{
				result.setKey((Key)response.getCollection().iterator().next() );
				log.info("Port Type created. Key = " + result.getKey() + "\".");
			}
		}
		return result;
	}
	
	/**
	 * find or create the binding and return it
	 * @throws Exception
	 */
	private Concept findOrCreateBinding(Concept in_portType) throws Exception
	{
		Concept result=null;
		log.info("creating Binding with portType key = "+ in_portType.getKey() +" ...");

		// first obtain a tModel from the wsdl (assuming only one binding)
		Object key = wsdl.getBindings().keySet().iterator().next();
		result = wsdlToUddi.getBindingConcept((Binding)wsdl.getBindings().get(key), this.wsdlURL, in_portType);
		Collection names = new ArrayList(1);
		names.add(result.getName().getLocalizedStrings().iterator().next().toString());

		BulkResponse response = bqm.findConcepts(FIND_QUALIFIERS, names, result.getClassifications(), null, null);
		
		if (response != null && response.getCollection().size() > 0)
		{
			result = (Concept)response.getCollection().iterator().next();
			log.info("Binding already exists. Key = " + result.getKey() + "\".");
		}
		else
		{
			log.info("No Binding \"" + (String)names.iterator().next() + "\" found. Will be created.");
			Collection concepts = new ArrayList(1);
			concepts.add(result);
			response = blcm.saveConcepts( concepts );
			if (response != null && response.getCollection().size() > 0)
			{
				result.setKey((Key)response.getCollection().iterator().next() );
				log.info("Binding created. Key = " + result.getKey() + "\".");
			}
		}

		return result;
	}
	

	/**
	 * find or create the business service
	 * @throws Exception
	 */
	private javax.xml.registry.infomodel.Service findOrCreateService(Organization in_organization) throws Exception
	{
		javax.xml.registry.infomodel.Service result=null;
		log.info("creating Business Service ...");
		
		Object key = wsdl.getServices().keySet().iterator().next();
		result = wsdlToUddi.getService((Service)wsdl.getServices().get(key));
		
		Collection names = new ArrayList(1);
		names.add(result.getName().getLocalizedStrings().iterator().next().toString());
		
		
		BulkResponse response = bqm.findServices(in_organization.getKey(), FIND_QUALIFIERS, names, result.getClassifications(), null);
		
		if (response != null && response.getCollection().size() > 0)
		{
			result = (javax.xml.registry.infomodel.Service)response.getCollection().iterator().next();
			log.info("Business Service already exists. Key = " + result.getKey() + "\".");
		}
		else
		{
			log.info("No Business Service \"" + (String)names.iterator().next() + "\" found. Will be created.");			
			in_organization.addService(result);
			Collection services = new ArrayList(1);
			services.add(result);
			response = blcm.saveServices( services );
			if (response != null && response.getCollection().size() > 0)
			{
				result.setKey((Key)response.getCollection().iterator().next() );
				log.info("Business Service created. Key = " + result.getKey() + "\".");
			}
		}

		return result;
	}


	
	/**
	 * find or create the service binding
	 * @throws Exception
	 */
	private void findOrCreateServiceBinding(javax.xml.registry.infomodel.Service in_service, Concept in_binding, Concept in_portType ) throws Exception
	{
		ServiceBinding result=null;
		ServiceBinding currentBinding=null;
		
		boolean createIt=true;
		
		log.info("creating Binding Template ...");
		
		// first obtain a bindingService from the wsdl
		String accessPoint = wsdlURL.replaceAll("\\?wsdl","");
		result = wsdlToUddi.getServiceBinding(accessPoint, wsdlURL, in_binding, in_portType);

		
		BulkResponse response = bqm.findServiceBindings(in_service.getKey(), FIND_QUALIFIERS, result.getClassifications(), result.getSpecificationLinks() );
		
		if (response != null && response.getCollection().size() > 0)
		{
			Iterator it=response.getCollection().iterator();
			while (it.hasNext() && createIt==true)
			{
				currentBinding = (ServiceBinding)it.next();
				if (currentBinding.getAccessURI().equalsIgnoreCase(accessPoint))
				{
					createIt=false;
					log.info("Service Binding for access point \"" + currentBinding.getAccessURI() +"\" already exists. Key = " + currentBinding.getKey() + "\".");
				}
			}
		}

		if (createIt)
		{
			log.info("No Service Binding for access point \"" + accessPoint + "\" found. Will be created.");

			in_service.addServiceBinding(result);
			
			Collection serviceBindings = new ArrayList(1);
			serviceBindings.add(result);
			response = blcm.saveServiceBindings( serviceBindings );
			if (response != null && response.getCollection().size() > 0)
			{
				result.setKey((Key)response.getCollection().iterator().next() );
				log.info("Service Binding for access point \"" + accessPoint + "\" created. Key = " + result.getKey() + "\".");
			}
		}

		return;
	}
	
	
	
	/**
	 * find or create an organisation
	 * @throws Exception
	 */
	private Organization findOrCreateOrganization(String in_businessEntityName) throws Exception
	{
		Organization result=null;
		log.info("creating Business Entity name = "+ this.businessEntityName +" ...");
		Collection names = new ArrayList(1);
		names.add(this.businessEntityName);
		BulkResponse response = bqm.findOrganizations(FIND_QUALIFIERS, names, null, null, null, null);
		
		if (response != null && response.getCollection().size() > 0)
		{
			result = (Organization)response.getCollection().iterator().next();
			log.info("Business Entity already exists. Key = " + result.getKey() + "\".");
		}
		else
		{
			log.info("No Business Entity \"" + in_businessEntityName + "\" found. Will be created.");
			result = blcm.createOrganization(in_businessEntityName);
			ArrayList organizations = new ArrayList(1);
			organizations.add(result);
			response = blcm.saveOrganizations(organizations);
			if (response != null && response.getCollection().size() > 0)
			{
				result.setKey((Key)response.getCollection().iterator().next() );
				log.info("Business Entity \"" + in_businessEntityName + "\" created. Key = " + result.getKey() + "\".");
			}
		}
		
		return result;
	}
	
	
	
	
	// accessors
	public Connection getConnection()
	{
		return this.connection;
	}

	public void setConnection(Connection in_connection)
	{
		this.connection = in_connection;
	}

	public Definition getWsdl()
	{
		return wsdl;
	}

	public void setWsdl(Definition wsdl)
	{
		this.wsdl = wsdl;
	}

	public String getBusinessEntityName()
	{
		return businessEntityName;
	}

	public void setBusinessEntityName(String businessEntityName)
	{
		this.businessEntityName = businessEntityName;
	}

	public String getWsdlURL()
	{
		return wsdlURL;
	}
	public void setWsdlURL(String wsdlURL)
	{
		this.wsdlURL = wsdlURL;
	}
	
	
}
