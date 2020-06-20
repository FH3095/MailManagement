package eu._4fh.mailmanagement.web.models;

import java.io.Serializable;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.cycle.RequestCycle;

import eu._4fh.mailmanagement.data.DataObject;
import eu._4fh.mailmanagement.db.Db;
import eu._4fh.mailmanagement.web.WicketApplication;

public class JpaModel<T extends DataObject> extends LoadableDetachableModel<T> {
	private static final long serialVersionUID = 2183787784674033832L;

	private Class<T> entityClass;
	private Serializable identifier;

	private Db getDb() {
		return RequestCycle.get().getMetaData(WicketApplication.dbKey);
	}

	@SuppressWarnings("unchecked")
	public JpaModel(final T entity) {
		super(entity);
		identifier = getDb().getIdForEntity(entity);
		entityClass = (Class<T>) entity.getClass();
	}

	@Override
	protected T load() {
		if (identifier == null) {
			throw new IllegalStateException("Missing identifier to load");
		}
		return getDb().getEntity(entityClass, identifier);
	}
}
