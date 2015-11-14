#!/usr/bin/python
# -*- coding: utf-8 -*-
from gensim import corpora, models
import logging


logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

if __name__ == "__main__":
    # 1. load dic and corpus
    dictionary = corpora.Dictionary.load('./corpus/300docs.dict')
    corpus = corpora.MmCorpus('./corpus/300docs.mm')

    lda = models.ldamodel.LdaModel(corpus, id2word=dictionary, num_topics=30, passes=5)
    lda.print_topics(30)
    lda.save("./corpus/lad.model")

    #tfidf = models.TfidfModel(corpus)
    #corpus_tfidf = tfidf[corpus]
    #lsi = models.LsiModel(corpus_tfidf, id2word=dictionary, num_topics=100)
    #lsi.print_topics(100)

   # lda = models.LdaModel.load("./corpus/lad.model")
    #lda.print_topics(100)

