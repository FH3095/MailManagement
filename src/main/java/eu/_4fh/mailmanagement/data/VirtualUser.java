package eu._4fh.mailmanagement.data;

import javax.annotation.CheckForNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "virtual_users", uniqueConstraints = {
		@UniqueConstraint(name = "unique_alias_prefix", columnNames = { "alias_prefix" }) }, indexes = {
				@Index(name = "idx_active", columnList = "active", unique = false),
				@Index(name = "idx_alias_prefix", columnList = "alias_prefix", unique = true) })
public class VirtualUser extends Address {
	private static final long serialVersionUID = -2485362537825218594L;

	@Column(name = "active", nullable = false)
	@ColumnDefault(value = "1")
	private boolean vuActive;

	@Column(name = "alias_prefix", nullable = true, length = 32)
	@ColumnDefault(value = "NULL")
	private @CheckForNull String aliasPrefix;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "quota_bytes", nullable = true)
	@ColumnDefault(value = "NULL")
	private @CheckForNull Long quotaBytes;

	public VirtualUser() {
	}

	public boolean isVirtualUserActive() {
		return vuActive;
	}

	public void setVirtualUserActive(boolean active) {
		this.vuActive = active;
	}

	@Override
	public boolean isAccessAllowed() {
		return false;
	}
}
