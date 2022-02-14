CREATE SCHEMA "integ_preco";

CREATE TABLE integ_preco.loja(
	nome VARCHAR(20),
	nome_secao VARCHAR(40) NOT NULL,
	
	CONSTRAINT pk_loja PRIMARY KEY(nome)
);

CREATE SEQUENCE id_jogo_seq START 1;

CREATE TABLE integ_preco.jogo(
	id_jogo INT DEFAULT nextval('id_jogo_seq'),
	titulo VARCHAR(50) NOT NULL,
	desenvolvedora VARCHAR(30),
	data_lancamento VARCHAR,
	url_capa VARCHAR NOT NULL,
	fabricante VARCHAR(30),
	marca VARCHAR(30),
	descricao VARCHAR,
	multijogador CHAR(1),
	genero VARCHAR(30),
	
	CONSTRAINT pk_jogo PRIMARY KEY(id_jogo),
	CONSTRAINT un_jogo UNIQUE(titulo)
);

CREATE TABLE integ_preco.oferta_jogo(
	nome_loja VARCHAR(20),
	id_jogo INT,
	nome_vendedor VARCHAR(50),
	nome_transportadora VARCHAR(50), 
	
	CONSTRAINT pk_oferta_jogo PRIMARY KEY(nome_loja, id_jogo),
	CONSTRAINT fk_oferta_loja FOREIGN KEY(nome_loja)
		REFERENCES integ_preco.loja(nome) ON DELETE CASCADE,
	CONSTRAINT fk_oferta_jogo FOREIGN KEY(id_jogo)
		REFERENCES integ_preco.jogo(id_jogo) ON DELETE CASCADE
);

CREATE TABLE integ_preco.historico_jogo_ofertado(
	num INT,
	nome_loja VARCHAR(20),
	id_jogo INT,
	data_coleta DATE NOT NULL,
	preco MONEY NOT NULL,
	parcelas VARCHAR,
	media_aval NUMERIC(2, 1),
	
	CONSTRAINT pk_hist_jogo_ofer PRIMARY KEY(num, nome_loja, id_jogo),
	CONSTRAINT fk_hist_jogo_ofer FOREIGN KEY(nome_loja, id_jogo)
		REFERENCES integ_preco.oferta_jogo(nome_loja, id_jogo) ON DELETE CASCADE,
	CONSTRAINT ck_oferta_media_aval CHECK (media_aval <= 5.0)
);

CREATE TABLE integ_preco.avaliacao(
	num_aval INT,
	titulo VARCHAR(100),
	id_jogo INT,
	nome_loja VARCHAR(20),
	texto VARCHAR NOT NULL,
	data_realizacao VARCHAR,
	qtd_estrelas INT NOT NULL,
	votos_aval_util INT,
	nome_avaliador VARCHAR(50),
	pais_avaliador VARCHAR(30),
	
	CONSTRAINT pk_avaliacao PRIMARY KEY(num_aval, nome_loja, id_jogo),
	CONSTRAINT fk_avaliacao_oferta FOREIGN KEY(id_jogo, nome_loja)
		REFERENCES integ_preco.oferta_jogo(id_jogo, nome_loja) ON DELETE CASCADE,
	CONSTRAINT ck_avaliacao_estrelas CHECK (qtd_estrelas <= 5)
);

CREATE TABLE integ_preco.pergunta_cliente(
	num_perg INT,
	id_jogo INT,
	nome_loja VARCHAR(20),
	texto_pergunta VARCHAR NOT NULL,
	texto_resposta VARCHAR,
	votos_pergunta_util INT NOT NULL,
	data_pergunta VARCHAR,
	data_resposta VARCHAR,
	
	CONSTRAINT pk_pergunta_cliente PRIMARY KEY(num_perg, id_jogo, nome_loja),
	CONSTRAINT fk_pergunta_oferta FOREIGN KEY(id_jogo, nome_loja) 
		REFERENCES integ_preco.oferta_jogo(id_jogo, nome_loja) ON DELETE CASCADE
);

CREATE SEQUENCE script_crawl_seq START 1;

CREATE TABLE integ_preco.script_crawling(
	num INT DEFAULT nextval('script_crawl_seq'),
	nome_loja VARCHAR(20) NOT NULL,
	funcao_script VARCHAR NOT NULL,
	
	CONSTRAINT pk_script PRIMARY KEY(num),
	CONSTRAINT fk_script FOREIGN KEY(nome_loja)
		REFERENCES integ_preco.loja(nome) ON DELETE CASCADE,
	CONSTRAINT un_script UNIQUE(funcao_script)
);

CREATE TABLE integ_preco.versao_script(
	num_versao INT,
	num_script INT,
	data_utilizacao DATE NOT NULL,
	algoritmo VARCHAR NOT NULL,
	
	CONSTRAINT pk_versao PRIMARY KEY(num_versao, num_script),
	CONSTRAINT fk_versao FOREIGN KEY(num_script) 
		REFERENCES integ_preco.script_crawling(num) ON DELETE CASCADE
);