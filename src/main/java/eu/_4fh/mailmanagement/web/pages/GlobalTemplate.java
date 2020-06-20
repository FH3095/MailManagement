package eu._4fh.mailmanagement.web.pages;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;

import eu._4fh.mailmanagement.MailRoles;
import eu._4fh.mailmanagement.db.Db;
import eu._4fh.mailmanagement.web.WicketApplication;
import eu._4fh.mailmanagement.web.WicketSession;

public class GlobalTemplate extends WebPage {
	private static final long serialVersionUID = -3646447839019560982L;
	protected static final String contentId = "contentComponent";
	private static final String bodyWrapperHeaderId = "bodyWrapperHeader";
	protected final Component bodyHeader;

	public GlobalTemplate() {
		super();
		add(bodyHeader = new Label(bodyWrapperHeaderId, getClass().getSimpleName()));
	}

	protected Db getDb() {
		return RequestCycle.get().getMetaData(WicketApplication.dbKey);
	}

	protected boolean isAdmin() {
		return WicketSession.exists() && WicketSession.get().getRoles().contains(MailRoles.admin);
	}
}
