alter table addresses drop foreign key if exists FKaw8n8scoubio36w1nnru1xcot;
alter table aliases drop foreign key if exists FKlx31gt72dl9jvpu5b6ju4c0pl;
alter table local_users drop foreign key if exists FKs5rw0vt5h4gkrnnksovi1o3in;
alter table virtual_users drop foreign key if exists FK9scr958hwiqyeeqf0vr43e5oj;
drop table if exists addresses;
drop table if exists aliases;
drop table if exists domains;
drop table if exists local_users;
drop table if exists virtual_users;
