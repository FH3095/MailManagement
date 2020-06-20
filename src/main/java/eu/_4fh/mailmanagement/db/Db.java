package eu._4fh.mailmanagement.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;
import eu._4fh.mailmanagement.MailRoles;
import eu._4fh.mailmanagement.data.Alias;
import eu._4fh.mailmanagement.data.DataObject;
import eu._4fh.mailmanagement.data.Domain;
import eu._4fh.mailmanagement.web.Context;
import eu._4fh.mailmanagement.web.WicketSession;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public class Db implements AutoCloseable {
	private static final String HINT_LOAD_GRAPH = "javax.persistence.loadgraph";

	private final EntityManager em;
	private final PersistenceUnitUtil persistenceUnitUtil;
	private final EntityTransaction transaction;
	private final Roles roles;

	public Db(final EntityManagerFactory entityManagerFactory, final Roles roles) {
		if (!roles.hasAnyRole(new Roles(MailRoles.user))) {
			throw new IllegalStateException("Anonymous DB access denied");
		}
		this.roles = roles;
		this.em = entityManagerFactory.createEntityManager();
		this.persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
		transaction = em.getTransaction();
		transaction.begin();
	}

	public void close() {
		if (transaction.isActive()) {
			transaction.setRollbackOnly();
			transaction.rollback();
		}
		if (em.isOpen()) {
			em.close();
		}
	}

	public Serializable getIdForEntity(final Object entity) {
		return (Serializable) persistenceUnitUtil.getIdentifier(entity);
	}

	private boolean isAdmin() {
		return roles.hasAnyRole(new Roles(MailRoles.admin));
	}

	public <T extends DataObject> T getEntity(final Class<T> entityClass, final Serializable identifier) {
		final T entity = em.find(entityClass, identifier);
		if (entity == null) {
			throw new IllegalArgumentException("Entity with class " + entityClass.getName() + " and identifier "
					+ identifier.toString() + " does not exist");
		}
		if (!isAdmin() && !entity.isAccessAllowed()) {
			throw new IllegalArgumentException(
					"Access denied to " + entityClass.getName() + " with identifier " + identifier.toString());
		}
		return entity;
	}

	private <T extends DataObject> List<T> runQuery(final TypedQuery<T> query) {
		final List<T> queryResult = query.getResultList();
		final List<T> result = new ArrayList<>(queryResult.size());
		if (!isAdmin()) {
			queryResult.stream().filter(elem -> elem.isAccessAllowed()).forEach(result::add);
		} else {
			result.addAll(queryResult);
		}
		return Collections.unmodifiableList(result);
	}

	public @Nonnull Domain getDefaultDomain() {
		final String defaultDomainName = Context.getInstance().getDefaultDomain();
		final TypedQuery<Domain> query = em.createNamedQuery("Domain.findByName", Domain.class);
		query.setParameter(1, defaultDomainName);
		final Optional<Domain> domain = runQuery(query).stream().findFirst();
		if (domain.isEmpty()) {
			throw new IllegalStateException(
					"Default-Domain " + Objects.toString(defaultDomainName) + " does not exist");
		}
		return domain.orElseThrow();
	}

	public List<Domain> getNonLocalDomains() {
		final TypedQuery<Domain> query = em.createNamedQuery("Domain.findNonLocalDomains", Domain.class);
		return runQuery(query);
	}

	public Alias createAlias() {
		final Alias alias = new Alias();
		alias.setAddressActive(true);
		alias.setLocalPart("");
		alias.setDomain(getDefaultDomain());
		alias.setAliasActive(true);
		alias.setTarget(WicketSession.get().getUsername());
		return alias;
	}

	public void saveAlias(final Alias alias) {
		em.persist(alias);
		transaction.commit();
		em.refresh(alias);
	}

	public void deleteAlias(final Alias alias) {
		final Alias toDelete = em.contains(alias) ? alias : em.merge(alias);
		em.remove(toDelete);
		transaction.commit();
	}

	public Alias getAlias(long id) {
		return getEntity(Alias.class, id);
	}

	public List<Alias> getAliases() {
		final TypedQuery<Alias> query = em.createNamedQuery("Alias.findAll", Alias.class);
		query.setHint(HINT_LOAD_GRAPH, em.getEntityGraph("Alias.fetchAll"));
		return runQuery(query);
	}
}
