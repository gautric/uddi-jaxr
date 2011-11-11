/*
 * JBoss, Home of Professional Open Source
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */ 
package org.jboss.example.uddi.publisher;

import javax.wsdl.Binding;
import javax.wsdl.PortType;
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.infomodel.Classification;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.registry.infomodel.SpecificationLink;



/**
 * @author <a href="mailto:noel.rocher@jboss.org">Noel Rocher</a>
 *
 */
public class WSDLToUDDI
{
	// uddi keys
	private final static String UUID_HTTP_PROTOCOL  = "uuid:6e10b91b-babc-3442-b8fc-5a3c8fde0794";
	private final static String UUID_PORT_TYPE      = "uuid:082b0851-25d8-303c-b332-f24a6d53e38e";
	private final static String UUID_PROTOCOL       = "uuid:4dc74177-7806-34d9-aecd-33c57dc3a865";
	private final static String UUID_SOAP_PROTOCOL  = "uuid:aa254698-93de-3870-8df3-a5c075d64a0e";
	private final static String UUID_TRANSPORT      = "uuid:e5c43936-86e4-37bf-8196-1d04b35c0099";
	private final static String UUID_WSDL_ADDRESS   = "uuid:ad61de98-4db8-31b2-a299-a2373dc97212";
	private final static String UUID_WSDL_TYPE      = "uuid:6e090afa-33e5-36eb-81b7-1ca18373f457";
	private final static String UUID_XML_LOCAL_NAME = "uuid:2ec65201-9109-3919-9bec-c9dbefcaccf6";
	private final static String UUID_XML_NAMESPACE  = "uuid:d01987d1-ab2e-3013-9be2-2a66eb99d824";

	private final static String UUID_TYPE  			= "uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4";	
	private final static String UDDI_TYPE_NAME  	= "uuid-org:types";

	
	// jaxr concepts constants
	private final Concept HTTP_PROTOCOL;
	private final Concept SOAP_PROTOCOL;
	private final Concept WSDL_ADDRESS;

	// jaxr classification scheme constants
	private final ClassificationScheme PORT_TYPE;
	private final ClassificationScheme PROTOCOL;
	private final ClassificationScheme TRANSPORT;
	private final ClassificationScheme WSDL_TYPE;
	private final ClassificationScheme XML_LOCAL_NAME;
	private final ClassificationScheme XML_NAMESPACE;
	private final ClassificationScheme TYPE;
	
	// jaxr classification constants (simplification. we should have look at the wsd binding extension)
	private final Classification BINDING_PROTOCOL;
	private final Classification BINDING_TRANSPORT;

	
	// used for objects creation
	BusinessLifeCycleManager blcm=null;
	
	
	// CTOR : set all constants
	public WSDLToUDDI(BusinessQueryManager in_bqm, BusinessLifeCycleManager in_blcm) throws Exception
	{
		blcm = in_blcm;
		
		PORT_TYPE = (ClassificationScheme)in_bqm.getRegistryObject(UUID_PORT_TYPE, blcm.CLASSIFICATION_SCHEME);
		PROTOCOL = (ClassificationScheme)in_bqm.getRegistryObject(UUID_PROTOCOL, blcm.CLASSIFICATION_SCHEME);
		TRANSPORT = (ClassificationScheme)in_bqm.getRegistryObject(UUID_TRANSPORT, blcm.CLASSIFICATION_SCHEME);
		WSDL_TYPE = (ClassificationScheme)in_bqm.getRegistryObject(UUID_WSDL_TYPE, blcm.CLASSIFICATION_SCHEME);
		XML_LOCAL_NAME = (ClassificationScheme)in_bqm.getRegistryObject(UUID_XML_LOCAL_NAME, blcm.CLASSIFICATION_SCHEME);
		XML_NAMESPACE = (ClassificationScheme)in_bqm.getRegistryObject(UUID_XML_NAMESPACE, blcm.CLASSIFICATION_SCHEME);
		TYPE = (ClassificationScheme)in_bqm.getRegistryObject(UUID_TYPE, blcm.CLASSIFICATION_SCHEME);

		WSDL_ADDRESS = (Concept)in_bqm.getRegistryObject(UUID_WSDL_ADDRESS, blcm.CONCEPT);
		HTTP_PROTOCOL = (Concept)in_bqm.getRegistryObject(UUID_HTTP_PROTOCOL, blcm.CONCEPT);
		SOAP_PROTOCOL = (Concept)in_bqm.getRegistryObject(UUID_SOAP_PROTOCOL, blcm.CONCEPT);
		
		BINDING_PROTOCOL  = blcm.createClassification(PROTOCOL, blcm.createInternationalString("SOAP protocol"), SOAP_PROTOCOL.getKey().getId());
		BINDING_TRANSPORT = blcm.createClassification(TRANSPORT, blcm.createInternationalString("HTTP protocol"), HTTP_PROTOCOL.getKey().getId());		
	}

	
	
	/** return the Concept from the wsdl portType */
	public Concept getPortTypeConcept(PortType in_portType, String in_wsdlURL) throws Exception
	{
		Concept result=null;
		
		String portTypeName = (String)in_portType.getQName().getLocalPart();
		result = blcm.createConcept( null, portTypeName ,"" );
		ExternalLink wsdlLink = blcm.createExternalLink(
					in_wsdlURL + 
						"#xmlns(wsdl=http://schemas.xmlsoap.org/wsdl/) xpointer(/wsdl:definitions/wsdl:portType[@name=\"" + 
						portTypeName + 
						"\"])",
					"WSDL Port Type definition");
		result.addExternalLink(wsdlLink); 
		
		result.addClassification(blcm.createClassification( XML_NAMESPACE, blcm.createInternationalString("portType namespace"), (String)in_portType.getQName().getNamespaceURI())  );
		result.addClassification(blcm.createClassification( WSDL_TYPE, blcm.createInternationalString("WSDL type"), "portType")  );
		
		return result;
	}

	
	/** return the Concept from the wsdl binding */
	public Concept getBindingConcept(Binding in_binding, String in_wsdlURL, Concept in_portType) throws Exception
	{
		Concept result=null;
		
		String bindingName = (String)in_binding.getQName().getLocalPart();
		result = blcm.createConcept( null, bindingName ,"" );
		ExternalLink wsdlLink = blcm.createExternalLink(
					in_wsdlURL + 
						"#xmlns(wsdl=http://schemas.xmlsoap.org/wsdl/) xpointer(/wsdl:definitions/wsdl:binding[@name=\"" + 
						bindingName + 
						"\"])",
					"WSDL Binding definition");
		result.addExternalLink(wsdlLink); 
		
		result.addClassification(BINDING_PROTOCOL);
		result.addClassification(BINDING_TRANSPORT);
		result.addClassification(blcm.createClassification( XML_NAMESPACE, blcm.createInternationalString("binding namespace"), (String)in_binding.getQName().getNamespaceURI())  );
		result.addClassification(blcm.createClassification( WSDL_TYPE, blcm.createInternationalString("WSDL type"), "binding")  );
		result.addClassification(blcm.createClassification( PORT_TYPE, blcm.createInternationalString("portType reference"), in_portType.getKey().getId())  );
		result.addClassification(blcm.createClassification( TYPE, UDDI_TYPE_NAME, "wsdlSpec")  );
		
		return result;
	}

	
	/** return the registry Service from the wsdl service */
	public Service getService(javax.wsdl.Service in_service) throws Exception
	{
		Service result=null;
		
		String serviceName = (String)in_service.getQName().getLocalPart();
		result = blcm.createService(serviceName);
		
		result.addClassification(blcm.createClassification( XML_NAMESPACE, blcm.createInternationalString("service namespace"), (String)in_service.getQName().getNamespaceURI())  );
		result.addClassification(blcm.createClassification( WSDL_TYPE, blcm.createInternationalString("WSDL type"), "service")  );
		result.addClassification(blcm.createClassification( XML_LOCAL_NAME, blcm.createInternationalString("service local name"), serviceName)  );
		
		return result;
	}

	
	/** return the ServiceBinding  */
	public ServiceBinding getServiceBinding(String in_accessPoint, String in_wsdlURL, Concept in_portType, Concept in_binding) throws Exception
	{
		ServiceBinding result=null;
		SpecificationLink specLink = null;
		
		result = blcm.createServiceBinding();
		
		// access point
		result.setAccessURI(in_accessPoint);
		
		// portType
		specLink = blcm.createSpecificationLink();
		specLink.setSpecificationObject(in_portType);
		result.addSpecificationLink(specLink);
		
		// binding
		specLink = blcm.createSpecificationLink();
		specLink.setSpecificationObject(in_binding);
		result.addSpecificationLink(specLink);
		
		// wsdl
		specLink = blcm.createSpecificationLink();
		specLink.setSpecificationObject(WSDL_ADDRESS);
		specLink.addExternalLink(blcm.createExternalLink(in_wsdlURL, "WSDL URL"));
		result.addSpecificationLink(specLink);

		return result;
	}
	
	
}
