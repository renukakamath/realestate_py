from flask import *
from database import *
from public import public
from admin import admin
from officer import officer



app=Flask(__name__)
app.secret_key="hai"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(officer,url_prefix='/officer')

# app.register_blueprint(api,url_prefix='/api')


# @app.errorhandler(404)
# def not_found(e):
#   return render_template("404.html")
app.run(debug=True,port=5443,host="0.0.0.0")
