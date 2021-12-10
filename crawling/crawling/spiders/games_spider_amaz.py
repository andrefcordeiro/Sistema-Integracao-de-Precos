import scrapy


# Retorna as avaliacoes da página de um jogo
def parse_avaliacoes(avaliacoes):
    aval = []

    for avaliacao in avaliacoes:
        aval.append({
            'nome_avaliador': avaliacao.xpath('*//span[@class="a-profile-name"]/text()').get(),
            'titulo': avaliacao.xpath('*//a[@data-hook="review-title"]/span/text()').get(),
            'data': avaliacao.xpath('*//span[@data-hook="review-date"]/text()').get().split(" ", 4)[4],
            'texto': avaliacao.xpath('*//div[@data-hook="review-collapsed"]/span').get().replace("\n", "")
                .replace("<span>", "").replace("</span>", "").replace("<br>", "").lstrip().rstrip(),
            'votos_aval_util':
                avaliacao.xpath('*//span[@data-hook="helpful-vote-statement"]/text()').get().split(" ", 1)[0],
            'estrelas': avaliacao.xpath('*//i[@data-hook="review-star-rating"]/span/text()').get()[:1],
        })

    return aval


def parse_perg_resp(response):
    prs = []

    # container da pag com as perguntas
    perguntas = response.xpath(
        '//div[@class="a-section askTeaserQuestions"]/div[@class="a-fixed-left-grid a-spacing-base"]')

    for p in perguntas:
        perg_resp_container = p.xpath('*//div')[1]

        prs.append({
            'pergunta': perg_resp_container.xpath('*//span[@class="a-declarative"]/text()').get().rstrip().lstrip(),
            'nome': '',
            'data': '',
            'resposta': p.xpath(
                '*//div[@class="a-fixed-left-grid-col a-col-right"]/span/text()').get(),
            'data_resposta': p.xpath(
                '*//div[@class="a-fixed-left-grid-col a-col-right"]/div/span/text()').get().rstrip().lstrip()[2:],
            'votos_perg_util': p.xpath('*//ul[@class="vote voteAjax"]/li/span[@class="count"]/text()').get()
        })

    return prs


# Retorna as perguntas da página de um jogo
def parse_perguntas(response):
    # link para acessar a página com as perguntas
    link_pag_perguntas = response.xpath('//div[@class="cdQuestionLazySeeAll"]/a/@href').get()

    url = response.urljoin(link_pag_perguntas)
    yield scrapy.Request(url, callback=parse_perg_resp)


# Pega as informações da página do jogo
def parse_game(response):  # Pega as informações da página do jogo
    desc = response.css('div#productDescription')
    parcelado = response.xpath("//div[@id='installmentCalculator_feature_div']/span/text()").get()
    vend_transp = response.xpath('//div[@class="tabular-buybox-text"]/div/span/text()')
    capa = response.xpath('//div[@id="imgTagWrapperId"]/img/@src').get()
    avaliacoes = response.xpath('//div[@class="a-section review aok-relative"]')

    yield {
        'titulo': response.css('#productTitle::text').extract_first().lstrip(' ').rstrip(' '),
        'preco': response.css('#priceblock_ourprice::text').extract_first().replace("\xa0", " "),
        'descricao': desc.xpath('//p/span/text()').get(),
        'vendedora': vend_transp[1].get(),
        'transportadora': vend_transp[0].get(),
        'parcelas': parcelado,
        'url_capa': capa,
        'avaliacoes': parse_avaliacoes(avaliacoes),
        'perguntas': parse_perguntas(response)
    }


class GamesSpiderAmazon(scrapy.Spider):
    name = 'games_amazon'

    start_urls = ['https://www.amazon.com.br/s?i=videogames&rh=n%3A16253336011%2Cp_72%3A17833786011&pd_rd_r=8f2c9c40'
                  '-f018-4a09-b7b3-45171ac7ae04&pd_rd_w=3yz3s&pd_rd_wg=Z7aDx&pf_rd_p=716b5142-7b40-4cb8-94a9'
                  '-c2d932cb1153&pf_rd_r=PDYSZRVE0SE191N9ZWKF&qid=1638734553&ref=sr_pg_1']

    def parse(self, response):  # Pega todos os links da lista de jogos
        links_games = response.xpath('//a[@class="a-link-normal s-no-outline"]/@href').getall()
        # image_urls = response.xpath('//img[@class="s-image"]/@src').getall()

        for link_game in links_games:
            url = response.urljoin(link_game)
            yield scrapy.Request(url, callback=parse_game)

        # yield {"image_urls": image_urls}

        # Avança para a próxima página
        next_pag = response.xpath('*//li[@class="a-last"]/a/@href').get()
        if next_pag is not None:
            # juntando a url padrão do site com a url para a próxima página
            yield scrapy.Request(response.urljoin(next_pag), callback=self.parse)
