#!/usr/bin/python
# -*- coding: utf-8 -*-
import MySQLdb
__author__ = 'wangxiaoyi'


db = MySQLdb.connect("10.214.208.14", "hsd", "root", "hsd_db")
cursor = db.cursor()
cursor.execute("select rid, content from reports order by pub_date desc limit 0, 3;")

try:
    results = cursor.fetchall()
    for row in results:
        rid = row[0]
        content = row[1]
        print "row_id=%s, content=%s" % (rid, content)
except:
    print "fetch data error"

db.close()