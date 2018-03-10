create table usuario (
	id bigint not null primary key,
	nome varchar not null,
	email varchar not null,
	senha varchar not null
);

create table permissao (
	id bigint not null primary key,
	descricao varchar not null
);

create table usuario_permissao (
	id_usuario bigint not null references usuario,
	id_permissao bigint not null references permissao,
	primary key (id_usuario, id_permissao)
);

insert into usuario (id, nome, email, senha) values (1, 'Administrador', 'admin@curso.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
insert into usuario (id, nome, email, senha) values (2, 'Maria Silva', 'maria@curso.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

insert into permissao (id, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
insert into permissao (id, descricao) values (2, 'ROLE_ALTERAR_CATEGORIA');
insert into permissao (id, descricao) values (3, 'ROLE_REMOVER_CATEGORIA');
insert into permissao (id, descricao) values (4, 'ROLE_PESQUISAR_CATEGORIA');

insert into permissao (id, descricao) values (5, 'ROLE_CADASTRAR_PESSOA');
insert into permissao (id, descricao) values (6, 'ROLE_ALTERAR_PESSOA');
insert into permissao (id, descricao) values (7, 'ROLE_REMOVER_PESSOA');
insert into permissao (id, descricao) values (8, 'ROLE_PESQUISAR_PESSOA');

insert into permissao (id, descricao) values (9, 'ROLE_CADASTRAR_LANCAMENTO');
insert into permissao (id, descricao) values (10, 'ROLE_ALTERAR_LANCAMENTO');
insert into permissao (id, descricao) values (11, 'ROLE_REMOVER_LANCAMENTO');
insert into permissao (id, descricao) values (12, 'ROLE_PESQUISAR_LANCAMENTO');



-- admin
insert into usuario_permissao (id_usuario, id_permissao) values (1, 1);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 2);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 3);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 4);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 5);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 6);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 7);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 8);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 9);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 10);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 11);
insert into usuario_permissao (id_usuario, id_permissao) values (1, 12);

-- maria
insert into usuario_permissao (id_usuario, id_permissao) values (2, 3);
insert into usuario_permissao (id_usuario, id_permissao) values (2, 6);
insert into usuario_permissao (id_usuario, id_permissao) values (2, 9);
