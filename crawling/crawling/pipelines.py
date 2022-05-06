# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
from scrapy.exporters import JsonItemExporter
from datetime import datetime

'''
from scrapy.pipelines.images import ImagesPipeline


class GamesImagePipeline(ImagesPipeline):
    def file_path(self, request, response=None, info=None, *, item=None):
        return request.url.split('/')[-1]
'''


class GamesPipeline(object):
    file = None

    def __init__(self):
        self.exporter = None

    def open_spider(self, spider):
        now = datetime.now().strftime("%d-%m-%Y-%H.%M.%S")

        if spider.name == 'games_amazon':
            self.file = open('./output/amazon/games_amazon_' + now + '.json', 'wb')
        if spider.name == 'games_submarino':
            self.file = open('./output/submarino/games_submarino_' + now + '.json', 'wb')
        if spider.name == 'mercado_livre_jogos':
            self.file = open('./output/mercado_livre/games_mercado_livre_' + now + '.json', 'wb')
        if spider.name == 'games_shoptime':
            self.file = open('./output/shoptime/games_shoptime_' + now + '.json', 'wb')

        self.exporter = JsonItemExporter(self.file, indent=2, ensure_ascii=False)
        self.exporter.start_exporting()

    def close_spider(self, spider):
        self.exporter.finish_exporting()
        self.file.close()

    def process_item(self, item, spider):
        self.exporter.export_item(item)
        return item
