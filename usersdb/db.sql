CREATE TABLE public.users (
    uuid uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    name character varying(255),
    surname character varying(255),
    email character varying(255),
    password character varying(255)
);

ALTER TABLE public.users OWNER TO usersapp;
