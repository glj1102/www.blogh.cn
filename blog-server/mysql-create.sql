create table blog (
  id                        bigint auto_increment not null,
  user                      varchar(255),
  email                     varchar(255),
  blog_title                varchar(255),
  blog_content              varchar(255),
  category_1                bigint,
  category_1_name           varchar(255),
  category_2                bigint,
  category_2_name           varchar(255),
  create_time               datetime,
  status                    varchar(255),
  view_count                integer,
  constraint pk_blog primary key (id))
;

create table categoty (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  parent_id                 bigint,
  constraint pk_categoty primary key (id))
;

create table comment (
  id                        bigint auto_increment not null,
  blog_id                   bigint,
  user                      varchar(255),
  content                   varchar(255),
  create_time               datetime,
  status                    varchar(255),
  constraint pk_comment primary key (id))
;

create table user_mail (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  status                    varchar(255),
  create_time               datetime,
  constraint pk_user_mail primary key (id))
;



