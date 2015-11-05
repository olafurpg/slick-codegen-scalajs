create table app_user (
  id SERIAL primary key,
  username character varying(256),
  profiles text[] not null
) with (oids=false);
