from flask import Flask, request

from DBhelper import DBhelper

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/do_register', methods=['POST'])
def do_register():
    db = DBhelper()
    print(request.form['username'], request.form['password'], request.form['role'])
    res = db.do_register(request.form['username'], request.form['password'], request.form['role'])

    return res.__str__()


@app.route('/do_login', methods=['POST'])
def do_login():
    db = DBhelper()
    res = db.do_login(request.form['username'], request.form['password'])
    return res.__str__()


@app.route('/change_password', methods=['POST'])
def change_password():
    db = DBhelper()
    res = db.change_password(request.form['username'], request.form['password'], request.form['newpassword'])
    return res.__str__()


@app.route('/new_class', methods=['POST'])
def new_class():
    db = DBhelper()
    res = db.new_class(request.form['username'], request.form['classaname'])
    return res.__str__()


@app.route('/start_sign', methods=['POST'])
def start_sign():
    db = DBhelper()
    res = db.start_sign(request.form['classaname'])
    return res.__str__()


@app.route('/end_sign', methods=['POST'])
def end_sign():
    db = DBhelper()
    res = db.end_sign(request.form['classaname'])
    return res.__str__()


@app.route('/do_sign', methods=['POST'])
def do_sign():
    db = DBhelper()
    res = db.do_sign(request.form['classaname'], request.form['username'])
    return res.__str__()


@app.route('/get_state', methods=['POST'])
def get_state():
    db = DBhelper()
    res = db.get_state(request.form['classaname'])
    return res.__str__()


@app.route('/get_class', methods=['POST'])
def get_class():
    db = DBhelper()
    res = db.get_class(request.form['username'])
    return res.__str__()

@app.route('/get_sign', methods=['POST'])
def get_sign():
    db = DBhelper()
    res = db.get_sign_history(request.form['username'])
    return res.__str__()

if __name__ == '__main__':
    app.run(host='0.0.0.0')
