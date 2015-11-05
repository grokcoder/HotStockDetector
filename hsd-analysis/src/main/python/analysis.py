#!/usr/bin/python
# -*- coding: utf-8 -*-
from gensim import corpora, models
import logging


logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

if __name__ == "__main__":
    # 1. load dic and corpus
    dictionary = corpora.Dictionary.load('./corpus/300docs.dict')
    corpus = corpora.MmCorpus('./corpus/300docs.mm')

    lda = models.ldamodel.LdaModel(corpus, id2word=dictionary, num_topics=3, passes=10)
    lda.print_topics(3)



