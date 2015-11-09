#!/usr/bin/python
# -*- coding: utf-8 -*-
import logging
import re

import jieba
from collections import defaultdict
from gensim import corpora

import hsdio.mysqldao as dao
import hsdio.fio as fio
__author__ = 'wangxiaoyi'


logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
if __name__ == "__main__":
    logging.info("starting ETL ... ")

    new_words = [word.decode("utf-8") for word in fio.get_words("../corpus/new_words.txt")]
    for w in new_words:
        jieba.add_word(w)

    path = "../corpus/stop_words_zh.txt"
    # 0. construct stop words list
    logging.info("construct stop word list")
    stop_words = [word.decode("utf-8") for word in fio.get_stop_words(path)]

    useless_words = [word.decode("utf-8") for word in fio.get_stop_words("../corpus/useless_words.txt")]
    stop_words.extend(useless_words)

    # 1.retrieve the original data from database
    logging.info("retrieve original data from database")
    results = dao.get_original_data_from_db(1, 10000)

    documents = []
    # 2.remove stop words, '\n', " "
    logging.info("remove stop words")
    for row in results:
        rid = row[0]
        content = row[1]
        # remove useless url and end content of file
        content = re.sub('“看完这篇还不够？如果你也在创业，并且希望自己的项目被报道，请戳这里\s*<http.+>\s*告诉我们', "", content)
        content = re.sub('<http.+>', '', content)
        seg_words = jieba.cut(content)
        words = [w for w in seg_words if w not in stop_words and len(w) > 0 and w != "\n" and w != " "]
        documents.append(words)

    # 3. remove words that appear only once
    logging.info("remove words that appear only once")
    frequency = defaultdict(int)
    for doc in documents:
        for token in doc:
            frequency[token] += 1

    documents = [[token for token in doc if frequency[token] > 1] for doc in documents]

    # 4. construct token2id (word2id)
    logging.info("construct word2id")
    dictionary = corpora.Dictionary(documents)

    # 5. save the words dict
    logging.info("save the words dict")
    dictionary.save("../corpus/300docs.dict")

    # 6. save the bag of words representation of documents
    logging.info("save bag of words representation of documents")
    corpus = [dictionary.doc2bow(doc) for doc in documents]
    corpora.MmCorpus.serialize('../corpus/300docs.mm', corpus)

    logging.info("ETL is done!")
