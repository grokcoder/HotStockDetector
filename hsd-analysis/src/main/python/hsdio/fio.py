#!/usr/bin/python
# -*- coding: utf-8 -*-


def get_stop_words(addr):
    stop_words = [line.replace("\n", "") for line in open(addr) if len(line) > 1]
    return stop_words

if __name__ == '__main__':
    stop_words = get_stop_words("/Users/wangxiaoyi/codes/HotStockDetector/"
                                "hsd-analysis/src/main/python/corpus/stop_words_zh.txt")
    for w in stop_words:
        if w == "按" :
            print(w)
