create table addresses (id bigint not null auto_increment, active bit default 1 not null, address varchar(255) not null, domain_id bigint not null, primary key (id)) engine=InnoDB;
create table aliases (active bit default 1 not null, target varchar(255) not null, id bigint not null, primary key (id)) engine=InnoDB;
create table domains (id bigint not null, local_user_domain bit default 0 not null, domain varchar(255) not null, primary key (id)) engine=InnoDB;
create table local_users (active bit default 1 not null, id bigint not null, primary key (id)) engine=InnoDB;
create table virtual_users (alias_prefix varchar(32) default NULL, password varchar(255) not null, quota_bytes bigint default NULL, active bit default 1 not null, id bigint not null, primary key (id)) engine=InnoDB;
create index idx_active on addresses (active);
alter table addresses add constraint idx_domain_address unique (domain_id, address);
create index idx_active on aliases (active);
create index idx_local_user_domain on domains (local_user_domain);
alter table domains add constraint idx_domain unique (domain);
create index idx_active on local_users (active);
create index idx_active on virtual_users (active);
alter table virtual_users add constraint idx_alias_prefix unique (alias_prefix);
alter table addresses add constraint FKaw8n8scoubio36w1nnru1xcot foreign key (domain_id) references domains (id);
alter table aliases add constraint FKlx31gt72dl9jvpu5b6ju4c0pl foreign key (id) references addresses (id);
alter table local_users add constraint FKs5rw0vt5h4gkrnnksovi1o3in foreign key (id) references addresses (id);
alter table virtual_users add constraint FK9scr958hwiqyeeqf0vr43e5oj foreign key (id) references addresses (id);
create table addresses (id bigint not null auto_increment, active bit default 1 not null, address varchar(255) not null, domain_id bigint not null, primary key (id)) engine=InnoDB;
create table aliases (active bit default 1 not null, target varchar(255) not null, id bigint not null, primary key (id)) engine=InnoDB;
create table domains (id bigint not null, local_user_domain bit default 0 not null, domain varchar(255) not null, primary key (id)) engine=InnoDB;
create table local_users (active bit default 1 not null, id bigint not null, primary key (id)) engine=InnoDB;
create table virtual_users (alias_prefix varchar(32) default NULL, password varchar(255) not null, quota_bytes bigint default NULL, active bit default 1 not null, id bigint not null, primary key (id)) engine=InnoDB;
create index idx_active on addresses (active);
alter table addresses add constraint idx_domain_address unique (domain_id, address);
create index idx_active on aliases (active);
create index idx_local_user_domain on domains (local_user_domain);
alter table domains add constraint idx_domain unique (domain);
create index idx_active on local_users (active);
create index idx_active on virtual_users (active);
alter table virtual_users add constraint idx_alias_prefix unique (alias_prefix);
alter table addresses add constraint FKaw8n8scoubio36w1nnru1xcot foreign key (domain_id) references domains (id);
alter table aliases add constraint FKlx31gt72dl9jvpu5b6ju4c0pl foreign key (id) references addresses (id);
alter table local_users add constraint FKs5rw0vt5h4gkrnnksovi1o3in foreign key (id) references addresses (id);
alter table virtual_users add constraint FK9scr958hwiqyeeqf0vr43e5oj foreign key (id) references addresses (id);
