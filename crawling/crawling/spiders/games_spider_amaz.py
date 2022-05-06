from urllib.parse import urlencode

import scrapy

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

        texto = avaliacao.xpath('*//div[@data-hook="review-collapsed"]/span').get()
        if texto is None:
            texto = ''
        else:
            texto = texto.replace("\n", "").replace(
                "<span>", "").replace("</span>", "").replace("<br>", "").lstrip().rstrip()

        aval.append({
            'nomeAvaliador': avaliacao.xpath('*//span[@class="a-profile-name"]/text()').get(),
            'titulo': avaliacao.xpath('*//a[@data-hook="review-title"]/span/text()').get(),
            'data': avaliacao.xpath('*//span[@data-hook="review-date"]/text()').get().split(" ", 4)[4],
            'texto': texto,
            'votosAvalUtil': votos_aval_util,
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
            'dataResposta': parse_data_resposta(p),
            'votosPergUtil': p.xpath('*//ul[@class="vote voteAjax"]/li/span[@class="count"]/text()').get()
        })

    game['perguntas'] = prs
    yield game


def parse_vendedora(response):
    # verificando se o texto que indica a vendedora está dentro de um link
    vend = response.xpath('//div[@class="tabular-buybox-text a-spacing-none"]/span/a/text()').get()
    if not vend:
        return response.xpath('//div[@class="tabular-buybox-text a-spacing-none"]/span/text()').getall()[1]
    return vend


def parse_data_lancamento(detalhes):
    items_detalhes = detalhes.xpath('*//span[@class="a-list-item"]')

    for it in items_detalhes:
        texto = it.xpath('*//text()')[0].get()
        if texto is None:
            break
        texto_format = texto.replace("\n", "").replace(" ", "").replace("\u200f", "").replace("\u200e", "").strip()
        if texto_format == "Datadelançamento:":
            return it.xpath('*//text()')[1].get()


def get_asin_produto(detalhes):
    items_detalhes = detalhes.xpath('*//span[@class="a-list-item"]')

    for it in items_detalhes:
        texto = it.xpath('*//text()')[0].get()
        if texto is None:
            break
        texto_format = texto.replace("\n", "").replace(" ", "").replace("\u200f", "").replace("\u200e", "").strip()
        if texto_format == "ASIN:":
            return it.xpath('*//text()')[1].get()


# Pega as informações da página do jogo
def parse_jogo(response):  # Pega as informações da página do jogo
    desc = response.css('div#productDescription')
    div_parcelas = response.xpath('//div[@id="promotionMessageInsideBuyBox_feature_div"]/div')

    # se existem parcelas naquele produto
    if div_parcelas:
        parcelas = div_parcelas[1].xpath('*//text()').get().strip()
    else:
        parcelas = ''

    capa = response.xpath('//div[@id="imgTagWrapperId"]/img/@src').get()
    avaliacoes = response.xpath('//div[@class="a-section review aok-relative"]')
    detalhes = response.xpath('//ul[@class="a-unordered-list a-nostyle a-vertical a-spacing-none detail-bullet-list"]')[
        0]

    game = {
        'titulo': response.css('#productTitle::text').extract_first().upper().strip(' '),
        'preco': response.xpath('//span[@class="a-price aok-align-center"]/span/text()').get().split('$')[1].replace(
            ',', '.'),
        'descricao': desc.xpath('//p/span/text()').get(),
        'vendedora': parse_vendedora(response),
        'transportadora': response.xpath('//div[@class="tabular-buybox-text a-spacing-none"]/span/text()').get(),
        'parcelas': parcelas,
        'urlCapa': capa,
        'dataLancamento': parse_data_lancamento(detalhes),
        'avaliacoes': parse_avaliacoes(avaliacoes),
        'perguntas': []
    }

    # link para acessar a página com as perguntas
    asin_produto = get_asin_produto(detalhes)  # recebe o "asin" do produto para acessar a página de perguntas
    link_pag_perguntas = "https://www.amazon.com.br/ask/questions/asin/" + asin_produto + "/1/ref=ask_ql_psf_ql_hza"
    request = scrapy.Request(get_scraperapi_url(link_pag_perguntas), callback=parse_perguntas)
    request.meta['item'] = game
    yield request


class GamesSpiderAmazon(scrapy.Spider):
    name = 'games_amazon'
    start_urls = [
        'https://www.amazon.com.br/s?i=videogames&rh=n%3A16253336011%2Cp_72%3A17833786011&pd_rd_r=8f2c9c40'
        '-f018-4a09-b7b3-45171ac7ae04&pd_rd_w=3yz3s&pd_rd_wg=Z7aDx&pf_rd_p=716b5142-7b40-4cb8-94a9'
        '-c2d932cb1153&pf_rd_r=PDYSZRVE0SE191N9ZWKF&qid=1638734553&ref=sr_pg_1']

    def parse(self, response):  # Pega todos os links da lista de jogos

        div_jogos = response.xpath(
            '//div[@class="s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 sg-col s-widget-spacing-small '
            'sg-col-12-of-16"]')

        link_jogos = response.xpath('//a[@class="a-link-normal s-no-outline"]/@href').getall()

        cont_jogo = 0
        for link_jogo in link_jogos:
            div_jogo = div_jogos[cont_jogo]
            span_preco = div_jogo.xpath('*//span[@class="a-price-whole"]')

            cont_jogo += 1

            # verificando se há um preço sendo apresentado para o produto
            if span_preco:
                url = response.urljoin(link_jogo)
                yield scrapy.Request(get_scraperapi_url(url), callback=parse_jogo)

        # Avança para a próxima página
        next_pag = response.xpath(
            '*//a[@class="s-pagination-item s-pagination-next s-pagination-button s-pagination-separator"]/@href').get()
        if next_pag is not None:
            yield scrapy.Request(get_scraperapi_url(response.urljoin(next_pag)), callback=self.parse)
