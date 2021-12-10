import scrapy


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


# TODO
# Retorna as perguntas da página de um jogo
"""
def parse_perguntas(perguntas):
    prs = []
    p = perguntas[0]
    for p in perguntas:
        nome_dat_perg = p.xpath('*//p[@class="question__Owner-p6e9ij-3 fDYXRi"]/text()').getall()

        prs.append({
            'pergunta': p.xpath('*//p[@class="highlight-paragraph__Paragraph-sc-11yisd6-0 cUleZO"]/text()').get(),
            'nome': nome_dat_perg[1],
            'data': nome_dat_perg[3],
            'resposta': p.xpath(
                '*//span[@class="src__Text-sc-154pg0p-0 src__TextUI-sc-1ht81y0-0 ealAWO"]/text()').get(),
            'data_resposta': p.xpath('*//span[@class="answer-box__AnswerCreatedAt-y7vmri-2 diwAgC"]/text()').get(),
            'votos_perg_util': ''
        })
    return prs
"""


# Pega as informações da página do jogo
def parse_game(response):
    desc_array = response.xpath('//div[@class="description__HTMLContent-sc-1o6bsvv-1 dGafvC"]/text()').getall()
    preco = response.xpath('//div[@class="src__BestPrice-sc-1jnodg3-5 ykHPU priceSales"]/text()').getall()
    capa = response.xpath('//div[@class="image__WrapperImages-oakrdw-1 dglFwu"]/div/picture/img/@src').get()

    avaliacoes = response.xpath('//div[@class="review__Flex-l45my2-0 review__Wrapper-l45my2-1 kdCqbc"]')
    perguntas = response.xpath('//div[@class="question__Wrapper-p6e9ij-0 ckWhGH"]')

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
        # 'perguntas': parse_perguntas(perguntas)

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
            yield scrapy.Request(url, callback=parse_game)

        # Avança para a próxima página

        """
        next_pag = response.xpath('*//li[@class="a-last"]/a/@href').get()
        if next_pag is not None:
            # juntando a url padrão do site com a url para a próxima página
            yield scrapy.Request(response.urljoin(next_pag), callback=self.parse)
        """
