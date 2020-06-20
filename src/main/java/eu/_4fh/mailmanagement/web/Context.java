package eu._4fh.mailmanagement.web;

import java.util.Objects;
import java.util.Properties;

import javax.annotation.CheckForNull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public class Context {
	private static final String imapPropertiesName = "java:/comp/env/config/imap-properties";
	private static final String adminUserName = "java:/comp/env/config/adminUser";
	private static final String defaultDomainName = "java:/comp/env/config/defaultDomain";
	private static final Context instance = new Context();

	public static Context getInstance() {
		return instance;
	}

	public Properties getImapProperties() {
		final Object obj = getContextObject(imapPropertiesName);
		if (!(obj instanceof String)) {
			throw new IllegalStateException(imapPropertiesName + " is not a String but " + obj.getClass().getName());
		}
		final String[] values = ((String) obj).split("\\s+");
		final Properties props = new Properties();
		for (final String value : values) {
			final int equalIndex = value.indexOf('=');
			final String namePart = value.substring(0, equalIndex);
			final String valuePart = value.substring(equalIndex + 1);
			props.put(namePart, valuePart);
		}
		return props;
	}

	public String getAdminUsername() {
		final Object adminUser = getContextObject(adminUserName);
		if (!(adminUser instanceof String)) {
			throw new IllegalStateException(adminUserName + " is not a String but " + adminUser.getClass().getName());
		}
		return (String) adminUser;
	}

	public String getDefaultDomain() {
		final Object defaultDomain = getContextObject(defaultDomainName);
		if (!(defaultDomain instanceof String)) {
			throw new IllegalStateException(
					defaultDomainName + " is not a String but " + defaultDomain.getClass().getName());
		}
		return (String) defaultDomain;
	}

	public Object getContextObject(final String name) {
		try {
			javax.naming.Context initContext = new InitialContext();
			final @CheckForNull Object obj = initContext.lookup(name);
			return Objects.requireNonNull(obj, "Context-Object " + name + " not found");
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}
}
