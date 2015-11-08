create table app_user (
  id SERIAL primary key,
  created_at TIMESTAMP,
  username character varying(256),
  profiles text[] not null
) with (oids=false);
