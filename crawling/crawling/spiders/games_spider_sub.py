import json
from urllib.parse import urlencode

import scrapy

API_KEY = '9d09f0e972138f5a01e92b085e617e60'


def get_scraperapi_url(url):
    payload = {'api_key': API_KEY, 'url': url, 'render': 'true'}
    proxy_url = 'http://api.scraperapi.com/?' + urlencode(payload)
    return proxy_url


# Retorna as avaliacoes da página de um jogo
def parse_avaliacoes(avaliacoes):
    aval = []

    for avaliacao in avaliacoes:
        nome_dat_aval = avaliacao.xpath(
            '*//div[@class="review__Flex-l45my2-0 review__User-l45my2-5 iZmFFt"]/text()').getall()

        aval.append({
            'nome_avaliador': nome_dat_aval[0],
            'titulo': avaliacao.xpath('*//h4[@class="review__Title-l45my2-3 cQPVrn"]/text()').get(),
            'data': nome_dat_aval[2],
            'texto': avaliacao.xpath(
                '*//span[@class="src__Text-sc-154pg0p-0 src__TextUI-sc-1ht81y0-0 ealAWO"]/text()').get(),
            'votos_aval_util':
                avaliacao.xpath('*//span[@class="review__LabelRecommentation-sc-4m3nj8-10 eklQuG"]/text()').getall()[1],
            'estrelas': len(avaliacao.xpath('*//polygon[@fill="#f2c832"]').getall())
        })

    return aval


# Retorna as perguntas da página de um jogo
def parse_perguntas(script):
    obj = json.loads(script)

    perguntas = []
    respostas = []

    # Procurando pelas perguntas e respostas nos atributos do objeto
    for key, value in obj.items():
        if key.startswith('Question'):
            perguntas.append({
                'pergunta': value['question'],
                'data_pergunta': value['createdAt'],
            })

        if key.startswith('Answer'):
            respostas.append({
                'resposta': value['answer'],
                'data_resposta': value['createdAt'],
            })

    prs = []
    indiceR = 0
    for p in perguntas:
        r = respostas[indiceR]
        prs.append({
            'pergunta': p['pergunta'],
            'data_pergunta': p['data_pergunta'],
            'resposta': r['resposta'],
            'data_resposta': r['data_resposta']
        })
        indiceR += 1

    return prs


# Pega as informações da página do jogo
def parse_game(response):
    desc_array = response.xpath('//div[@class="description__HTMLContent-sc-1o6bsvv-1 dGafvC"]/text()').getall()
    preco = response.xpath('//div[@class="src__BestPrice-sc-1jnodg3-5 ykHPU priceSales"]/text()').getall()
    capa = response.xpath('//div[@class="image__WrapperImages-oakrdw-1 dglFwu"]/div/picture/img/@src').get()

    avaliacoes = response.xpath('//div[@class="review__Flex-l45my2-0 review__Wrapper-l45my2-1 kdCqbc"]')
    script = response.xpath('//script[contains(., "window.__APOLLO_STATE__ =")]/text()').extract_first().lstrip()[
             26:]  # script onde serão buscadas as perguntas

    games = {
        'titulo': response.xpath('//h1[@class="src__Title-sc-1xq3hsd-0 bHxjvB"]/text()').get().lstrip(' ').rstrip(
            ' '),
        'preco': preco[0] + preco[1],
        'descricao': '\n'.join(desc_array),
        'vendedora': response.xpath('//div[@class="offers-box__Wrapper-fiox0-0 ea-dBtZ"]/p/a/text()').get(),
        'transportadora': response.xpath(
            '//div[@class="offers-box__Wrapper-fiox0-0 ea-dBtZ"]/p/strong/text()').get(),
        'parcelado': response.xpath('//p[@class="src__Text-sc-162utrw-0 ibyqZE"]/text()').getall()[0].split(' ')[1],
        'url_capa': capa,
        'avaliacoes': parse_avaliacoes(avaliacoes),
        'perguntas': parse_perguntas(script)

    }

    yield games


class GamesSpiderSubmarino(scrapy.Spider):
    name = 'games_submarino'

    start_urls = ['https://www.submarino.com.br/categoria/games/playstation-4/m/playstation%204/g/tipo-de-produto'
                  '-Jogo?ordenacao=relevance&origem=blanca']

    def parse(self, response):  # Pega todos os links da lista de jogos

        grid_games = response.xpath('//div[@class="product-grid-item ProductGrid__GridColumn-sc-49j2r8-0 eZaEaE '
                                    'ColUI-gjy0oc-0 ifczFg ViewUI-sc-1ijittn-6 iXIDWU"]')
        links_games = grid_games.xpath('//a[@class="Link-bwhjk3-2 iDkmyz TouchableA-p6nnfn-0 joVuoc"]/@href').getall()

        for link_game in links_games:
            url = response.urljoin(link_game)
            yield scrapy.Request(get_scraperapi_url(url), callback=parse_game)

        # Avança para a próxima página
        next_pag = response.xpath('*//li[@class="a-last"]/a/@href').get()
        if next_pag is not None:
            # juntando a url padrão do site com a url para a próxima página
            yield scrapy.Request(get_scraperapi_url(response.urljoin(next_pag)), callback=self.parse)
