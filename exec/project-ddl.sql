create table dependency
(
    dependency_id          bigserial
        primary key,
    basic_dependency       boolean       not null,
    dependency_category    varchar(20)   not null,
    dependency_code        varchar(2000) not null,
    dependency_description varchar(500)  not null,
    dependency_name        varchar(100)  not null
);

alter table dependency
    owner to soup;

create table member
(
    member_id                uuid         not null
        primary key,
    member_created_at        timestamp(6),
    member_modified_at       timestamp(6),
    member_status            boolean,
    member_email             varchar(50)  not null,
    member_nickname          varchar(20),
    member_phone             varchar(20),
    member_profile_image_url varchar(255),
    member_social_id         varchar(255) not null,
    member_social_type       varchar(255) not null
        constraint member_member_social_type_check
            check ((member_social_type)::text = ANY
                   ((ARRAY ['KAKAO'::character varying, 'NAVER'::character varying, 'GOOGLE'::character varying])::text[]))
);

alter table member
    owner to soup;

create table project
(
    project_id          varchar(255) not null
        primary key,
    project_created_at  timestamp(6),
    project_modified_at timestamp(6),
    project_status      boolean,
    project_file_uri    varchar(1000),
    project_img_url     varchar(1000),
    project_name        varchar(255)
);

alter table project
    owner to soup;

create table project_auth
(
    project_auth_id          uuid         not null
        primary key,
    project_auth_created_at  timestamp(6),
    project_auth_modified_at timestamp(6),
    project_auth_status      boolean,
    member_id                uuid
        constraint fk9849cncwr9nsp8pd1jjygjms2
            references member,
    project_id               varchar(255) not null
        constraint fkdiluv14rj4ll2h0njuk7tvqhy
            references project
);

alter table project_auth
    owner to soup;

create table project_auth_role
(
    project_auth_id uuid not null
        constraint fk5dorhyvebonn3jvlwfw6ffnqw
            references project_auth,
    role            varchar(255)
        constraint project_auth_role_role_check
            check ((role)::text = ANY
                   ((ARRAY ['ADMIN'::character varying, 'MAINTAINER'::character varying, 'DEVELOPER'::character varying])::text[]))
);

alter table project_auth_role
    owner to soup;

create table springboot_version
(
    version_id   bigserial
        primary key,
    version_name varchar(255)
);

alter table springboot_version
    owner to soup;

