import json
import scrapy
from urllib.parse import urlencode

API_KEY = ''


def get_scraperapi_url(url):
    payload = {'api_key': API_KEY, 'url': url, 'render': 'true'}
    proxy_url = 'http://api.scraperapi.com/?' + urlencode(payload)
    return proxy_url


# Retorna as avaliacoes da página de um jogo
def parse_avaliacoes(avaliacoes):
    aval = []

    for avaliacao in avaliacoes:
        nome_dat_aval = avaliacao.xpath(
            '*//div[@class="review__Flex-sc-4m3nj8-0 review__User-sc-4m3nj8-5 btVwbB"]/text()').getall()

        aval.append({
            'nome_avaliador': nome_dat_aval[0],
            'titulo': avaliacao.xpath('*//h4[@class="review__Title-sc-4m3nj8-3 edciIP"]/text()').get(),
            'data': nome_dat_aval[2],
            'texto': avaliacao.xpath(
                '*//span[@class="src__Text-sc-154pg0p-0 src__TextUI-sc-1ht81y0-0 ealAWO"]/text()').get(),
            'votos_aval_util':
                avaliacao.xpath(
                    '*//span[@class="review__LabelRecommentation-sc-4m3nj8-10 eklQuG"]/text()').getall()[1],
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
                'votos_resp_util': value['evaluation']['likes']
            })

    prs = []
    indiceR = 0
    for p in perguntas:
        r = respostas[indiceR]
        prs.append({
            'pergunta': p['pergunta'],
            'data_pergunta': p['data_pergunta'],
            'resposta': r['resposta'],
            'data_resposta': r['data_resposta'],
            'votos_resp_util': r['votos_resp_util']
        })
        indiceR += 1

    return prs


# Pega as informações da página do jogo
def parse_game(response):
    desc_array = response.xpath(
        '//div[@class="src__Description-sc-1c7a3hi-2 dChPoq"]/text()').getall()
    preco = response.xpath(
        '//div[@class="src__BestPrice-sc-17hp6jc-5 iyyimP priceSales"]/text()').getall()
    capa = response.xpath(
        '//div[@class="image__WrapperImages-zie4e-1 iBvQLB"]/div/picture/img/@src').get()

    avaliacoes = response.xpath(
        '//div[@class="review__Flex-sc-4m3nj8-0 review__Wrapper-sc-4m3nj8-1 cBgQU"]')
    script = response.xpath('//script[contains(., "window.__APOLLO_STATE__ =")]/text()').extract_first().lstrip()[
        26:]  # script onde serão buscadas as perguntas

    trasportadora = response.xpath(
        '//div[@class="offers-box__Wrapper-o10e3b-0 bRuUTP"]/p/strong/text()').get()
    vendedora = response.xpath(
        '//div[@class="offers-box__Wrapper-o10e3b-0 bRuUTP"]/p/a/text()').get()
    if vendedora is None:
        vendedora = trasportadora

    games = {
        'titulo': response.xpath('//h1[@class="src__Title-sc-79cth1-0 giPKel"]/text()').get().lstrip(' ').rstrip(
            ' '),
        'preco': preco[0] + preco[1],
        'descricao': '\n'.join(desc_array),
        'vendedora': vendedora,
        'transportadora': trasportadora,
        'parcelado': response.xpath('//p[@class="src__Text-ztk7e6-0 hubCwF"]/text()').getall()[0],
        'url_capa': capa,
        'avaliacoes': parse_avaliacoes(avaliacoes),
        'perguntas': parse_perguntas(script)

    }

    yield games


class GamesSpiderShoptime(scrapy.Spider):
    name = 'games_shoptime'

    start_urls = [
        'https://www.shoptime.com.br/categoria/games/playstation-4/jogos-ps4']

    def parse(self, response):  # Pega todos os links da lista de jogos

        grid_games = response.xpath(
            '//div[@class="row product-grid no-gutters main-grid"]')
        links_games = grid_games.xpath(
            '//a[@class="Link-bwhjk3-2 iDkmyz TouchableA-p6nnfn-0 dVqjAx"]/@href').getall()

        for link_game in links_games:
            url = response.urljoin(link_game)
            # yield scrapy.Request(url, callback=parse_game)
            yield scrapy.Request(get_scraperapi_url(url), callback=parse_game)

        # Avança para a próxima página
        links_paginacao = response.xpath(
            '*//ul[@class="pagination-product-grid pagination"]/li')
        next_pag = links_paginacao[len(
            links_paginacao) - 1].xpath('*//@href').get()
        if next_pag is not None:
            # yield response.follow(next_pag, callback=self.parse)  # visita proxima página
            # visita proxima página
            yield response.follow(get_scraperapi_url(next_pag), callback=self.parse)
