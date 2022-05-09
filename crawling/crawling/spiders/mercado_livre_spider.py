# coding=UTF-8
import scrapy


def collect_estrelas(nota):
    estrelas = len(nota.xpath('*//path[@fill="#3483FA"]'))

    return estrelas


def collect_perguntas(perguntas):
    quotes = []

    for pergunta in perguntas:
        quotes.append({
            'pergunta': pergunta.xpath('*//span[@class="ui-pdp-color--BLACK ui-pdp-size--SMALL ui-pdp-family--REGULAR '
                                       'ui-pdp-qadb__questions-list__question__label"]/text()').get(),
            'nome': '',
            'data_pegunta': '',
            'resposta': pergunta.xpath('*//span[@class="ui-pdp-color--GRAY ui-pdp-size--SMALL ui-pdp-family--REGULAR '
                                       'ui-pdp-qadb__questions-list__answer-item__answer"]/text()').get(),
            'data_resposta': '',
            'votos_perg': ''
        })

    return quotes


def collect_avaliacoes(avaliacoes, notas):
    ava = []
    i = 0

    for avaliacao in avaliacoes:

        nota = notas[i].xpath('*//div[@class="ui-pdp-reviews__comments__review-comment__rating"]')
        i += 1

        ava.append({
             'titulo': avaliacao.xpath('*//p[@class="ui-pdp-reviews__comments__review-comment__title"]/text()').get(),
             'texto': avaliacao.xpath('*//p[@class="ui-pdp-reviews__comments__review-comment__comment"]/text()').get(),
            'estrelas': collect_estrelas(nota)

        })

    return ava


def game_data_collect(response):
    desc = response.xpath('//p[@class="ui-pdp-description__content"]/text()').get()
    capa = response.xpath('//figure[@class="ui-pdp-gallery__figure"]/img/@src').get()
    perguntas = response.xpath('//div[@class="ui-pdp-qadb__questions-list__wraper"]')
    avaliacoes = response.xpath('//div[@id="tab-content-id-todos"]')
    notas = response.xpath('//div[@class="ui-pdp-reviews__comments__review-comment"]')
    preco_reais = response.xpath('//span[@class="price-tag-fraction"]/text()').get()
    preco_cent = response.xpath('//span[@class="price-tag-cents"]/text()').get()
    #parcelar = response.xpath('//*[@id="root-app"]/div[2]/div[3]/div[1]/div[1]/div/div[1]/div[2]/div[2]/div[2]/p/text()[1]').get()
    # parcela_reais = response.xpath('/html/body/main/div[2]/div[3]/div[1]/div[1]/div/div[1]/div[2]/div[3]/div[2]/p/span[2]/span[1]/text()').get()

    yield {
         'titulo': response.xpath('//h1[@class="ui-pdp-title"]/text()').get(),
         'url_capa': capa,
         'preco': preco_reais + ',' + preco_cent,
        # 'parcelas': parcelar + parcela_reais
         'descricao': desc,
         'vendedor': response.xpath('//span[@class="ui-pdp-color--BLUE ui-pdp-family--REGULAR"]/text()').get(),
         'transportadora': 'Mercado Envios',  # O Mercado Livre cuida do envio de produtos por conta própria
         'desenvolvedora': response.xpath('//*[@id="highlighted-specs"]/div[3]/div/div/div/div[1]/div/table/tbody/tr[5]/'
                                           'td/span/text()').get(),
         'editora': response.xpath('//*[@id="highlighted-specs"]/div[3]/div/div/div/div[1]/div/table/tbody/tr[6]/td/'
                                  'span/text()').get(),
         'ano_lancamento': response.xpath('//*[@id="highlighted-specs"]/div[3]/div/div/div/div[1]/div/table/tbody/tr[7]/td/span/text()').get(),
         'genero': response.xpath('//*[@id="highlighted-specs"]/div[3]/div/div/div/div[1]/div/table/tbody/tr[8]/td/span/text()').get(),
         'multijogador': response.xpath('//*[@id="highlighted-specs"]/div[3]/div/div/div/div[2]/div/table/tbody/tr[3]/td/span/text()').get(),
         'perguntas': collect_perguntas(perguntas),
        'avaliacoes': collect_avaliacoes(avaliacoes, notas)
    }


class VideoGamesMercadoLivre(scrapy.Spider):
    name = 'mercado_livre_jogos'



    start_urls = [
        'https://games.mercadolivre.com.br/video-games/ps4/#applied_filter_id%3DVIDEO_GAME_PLATFORM%26applied_filter_name%3DPlataforma%26applied_filter_order%3D4%26applied_value_id%3D126551%26applied_value_name%3DPS4%26applied_value_order%3D24%26applied_value_results%3D35376%26is_custom%3Dfalse']
    count = 0

    def parse(self, response, **kwargs):
        produtos_urls = response.xpath('//div[@class="ui-search-result__image"]/a/@href').getall()

        for produto in produtos_urls:
            url = response.urljoin(produto)
            yield scrapy.Request(url, callback=game_data_collect)

        self.count += 1

        prox_pag = response.xpath('*//li[@class="andes-pagination__button andes-pagination__button--next"]/a/@href').get()
        if prox_pag is not None and self.count < 3:
          yield scrapy.Request(response.urljoin(prox_pag), callback=self.parse)

