
INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:6e10b91b-babc-3442-b8fc-5a3c8fde0794','Administrator','admin','UDDI.org','uddi-org:protocol:http','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#http',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:6e10b91b-babc-3442-b8fc-5a3c8fde0794',0,'en','A tModel that represents the HTTP protocol.');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:6e10b91b-babc-3442-b8fc-5a3c8fde0794',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','protocol');

INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:082b0851-25d8-303c-b332-f24a6d53e38e','Administrator','admin','UDDI.org','uddi-org:wsdl:portTypeReference','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#portTypeReference',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:082b0851-25d8-303c-b332-f24a6d53e38e',0,'en','A category system used to reference a wsdl:portType tModel.');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:082b0851-25d8-303c-b332-f24a6d53e38e',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:082b0851-25d8-303c-b332-f24a6d53e38e',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked');

INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:4dc74177-7806-34d9-aecd-33c57dc3a865','Administrator','admin','UDDI.org','uddi-org:wsdl:categorization:protocol','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#protocol',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:4dc74177-7806-34d9-aecd-33c57dc3a865',0,'en','Category system used to describe the protocol supported by a wsdl:binding.');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:4dc74177-7806-34d9-aecd-33c57dc3a865',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:4dc74177-7806-34d9-aecd-33c57dc3a865',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked');

INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:aa254698-93de-3870-8df3-a5c075d64a0e','Administrator','admin','UDDI.org','uddi-org:protocol:soap','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#soap',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:aa254698-93de-3870-8df3-a5c075d64a0e',0,'en','A tModel that represents the SOAP 1.1 protocol.');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:aa254698-93de-3870-8df3-a5c075d64a0e',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','protocol');

INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:e5c43936-86e4-37bf-8196-1d04b35c0099','Administrator','admin','UDDI.org','uddi-org:wsdl:categorization:transport','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#transport',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:e5c43936-86e4-37bf-8196-1d04b35c0099',0,'en','Category system used to describe the transport supported by a wsdl:binding.');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:e5c43936-86e4-37bf-8196-1d04b35c0099',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:e5c43936-86e4-37bf-8196-1d04b35c0099',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked');

INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:ad61de98-4db8-31b2-a299-a2373dc97212','Administrator','admin','UDDI.org','uddi-org:wsdl:address','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#Address',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:ad61de98-4db8-31b2-a299-a2373dc97212',0,'en','A tModel used to indicate the WSDL address option.');

INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:6e090afa-33e5-36eb-81b7-1ca18373f457','Administrator','admin','UDDI.org','uddi-org:wsdl:types','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#wsdlTypes',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:6e090afa-33e5-36eb-81b7-1ca18373f457',0,'en','WSDL Type Category System.');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:6e090afa-33e5-36eb-81b7-1ca18373f457',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:6e090afa-33e5-36eb-81b7-1ca18373f457',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','unchecked');

INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:2ec65201-9109-3919-9bec-c9dbefcaccf6','Administrator','admin','UDDI.org','uddi-org:xml:localName','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#xmlLocalName',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:2ec65201-9109-3919-9bec-c9dbefcaccf6',0,'en','A category system used to indicate XML local names.');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:2ec65201-9109-3919-9bec-c9dbefcaccf6',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:2ec65201-9109-3919-9bec-c9dbefcaccf6',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','unchecked');

INSERT INTO TMODEL (TMODEL_KEY,AUTHORIZED_NAME,PUBLISHER_ID,OPERATOR,NAME,OVERVIEW_URL,LAST_UPDATE) 
	VALUES ('uuid:d01987d1-ab2e-3013-9be2-2a66eb99d824','Administrator','admin','UDDI.org','uddi-org:xml:namespace','http://www.oasis-open.org/committees/uddi-spec/doc/tn/uddi-spec-tc-tn-wsdl-v2.htm#xmlNamespace',CURDATE());
INSERT INTO TMODEL_DESCR (TMODEL_KEY,TMODEL_DESCR_ID,LANG_CODE,DESCR) 
	VALUES ('uuid:d01987d1-ab2e-3013-9be2-2a66eb99d824',0,'en','A category system used to indicate namespaces.');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:d01987d1-ab2e-3013-9be2-2a66eb99d824',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization');
INSERT INTO TMODEL_CATEGORY (TMODEL_KEY,CATEGORY_ID,TMODEL_KEY_REF,KEY_NAME,KEY_VALUE) 
	VALUES ('uuid:d01987d1-ab2e-3013-9be2-2a66eb99d824',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','unchecked');
	