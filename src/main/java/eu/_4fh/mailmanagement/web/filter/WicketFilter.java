package eu._4fh.mailmanagement.web.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@WebFilter(value = "/*", initParams = {
		@WebInitParam(name = "applicationClassName", value = "eu._4fh.mailmanagement.web.WicketApplication"),
		@WebInitParam(name = "filterMappingUrlPattern", value = "/*") })
@SuppressFBWarnings(value = "NM_SAME_SIMPLE_NAME_AS_SUPERCLASS")
public class WicketFilter extends org.apache.wicket.protocol.http.WicketFilter {
}
