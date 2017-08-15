# --- First database schema

# --- !Ups

ALTER TABLE todo ADD COLUMN created DATETIME;
ALTER TABLE todo ADD COLUMN duedate DATETIME;
ALTER TABLE todo ADD COLUMN startdate DATETIME;


# --- !Downs
ALTER TABLE todo DROP COLUMN created;
ALTER TABLE todo DROP COLUMN duedate;
ALTER TABLE todo DROP COLUMN startdate;
