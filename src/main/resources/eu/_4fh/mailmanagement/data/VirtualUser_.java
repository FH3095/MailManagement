package eu._4fh.mailmanagement.data;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VirtualUser.class)
public abstract class VirtualUser_ extends eu._4fh.mailmanagement.data.Address_ {

	public static volatile SingularAttribute<VirtualUser, String> password;
	public static volatile SingularAttribute<VirtualUser, Long> quotaBytes;
	public static volatile SingularAttribute<VirtualUser, Character> quotaOverLimit;
	public static volatile SingularAttribute<VirtualUser, Boolean> vuActive;
	public static volatile SingularAttribute<VirtualUser, String> aliasPrefix;

	public static final String PASSWORD = "password";
	public static final String QUOTA_BYTES = "quotaBytes";
	public static final String QUOTA_OVER_LIMIT = "quotaOverLimit";
	public static final String VU_ACTIVE = "vuActive";
	public static final String ALIAS_PREFIX = "aliasPrefix";

}

