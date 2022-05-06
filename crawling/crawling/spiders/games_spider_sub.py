import json
from urllib.parse import urlencode

import scrapy

API_KEY = '9d09f0e972138f5a01e92b085e617e60'


def get_scraperapi_url(url):
    # payload = {'api_key': API_KEY, 'url': url, 'render': 'true'}
    # proxy_url = 'http://api.scraperapi.com/?' + urlencode(payload)
    # return proxy_url
    return url


def formatarData(data):
    data_aux = data.split('T')[0].split('-')
    return data_aux[2] + '/' + data_aux[1] + '/' + data_aux[0]


# Retorna as avaliacoes da página de um jogo
'''
def parse_avaliacoes(avaliacoes):
    aval = []

    for avaliacao in avaliacoes:
        nome_dat_aval = avaliacao.xpath(
            '*//div[@class="review__Flex-sc-l45my2-0 review__User-sc-l45my2-5 ffCSNM"]/text()').getall()

        aval.append({
            'nomeAvaliador': nome_dat_aval[0],
            'titulo': avaliacao.xpath('*//h4[@class="review__Title-sc-l45my2-3 hZdBa"]/text()').get(),
            'data': nome_dat_aval[2],
            'texto': avaliacao.xpath(
                '*//span[@class="src__Text-sc-154pg0p-0 src__TextUI-sc-1ht81y0-0 ealAWO"]/text()').get(),
            'votosAvalUtil':
                avaliacao.xpath('*//span[@class="review__LabelRecommentation-sc-l45my2-10 cttuJr"]/text()').getall()[1],
            'estrelas': len(avaliacao.xpath('*//polygon[@fill="#f2c832"]').getall())
        })

    return aval
'''


def parse_avaliacoes(script_json):
    avaliacoes = []

    # Procurando pelas perguntas e respostas nos atributos do objeto
    for key, value in script_json.items():
        if key.startswith('ReviewsResult'):
            avaliacoes.append({
                'nomeAvaliador': value['user'],
                'titulo': value['title'],
                'data': formatarData(value['date']),
                'texto': value['review'],
                'votosAvalUtil': value['positiveFeedback'],
                'estrelas': value['rating']

            })

    return avaliacoes


# Retorna as perguntas da página de um jogo
def parse_perguntas(script_json):
    perguntas = []
    respostas = []

    # Procurando pelas perguntas e respostas nos atributos do objeto
    for key, value in script_json.items():
        if key.startswith('Question'):
            perguntas.append({
                'pergunta': value['question'],
                'dataPergunta': value['createdAt'],
            })

        if key.startswith('Answer'):
            respostas.append({
                'resposta': value['answer'],
                'dataResposta': value['createdAt'],
                'votosRespUtil': value['evaluation']['likes']
            })

    prs = []
    indiceR = 0
    for p in perguntas:
        r = respostas[indiceR]
        prs.append({
            'pergunta': p['pergunta'],
            'dataPergunta': formatarData(p['dataPergunta']),
            'resposta': r['resposta'],
            'dataResposta': formatarData(r['dataResposta']),
            'votosRespUtil': r['votosRespUtil']
        })
        indiceR += 1

    return prs


# Pega as informações da página do jogo
def parse_jogo(response):
    desc_array = response.xpath('//div[@class="description__HTMLContent-sc-1o6bsvv-1 dGafvC"]/text()').getall()
    preco = response.xpath('//div[@class="src__BestPrice-sc-1jnodg3-5 ykHPU priceSales"]/text()').getall()
    capa = response.xpath('//div[@class="image__WrapperImages-sc-oakrdw-1 kOCIiH"]/div/picture/img/@src').get()

    avaliacoes = response.xpath('//div[@class="review__Flex-sc-l45my2-0 review__Wrapper-sc-l45my2-1 gZIEnN"]')
    script = response.xpath('//script[contains(., "window.__APOLLO_STATE__ =")]/text()').extract_first().lstrip()[
             26:]  # script onde serão buscadas as perguntas
    script_json = json.loads(script)

    trasportadora = response.xpath('//div[@class="offers-box__Wrapper-sc-fiox0-0 cOLhck"]/p/strong/text()').get()
    vendedora = response.xpath('//div[@class="offers-box__Wrapper-sc-fiox0-0 cOLhck"]/p/a/text()').get()
    if vendedora is None:
        vendedora = trasportadora

    games = {
        'titulo': response.xpath('//h1[@class="src__Title-sc-1xq3hsd-0 bHxjvB"]/text()').get().upper().strip(),
        'preco': preco[1].replace(',', '.'),
        'descricao': '\n'.join(desc_array),
        'vendedora': vendedora,
        'transportadora': trasportadora,
        'parcelas': response.xpath('//p[@class="src__Text-sc-162utrw-0 ibyqZE"]/text()').getall()[0].split(' ')[1],
        'urlCapa': capa,
        'avaliacoes': parse_avaliacoes(script_json),
        'perguntas': parse_perguntas(script_json)

    }

    yield games


class GamesSpiderSubmarino(scrapy.Spider):
    name = 'games_submarino'

    start_urls = ['https://www.submarino.com.br/categoria/games/playstation-4/jogos-ps4']

    def parse(self, response):  # Pega todos os links da lista de jogos

        grid_jogos = response.xpath('//div[@class="product-grid-item ProductGrid__GridColumn-sc-49j2r8-0 eZaEaE '
                                    'ColUI-gjy0oc-0 ifczFg ViewUI-sc-1ijittn-6 iXIDWU"]')
        links_jogos = grid_jogos.xpath('//a[@class="Link-bwhjk3-2 iDkmyz TouchableA-p6nnfn-0 joVuoc"]/@href').getall()

        for link_jogo in links_jogos:
            url = response.urljoin(link_jogo)
            yield scrapy.Request(get_scraperapi_url(url), callback=parse_jogo)

        # Avança para a próxima página
        links_paginacao = response.xpath('*//ul[@class="pagination-product-grid pagination"]/li')
        next_pag = links_paginacao[len(links_paginacao) - 1].xpath('*//@href').get()
        if next_pag is not None:
            # visita proxima página
            yield scrapy.Request(get_scraperapi_url(response.urljoin(next_pag)), callback=self.parse)
