from urllib.parse import urlencode

import scrapy
from scrapy.exceptions import CloseSpider

API_KEY = '9d09f0e972138f5a01e92b085e617e60'


def get_scraperapi_url(url):
    # payload = {'api_key': API_KEY, 'url': url, 'render': 'true'}
    # proxy_url = 'http://api.scraperapi.com/?' + urlencode(payload)
    # return proxy_url
    return url


# Retorna as avaliacoes da página de um jogo
def parse_avaliacoes(avaliacoes):
    aval = []

    for avaliacao in avaliacoes:
        votos_aval_util = avaliacao.xpath('*//span[@data-hook="helpful-vote-statement"]/text()').get()
        if votos_aval_util is None:
            votos_aval_util = 0
        else:
            votos_aval_util = votos_aval_util.split(" ", 1)[0]

        aval.append({
            'nome_avaliador': avaliacao.xpath('*//span[@class="a-profile-name"]/text()').get(),
            'titulo': avaliacao.xpath('*//a[@data-hook="review-title"]/span/text()').get(),
            'data': avaliacao.xpath('*//span[@data-hook="review-date"]/text()').get().split(" ", 4)[4],
            'texto': avaliacao.xpath('*//div[@data-hook="review-collapsed"]/span').get().replace("\n", "").replace(
                "<span>", "").replace("</span>", "").replace("<br>", "").lstrip().rstrip(),
            'votos_aval_util': votos_aval_util,
            'estrelas': avaliacao.xpath('*//i[@data-hook="review-star-rating"]/span/text()').get()[:1],
        })

    return aval


def parse_resposta(p):
    resp_expadinda = p.xpath('*//span[@class="askExpanderContainer noScriptNotDisplayExpander"]')
    if not resp_expadinda:
        resposta = p.xpath(
            '*//div[@class="a-fixed-left-grid-col a-col-right"]/span/text()')
        if not resposta:
            return ""
        else:
            return "".join(resposta.getall()).strip()

    return "".join(p.xpath(
        '*//span[@class="askLongText"]/text()').getall()).strip()


def parse_data_resposta(p):
    data = p.xpath(
        '*//div[@class="a-fixed-left-grid-col a-col-right"]/div/span/text()')

    if not data:
        return ""

    return data.get().strip()[2:]


# Retorna as perguntas de clientes da página de um jogo
def parse_perguntas(response):
    game = response.meta['item']
    prs = []

    # container da pag com as perguntas
    perguntas = response.xpath(
        '//div[@class="a-section askTeaserQuestions"]/div[@class="a-fixed-left-grid a-spacing-base"]')

    for p in perguntas:
        perg_resp_container = p.xpath('*//div')[1]

        prs.append({
            'pergunta': perg_resp_container.xpath('*//span[@class="a-declarative"]/text()').get().strip(),
            'resposta': parse_resposta(p),
            'data_resposta': parse_data_resposta(p),
            'votos_perg_util': p.xpath('*//ul[@class="vote voteAjax"]/li/span[@class="count"]/text()').get()
        })

    game['perguntas'] = prs
    yield game


def parse_vendedora(response):
    # verificando se o texto que indica a vendedora está dentro de um link
    vend = response.xpath('//div[@class="tabular-buybox-text a-spacing-none"]/span/a/text()').get()
    if not vend:
        return response.xpath('//div[@class="tabular-buybox-text a-spacing-none"]/span/text()').getall()[1]
    return vend


# Pega as informações da página do jogo
def parse_game(response):  # Pega as informações da página do jogo
    desc = response.css('div#productDescription')
    parcelado = response.xpath("//div[@id='installmentCalculator_feature_div']/span/text()").get()
    capa = response.xpath('//div[@id="imgTagWrapperId"]/img/@src').get()
    avaliacoes = response.xpath('//div[@class="a-section review aok-relative"]')

    game = {
        'titulo': response.css('#productTitle::text').extract_first().strip(' '),
        'preco': response.xpath('//span[@class="a-price a-text-price a-size-medium"]/span/text()').get(),
        'descricao': desc.xpath('//p/span/text()').get(),
        'vendedora': parse_vendedora(response),
        'transportadora': response.xpath('//div[@class="tabular-buybox-text a-spacing-none"]/span/text()').get(),
        'parcelas': parcelado,
        'url_capa': capa,
        'avaliacoes': parse_avaliacoes(avaliacoes),
        'perguntas': []
    }

    # link para acessar a página com as perguntas
    link_pag_perguntas = response.xpath('//div[@class="cdQuestionLazySeeAll"]/a/@href').get()
    url = response.urljoin(link_pag_perguntas)
    request = scrapy.Request(get_scraperapi_url(url), callback=parse_perguntas)
    request.meta['item'] = game
    yield request


class GamesSpiderAmazon(scrapy.Spider):
    name = 'games_amazon'
    start_urls = [
        'https://www.amazon.com.br/s?i=videogames&rh=n%3A16253336011%2Cp_72%3A17833786011&pd_rd_r=8f2c9c40'
        '-f018-4a09-b7b3-45171ac7ae04&pd_rd_w=3yz3s&pd_rd_wg=Z7aDx&pf_rd_p=716b5142-7b40-4cb8-94a9'
        '-c2d932cb1153&pf_rd_r=PDYSZRVE0SE191N9ZWKF&qid=1638734553&ref=sr_pg_1']
    number_of_pages = 3  # limite de páginas
    count = 0

    def parse(self, response):  # Pega todos os links da lista de jogos

        if self.count >= 3:
            raise CloseSpider(f"Scraped {self.number_of_pages} pages. Eject!")

        links_games = response.xpath('//a[@class="a-link-normal s-no-outline"]/@href').getall()
        for link_game in links_games:
            url = response.urljoin(link_game)
            yield scrapy.Request(get_scraperapi_url(url), callback=parse_game)

        self.count += 1

        # Avança para a próxima página
        next_pag = response.xpath('*//li[@class="a-last"]/a/@href').get()
        if next_pag is not None:
            yield scrapy.Request(get_scraperapi_url(response.urljoin(next_pag)), callback=self.parse)

