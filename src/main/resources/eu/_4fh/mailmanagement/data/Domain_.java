package eu._4fh.mailmanagement.data;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Domain.class)
public abstract class Domain_ {

	public static volatile SingularAttribute<Domain, Boolean> localUserDomain;
	public static volatile SingularAttribute<Domain, String> name;
	public static volatile SingularAttribute<Domain, Long> id;

	public static final String LOCAL_USER_DOMAIN = "localUserDomain";
	public static final String NAME = "name";
	public static final String ID = "id";

}

