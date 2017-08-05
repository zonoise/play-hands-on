# --- First database schema

# --- !Ups

ALTER TABLE todo ADD COLUMN created_at DATE;

# --- !Downs
ALTER TABLE todo DROP COLUMN created_at;