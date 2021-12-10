# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
from scrapy.exporters import JsonItemExporter
from scrapy.pipelines.images import ImagesPipeline


class GamesImagePipeline(ImagesPipeline):
    def file_path(self, request, response=None, info=None, *, item=None):
        return request.url.split('/')[-1]


class GamesPipeline(object):
    file = None

    def open_spider(self, spider):
        if spider.name == 'games_amazon':
            self.file = open('./output/amazon/games.json', 'wb')
        if spider.name == 'games_submarino':
            self.file = open('./output/submarino/games.json', 'wb')
        if spider.name == 'perg_submarino':
            self.file = open('./output/submarino/perg_games.json', 'wb')

        self.exporter = JsonItemExporter(self.file, indent=2, ensure_ascii=False)
        self.exporter.start_exporting()

    def close_spider(self, spider):
        self.exporter.finish_exporting()
        self.file.close()

    def process_item(self, item, spider):
        self.exporter.export_item(item)
        return item
