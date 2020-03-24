CREATE SCHEMA IF NOT EXISTS public;

DROP TABLE public.ops_api_user;

CREATE TABLE public.ops_api_user (
    uuid uuid NOT NULL,
    created_by uuid,
    creation_date timestamp without time zone,
    email character varying(200) NOT NULL,
    last_update_by uuid,
    last_update_date timestamp without time zone,
    password character varying(200),
    status character varying(10),
    version integer DEFAULT 0 NOT NULL
);
