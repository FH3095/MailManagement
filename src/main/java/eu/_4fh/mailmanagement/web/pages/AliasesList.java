package eu._4fh.mailmanagement.web.pages;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;
import eu._4fh.mailmanagement.data.Alias;
import eu._4fh.mailmanagement.web.models.JpaModel;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
@SuppressWarnings("serial")
public class AliasesList extends GlobalTemplate {
	private static final long serialVersionUID = -7943153876576440490L;

	public AliasesList() {
		super();
		setStatelessHint(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final List<Alias> aliases = getDb().getAliases();
		add(new ListView<Alias>("tableRow", aliases) {
			@Override
			protected void populateItem(final ListItem<Alias> item) {
				final Alias alias = item.getModelObject();
				item.setDefaultModel(new CompoundPropertyModel<>(new JpaModel<>(alias)));
				item.add(new Label("id"));
				item.add(new Label("adActive"));
				item.add(new Label("localPart"));
				item.add(new Label("domain.name"));
				item.add(new Label("alActive"));
				item.add(new Label("target"));

				item.add(generateEditLink("alias_edit_link", AliasEdit.class, alias.getId()));
				item.add(generateEditLink("alias_delete_link", AliasDelete.class, alias.getId()));
			}
		});
	}

	private static Link<Void> generateEditLink(final String id, final Class<? extends GlobalTemplate> clazz,
			final long aliasId) {
		final BookmarkablePageLink<Void> result = new BookmarkablePageLink<>(id, clazz,
				new PageParameters().add("id", aliasId));
		return result;
	}
}
