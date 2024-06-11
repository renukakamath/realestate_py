from flask import *
from database import *

admin=Blueprint('admin',__name__)

@admin.route('/adminhome')
def adminhome():
    
    return render_template('admin_home.html')


        

@admin.route('/addofficer',methods=['get','post'])
def addofficer():
    if not session.get("log_id") is None:
        data={}
        q="select * from officer"
        res=select(q)
        data['dview']=res
        
        if 'add' in request.form:
            fname=request.form['fname']
            lname=request.form['lname']
            place=request.form['place']
            phone=request.form['phone']
            email=request.form['email']
            uname=request.form['uname']
            passw=request.form['passw']
            q="select * from login where username='%s'"%(uname)
            res=select(q)
            if res:
                flash("Username Already Exists.....")
                return redirect(url_for('admin.addofficer'))
            else:
                q="insert into login values(null,'%s','%s','officer')"%(uname,passw)
                id=insert(q)
                q="insert into officer values(null,'%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email)
                insert(q)
                flash("Registration Successfull")
                return redirect(url_for('admin.addofficer'))
            
        if 'action' in request.args:
            action=request.args['action']
            did=request.args['id']
        
        else:
            action=None

        if action=='update':
            q="select * from officer where officer_id='%s'"%(did)
            data['up']=select(q)
        
        if 'update' in request.form:
            fname=request.form['fname']
            lname=request.form['lname']
            place=request.form['place']
            phone=request.form['phone']
            email=request.form['email']

            q="update officer set fname='%s',lname='%s',place='%s',phone='%s',email='%s' where officer_id='%s'"%(fname,lname,place,phone,email,did)
            update(q)
            flash("Successfully Saved......")
            return redirect(url_for('admin.addofficer'))
        
        if action=="delete":
            q="delete from officer where officer_id='%s'"%(did)
            delete(q)
            flash("Successfully Deleted......")
            return redirect(url_for('admin.addofficer'))


        return render_template('admin_manage_officer.html',data=data)
    else:
        return redirect(url_for('public.login'))


@admin.route('/viewplots')
def viewplots():
    data={}
    q="select * from plot"
    data['plot']=select(q)
    
    return render_template('admin_view_plot.html',data=data)


@admin.route('/viewcomplaints',methods=['get','post'])
def viewcomplaints():
    if not session.get("log_id") is None:
        data={}
        q="SELECT * FROM USER INNER JOIN complaint USING(user_id) "
        data['view']=select(q)

        if 'id' in request.args:
            id=request.args['id']
            data['id']=id
            eq=request.args['eq']
            data['enq']=eq
            
        if 'add' in request.form:
            eqid=request.form['eqid']
            reply=request.form['reply']
            q="update complaint set reply='%s' where complaint_id='%s'"%(reply,eqid)
            update(q)
            flash("replay Updated....")
            return redirect(url_for('admin.viewcomplaints'))
        return render_template('admin_view_complaint.html',data=data)
    else:
        return redirect(url_for('public.login'))