#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time : 2018/10/29 3:31 PM
# @Author : maxu
# @Site : 
# @File : test.py
# @Software: PyCharm
import requests

url = 'http://0.0.0.0:5000/get_sign'
data = "username=2016011390"
res = requests.post(url, data)
print(res.text)
