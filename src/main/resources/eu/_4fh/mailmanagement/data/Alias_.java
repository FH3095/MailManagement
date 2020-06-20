package eu._4fh.mailmanagement.data;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Alias.class)
public abstract class Alias_ extends eu._4fh.mailmanagement.data.Address_ {

	public static volatile SingularAttribute<Alias, Boolean> alActive;
	public static volatile SingularAttribute<Alias, String> target;

	public static final String AL_ACTIVE = "alActive";
	public static final String TARGET = "target";

}

