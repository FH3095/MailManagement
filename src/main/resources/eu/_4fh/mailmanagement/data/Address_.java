package eu._4fh.mailmanagement.data;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> localPart;
	public static volatile SingularAttribute<Address, Domain> domain;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, Boolean> adActive;

	public static final String LOCAL_PART = "localPart";
	public static final String DOMAIN = "domain";
	public static final String ID = "id";
	public static final String AD_ACTIVE = "adActive";

}

