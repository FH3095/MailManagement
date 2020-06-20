package eu._4fh.mailmanagement.data;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;

import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;
import eu._4fh.mailmanagement.MailRoles;
import eu._4fh.mailmanagement.web.WicketSession;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "addresses", uniqueConstraints = {
		@UniqueConstraint(name = "unique_domain_address", columnNames = { "domain_id", "address" }) }, indexes = {
				@Index(name = "idx_active", columnList = "active", unique = false),
				@Index(name = "idx_domain_address", columnList = "domain_id,address", unique = true) })
public class Address implements DataObject {
	private static final long serialVersionUID = 957328232152785407L;

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "active", nullable = false)
	@ColumnDefault(value = "1")
	private boolean adActive;

	@Column(name = "address", length = 255, nullable = false)
	private String localPart;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "domain_id", nullable = false)
	private Domain domain;

	public Address() {
	}

	public long getId() {
		return id;
	}

	public boolean isAddressActive() {
		return adActive;
	}

	public void setAddressActive(boolean active) {
		this.adActive = active;
	}

	public String getLocalPart() {
		return localPart;
	}

	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@Override
	public boolean isAccessAllowed() {
		return WicketSession.exists() && WicketSession.get().getRoles().contains(MailRoles.admin);
	}

	public String getFullAddress() {
		return localPart + "@" + domain.getName();
	}

	@Override
	public String toString() {
		return "Address " + getFullAddress();
	}
}
