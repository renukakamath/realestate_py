from flask import *
from database import *


public=Blueprint('public',__name__)

@public.route('/')
def home():
    session.clear() 
    return render_template('home.html')

@public.route('/login',methods=['get','post'])
def login():
    session.clear() 
    data={}
    if 'login' in request.form:
        uname=request.form['uname']
        passw=request.form['passw']
        q="select * from login where username='%s' and password='%s'"%(uname,passw)
        print(q)
        res=select(q)
        if res:
            utype=res[0]['usertype']
            session['log_id']=res[0]['login_id']
            if utype=='admin':
                flash("Welcome Admin")
                return redirect(url_for('admin.adminhome'))
            elif utype=='officer':
                q="select * from officer where login_id='%s'"%(session['log_id'])
                res1=select(q)
                flash("Welcome Officer")
                session['offid']=res1[0]['officer_id']
                return redirect(url_for('officer.officerhome'))
            
        else:
            flash("ooops Username OR Pasword Is Invalid")
            return redirect("public.login")
    return render_template('login.html')


@public.route('/labreg',methods=['get','post'])
def labreg():

    data={}
    # q="select * from doctor"
    # res=select(q)
    # data['dview']=res
    
    if 'add' in request.form:
       
        lname=request.form['name']
        place=request.form['place']
        phone=request.form['phone']
        email=request.form['email']
        uname=request.form['uname']
        passw=request.form['passw']
        q="select * from login where username='%s'"%(uname)
        res=select(q)
        if res:
            flash("Username Already Exists.....")
            return redirect(url_for('public.labreg'))
        else:
            q="insert into login values(null,'%s','%s','lab')"%(uname,passw)
            id=insert(q)
            q="insert into lab values(null,'%s','%s','%s','%s','%s')"%(id,lname,place,phone,email)
            insert(q)
            flash("Registration Successfull")
            return redirect(url_for('public.login'))

    return render_template('userreg.html',data=data)

@public.route('/phrreg',methods=['get','post'])
def phrreg():

    data={}
    # q="select * from doctor"
    # res=select(q)
    # data['dview']=res
    
    if 'add' in request.form:
       
        lname=request.form['shop']
        place=request.form['place']
        phone=request.form['phone']
        email=request.form['email']
        lno=request.form['lno']
        uname=request.form['uname']
        passw=request.form['passw']
        q="select * from login where username='%s'"%(uname)
        res=select(q)
        if res:
            flash("Username Already Exists.....")
            return redirect(url_for('public.labreg'))
        else:
            q="insert into login values(null,'%s','%s','phr')"%(uname,passw)
            id=insert(q)
            q="insert into pharrmacy values(null,'%s','%s','%s','%s','%s','%s')"%(id,lname,place,phone,email,lno)
            insert(q)
            flash("Registration Successfull")
            return redirect(url_for('public.login'))

    return render_template('phr_register.html',data=data)