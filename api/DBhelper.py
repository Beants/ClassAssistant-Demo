#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time : 2018/10/26 9:26 PM
# @Author : maxu
# @Site : 
# @File : DBhelper.py
# @Software: PyCharm
import random
import time

import pymongo


# 密码使用base64两次加密储存
class DBhelper:
    def __init__(self):
        self.dbName = 'ClassAssistant'
        self.collName = 'user'
        db = pymongo.MongoClient()
        self.db = db[self.dbName]
        self.usercoll = self.db[self.collName]
        self.classcoll = self.db['class']
        self.signcoll = self.db['sign']

    # 1 成功
    # 0 失败
    def do_register(self, username, password, role):
        data = {
            'username': username,
            'password': password,
            'role': role
        }
        print(data)
        if self.usercoll.find_one({'username': username}) is None:
            self.usercoll.insert(data)
            return 1
        else:
            return 0

    # 1 成功
    # 0 失败
    def do_login(self, username, password):
        real_username = self.usercoll.find_one({'username': username})
        if real_username is None:
            return 0
        elif password != real_username['password']:
            return 0
        else:
            return real_username['role']

    # 1 成功
    # 0 失败
    def change_password(self, username, oldpw, newpw):
        temp = self.usercoll.find_one({'username': username})
        if temp is None:
            return 0
        elif temp['password'] == oldpw:
            self.usercoll.find_one_and_update({'username': username}, {'$set': {'password': newpw}})
            return 1
        else:
            return 0

    def new_class(self, username_t, class_name, ):
        data = {
            'teacher': username_t,
            'classname': class_name,
            'students': [],
        }
        if self.classcoll.find_one({'classname': class_name}) is None:
            self.classcoll.insert(data)
            return 1
        else:
            return 0

    def join_class(self, classname, username_student):
        if self.classcoll.find_one({'classname': classname}) is None:
            return 0
        else:
            stus = self.classcoll.find_one({'classname': classname})['students']
            if username_student not in stus:
                stus.append(username_student)
                self.classcoll.find_one_and_update({'classname': classname}, {'$set': {'students': stus}})
            return 1

    def start_sign(self, classname):
        if self.classcoll.find_one({'classname': classname}) is not None:
            data = {
                'time_start': time.time(),
                'state': '1',
                'sign_id': random.randint(1000, 9999).__str__(),
                'classname': classname,
                'stu': {
                }
            }
            for stu in self.classcoll.find_one({'classname': classname})['students']:
                data['stu'][stu] = '0'
            self.signcoll.insert(data)
            return data['sign_id']
        else:
            return 0
        pass

    def end_sign(self, classname):
        if self.signcoll.find_one({'classname': classname, 'state': '1'}) is not None:
            self.signcoll.find_one_and_update({'classname': classname, 'state': '1'}, {'$set': {'state': '0'}})
            return 1
        else:
            return 0

    def do_sign(self, classname, username_stu):
        if self.signcoll.find_one({'classname': classname}) is not None:
            stu = self.signcoll.find_one({'classname': classname})['stu']
            stu[username_stu] = '1'
            self.signcoll.find_one_and_update({'classname': classname}, {'$set': {'stu': stu}})
            return 1
        else:
            return

    def get_state(self, classname):
        res = self.signcoll.find_one({'classname': classname})
        return res['stu']

    def get_class(self, username):
        res = self.classcoll.find({'teacher': username})
        RES = []
        for i in res:
            RES.append(i["classname"])
        return " ".join(RES)

    def get_sign_history(self, username):
        res_class = self.classcoll.find({'teacher': username})
        REss = []
        for i in res_class:

            res_sign = self.signcoll.find({'classname': i['classname']})
            for j in res_sign:
                length = 0
                for ii in j['stu'].values():
                    if ii == '1':
                        length += 1

                length = str(length) + '/' + str(j['stu'].keys().__len__())
                res = j['classname'] + '|' + j['state'] + '|' + j['sign_id'] + '|' + length
                REss.append(res)
        return '&'.join(REss)


if __name__ == '__main__':
    db = DBhelper()
    # password_ = base64.encodebytes('2017011351'.encode())
    # print(password_.decode())
    # print('注册：')
    # print(db.do_register('2017011351', password_.decode().replace('\n', ''), 'student'))
    # print('登陆：')
    # res = db.do_login('2016011390', 'MjAxNjAxMTM5MA==')
    # print(res)
    # print('更改密码：')
    # print(db.change_password('2016011390', 'MjAxNjAxMTM5MA==', 'MjAxNjAxMTM5MA=='))
    # print(db.new_class('2016011390', '测试班级1'))
    # print(db.join_class('测试班级', '2017011351'))
    # print(db.start_sign('测试班级1'))
    # print(db.end_sign('测试班级1'))
    # print(db.do_sign('测试班级', '2017011350'))

    print(db.get_sign_history("2016011390"))
