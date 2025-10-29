-- Create Database: TesBluMW

-- DROP DATABASE IF EXISTS "TesBluMW";

CREATE DATABASE "TesBluMW"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_Indonesia.1252'
    LC_CTYPE = 'English_Indonesia.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE "TesBluMW"
    IS 'Database for Blu middleware test';


