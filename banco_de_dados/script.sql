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

ALTER TABLE integ_preco.jogo ALTER COLUMN titulo SET DATA TYPE VARCHAR

ALTER TABLE integ_preco.jogo ALTER COLUMN desenvolvedora SET DATA TYPE VARCHAR(60);
ALTER TABLE integ_preco.jogo ALTER COLUMN fabricante SET DATA TYPE VARCHAR(60);
ALTER TABLE integ_preco.jogo ALTER COLUMN genero SET DATA TYPE VARCHAR(60);
ALTER TABLE integ_preco.jogo ALTER COLUMN marca SET DATA TYPE VARCHAR(60);


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

ALTER TABLE integ_preco.historico_jogo_ofertado ALTER COLUMN preco SET DATA TYPE Decimal(12,2) 

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

ALTER TABLE integ_preco.avaliacao ALTER COLUMN nome_avaliador SET DATA TYPE VARCHAR(100)

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

--- CONSULTAS UTILIZADAS NO DESENVOLVIMENTO DA INTERFACE PÚBLICA DO SISTEMA

-- GET_JOGOS_POR_TITULO
-- procurando jogos com determinada string
SELECT * FROM integ_preco.jogo WHERE position(UPPER('fifa') IN titulo) != 0

-- GET_OFERTAS_JOGO
-- retornando todas as ofertas de um jogo
SELECT * 
	FROM integ_preco.jogo jogo,
		integ_preco.oferta_jogo oj
	WHERE oj.id_jogo = 14  -- (?)
		AND oj.id_jogo = jogo.id_jogo
		
-- GET_MENOR_PRECO_HISTORICO_JOGO_LOJA
-- encontrar dados do menor preço historico de um jogo em uma loja a partir de seu id e nome da loja
SELECT preco, parcelas, data_coleta
	FROM integ_preco.historico_jogo_ofertado hist_a, 
	(
		SELECT hist.nome_loja, hist.id_jogo, MIN(hist.preco) min_preco
		FROM integ_preco.historico_jogo_ofertado hist
		WHERE hist.nome_loja = 'Amazon' -- (?)
		AND hist.id_jogo = 14  -- (?)
		GROUP BY hist.nome_loja, hist.id_jogo) hist_b
	WHERE hist_a.nome_loja = hist_b.nome_loja
		AND hist_a.preco = hist_b.min_preco
		
-- GET_DADOS_JOGO_LOJA_ULTIMO_HIST		
-- encontrar dados do ultimo historico de preço de um jogo em uma loja
SELECT *
	FROM integ_preco.historico_jogo_ofertado hist,
		integ_preco.jogo jogo, integ_preco.oferta_jogo oferta
	WHERE hist.num = (
		-- historico mais recente de uma oferta daquele jogo naquela loja
		SELECT MAX(num) FROM integ_preco.historico_jogo_ofertado
			WHERE hist.id_jogo = id_jogo
			AND hist.nome_loja = nome_loja
	)
	AND hist.id_jogo = jogo.id_jogo
	AND oferta.id_jogo = hist.id_jogo
	AND oferta.nome_loja = hist.nome_loja
	AND hist.nome_loja = 'Amazon' -- (?)
	AND hist.id_jogo = 14  -- (?)

-- GET_MENOR_PRECO_JOGO
-- encontrar menor preço (dentre o historico mais recente inserido) de um jogo em todas as lojas
SELECT hist_a.nome_loja, hist_a.data_coleta, 
		hist_a.parcelas, hist_a.preco,
		oferta.nome_transportadora, oferta.nome_vendedor
		
	FROM integ_preco.historico_jogo_ofertado hist_a, 
		integ_preco.oferta_jogo oferta
	WHERE (hist_a.id_jogo, hist_a.preco) IN
	(
		-- retorna o menor preço dentre todos os historicos de ofertas àquele jogo
		SELECT hist.id_jogo, MIN(hist.preco) min_preco
		FROM (
			-- retorna o historico de cada oferta de jogo com a data mais recente
			SELECT hist.id_jogo, hist.preco
				FROM integ_preco.historico_jogo_ofertado hist
				WHERE hist.num = (
					-- historico mais recente de uma oferta daquele jogo naquela loja
					SELECT MAX(num) FROM integ_preco.historico_jogo_ofertado
						WHERE hist.id_jogo = id_jogo
						AND hist.nome_loja = nome_loja
				)
 				AND hist.id_jogo = 14  -- (?)	
		) hist
		GROUP BY hist.id_jogo
	)
	AND hist_a.num = (
		-- historico mais recente de uma oferta daquele jogo naquela loja
		SELECT MAX(num) FROM integ_preco.historico_jogo_ofertado
			WHERE hist_a.id_jogo = id_jogo
			AND hist_a.nome_loja = nome_loja
	)
	AND oferta.id_jogo = hist_a.id_jogo
	AND oferta.nome_loja = hist_a.nome_loja
	
	
-- GET_JOGOS_MAIS_BEM_AVALIADOS
-- encontrar 10 produtos mais bem avaliados (média entre as avaliações e cada loja)
SELECT jogo.id_jogo, jogo.titulo, TRUNC(media_total.media, 2)
FROM integ_preco.jogo, (
	SELECT id_jogo, AVG(media_loja) media
		FROM (
			-- média em cada loja 
			SELECT jogo.id_jogo, loja.nome, AVG(aval.qtd_estrelas) media_loja FROM
				integ_preco.jogo jogo,
				integ_preco.loja loja,
				integ_preco.avaliacao aval
				WHERE jogo.id_jogo = aval.id_jogo
					AND aval.nome_loja = loja.nome
			GROUP BY jogo.id_jogo, loja.nome
		) AS media_aval_lojas
	GROUP BY id_Jogo
) media_total
WHERE media_total.id_jogo = jogo.id_jogo
ORDER BY media_total.media DESC
LIMIT 60

-- GET_JOGO_MAIS_BARATO_ATUALMENTE
-- retornar jogo mais barato atualmente
SELECT titulo, preco, data_coleta, url_capa, parcelas, 
		oferta.nome_loja, nome_transportadora, nome_vendedor 
	FROM integ_preco.jogo jogo,
		integ_preco.historico_jogo_ofertado hist,
		integ_preco.oferta_jogo oferta
	WHERE hist.id_jogo = jogo.id_jogo
		AND oferta.nome_loja = hist.nome_loja
 		AND oferta.id_jogo = jogo.id_jogo
		AND hist.preco = (
			SELECT MIN(historicos.preco)
				FROM (
					-- retorna o menor preço dentre todos os historicos de ofertas à um jogo
					SELECT hist.id_jogo, hist.preco
						FROM integ_preco.historico_jogo_ofertado hist
						WHERE hist.num = (
							-- historico mais recente de uma oferta daquele jogo naquela loja
							SELECT MAX(num) FROM integ_preco.historico_jogo_ofertado
								WHERE hist.id_jogo = id_jogo
								AND hist.nome_loja = nome_loja
						)
				) historicos
		)
		

	
-- GET_GENERO_JOGOS_QUERY
-- retorna a quantidade de jogos em cada gênero 
SELECT COUNT(genero) qtd_jogos, genero 
	FROM integ_preco.jogo
	WHERE genero IS NOT NULL
	GROUP BY genero

-- encontrar o historico de preco de um jogo em uma loja
SELECT * 
	FROM integ_preco.historico_jogo_ofertado hist, 
	WHERE hist.nome_loja = 'a'
		AND hist.id_jogo = '14'
		
-- retornar ofertas de jogos que estão na amazon e na submarino		
SELECT * 
	FROM integ_preco.oferta_jogo
	WHERE nome_loja = 'Submarino'
	AND oferta_jogo.id_jogo IN (
		SELECT oferta_jogo.id_jogo
			FROM integ_preco.oferta_jogo
			WHERE nome_loja = 'Amazon'
	)
	

-- 	GET_JOGOS_MAIS_VISITADOS
-- retornar jogos mais visitados (com base na quantidade de perguntas e avaliações)	
SELECT jogo.titulo
FROM integ_preco.jogo, 
(SELECT id_jogo, COUNT(*) c_aval
	FROM integ_preco.avaliacao
	group by id_jogo) aval, 
(SELECT id_jogo, COUNT(*) c_perg
	FROM integ_preco.pergunta_cliente
	group by id_jogo) perg
WHERE jogo.id_jogo = aval.id_jogo
	AND jogo.id_jogo = perg.id_jogo
ORDER BY (aval.c_aval + perg.c_perg) DESC
LIMIT 10
	