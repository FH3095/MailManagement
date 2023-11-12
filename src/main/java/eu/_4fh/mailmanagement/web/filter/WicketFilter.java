package eu._4fh.mailmanagement.web.filter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

@WebFilter(value = "/*", initParams = {
		@WebInitParam(name = "applicationClassName", value = "eu._4fh.mailmanagement.web.WicketApplication"),
		@WebInitParam(name = "filterMappingUrlPattern", value = "/*") })
@SuppressFBWarnings(value = "NM_SAME_SIMPLE_NAME_AS_SUPERCLASS")
public class WicketFilter extends org.apache.wicket.protocol.http.WicketFilter {
}
