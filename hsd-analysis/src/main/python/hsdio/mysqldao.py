#!/usr/bin/python
# -*- coding: utf-8 -*-
import MySQLdb
__author__ = 'wangxiaoyi'


def get_original_data_from_db(start=0, end=100 * 10000):

    db = MySQLdb.connect("10.214.208.14", "hsd", "root", "hsd_db")

    cursor = db.cursor()
    sql = "select rid, content from reports order by pub_date desc limit %d, %d " % (start, end)
    cursor.execute(sql)
    results = []
    try:
        results = cursor.fetchall()
        return results
    except IOError:
        print "fetch data error"

    db.close()
    return results

if __name__ == '__main__':

    ress = get_original_data_from_db(1, 200)
    for row in ress:
        print(row[0])
        print(row[1])

