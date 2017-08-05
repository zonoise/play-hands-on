# --- First database schema

# --- !Ups

ALTER TABLE todo ADD COLUMN created DATE;
ALTER TABLE todo ADD COLUMN duedate DATE;
ALTER TABLE todo ADD COLUMN startdate DATE;


# --- !Downs
ALTER TABLE todo DROP COLUMN created;
ALTER TABLE todo DROP COLUMN duedate;
ALTER TABLE todo DROP COLUMN startdate;
