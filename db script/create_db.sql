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


-- Create Table: user


CREATE TABLE IF NOT EXISTS public."user"
(
    id uuid NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."user"
    OWNER to postgres;