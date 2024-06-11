from flask import *
from database import *
import uuid




# import json
# from web3 import Web3, HTTPProvider

# # truffle development blockchain address
# blockchain_address = ' http://127.0.0.1:9545'
# # Client instance to interact with the blockchain
# web3 = Web3(HTTPProvider(blockchain_address))
# # Set the default account (so we don't need to set the "from" for every transaction call)
# web3.eth.defaultAccount = web3.eth.accounts[0]
# compiled_contract_path = 'C:/Users/renuk/OneDrive/Desktop/RISS/blockchain/real estate/real estate/web/node_modules/.bin/build/contracts/certificate.json'
# # compiled_contract_path = 'F:/NGO/node_modules/.bin/build/contracts/medicines.json'
# # Deployed contract address (see `migrate` command output: `contract address`)
# deployed_contract_address = '0xA7BBe41BCa5606342D92D6Da0E3db2CCb1aE1055'

officer=Blueprint('officer',__name__)

@officer.route('/officerhome')
def officerhome():
    
    return render_template('officer_home.html')




@officer.route('/uploadpost',methods=['get','post'])
def uploadpost():
    data={}

    
    if 'add' in request.form:
        
        place=request.form['place']
        dist=request.form['dist']
        img=request.files['img']
        state=request.form['state']
        path='static/uploads/'+str(uuid.uuid4())+str(img.filename)
        img.save(path)
        
        q="insert into plot values(null,'%s','%s','%s','%s')"%(path,place,dist,state)
        id=insert(q)
       
        flash("post added successfully....")
        return redirect(url_for('officer.uploadpost'))
    
    q="select * from plot "
    res=select(q)    
    data['viewpost']=res
    
    
    if 'action' in request.args:
        action=request.args['action']
        postid=request.args['postid']
        
    else:
        action=None
    
    if action=='delete':
        q="delete from plot where plot_id='%s'"%(postid)
        delete(q)
       
        flash("successfully deleted..")
        return redirect(url_for('officer.uploadpost'))
    
    if action=='update':
        q="select * from plot"
        data['upost']=res
        
    if 'edit' in request.form:
        
        place=request.form['place']
        dist=request.form['dist']
        img=request.files['img']
        state=request.form['state']
        path='static/uploads/'+str(uuid.uuid4())+str(img.filename)
        img.save(path)
        
        if img.filename=="":
            q="update plot set place='%s',district='%s',state='%s' where plot_id='%s'"%(place,dist,state,postid)
            update(q)
            
        else:
            q="update plot set place='%s',district='%s',state='%s',plot='%s' where plot_id='%s'"%(place,dist,state,path,postid)
            update(q)
            
        flash("update successfull...")
        return redirect(url_for('officer.uploadpost'))
    return render_template('officer_manage_plot.html',data=data)


@officer.route('/viewrequests')
def viewrequests():
    data={}
    q="select * from request inner join plot using(plot_id) inner join user using(user_id)"
    data['req']=select(q)
    
    

    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None

    if action=='accept':
        q="update request set status='accept' where request_id='%s'"%(id)
        update(q)
        flash("success")
        return redirect(url_for('officer.viewrequests'))
    if action=='reject':
        q="update request set status='reject' where request_id='%s'"%(id)
        update(q)
        flash("success")
        return redirect(url_for('officer.viewrequests'))
    
    return render_template('officer_view_requests.html',data=data)

@officer.route('/viewregisterrequests')
def viewregisterrequests():
    data={}
    q="select * from plot_register inner join plot using(plot_id) inner join user using(user_id)"
    data['req']=select(q)
    
    

    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None

    if action=='accept':
        q="update plot_register set status='accept' where plot_register_id='%s'"%(id)
        update(q)
        flash("success")
        return redirect(url_for('officer.viewregisterrequests'))
    if action=='reject':
        q="update plot_register set status='reject' where plot_register_id='%s'"%(id)
        update(q)
        flash("success")
        return redirect(url_for('officer.viewregisterrequests'))
    
    return render_template('officer_view_pllot_register_request.html',data=data)


@officer.route('/uploadcertifficate',methods=['get','post'])    #upload certificate this part is block chain 
def uploadcertifficate():
    data={}
    ids=request.args['id']
    if 'add' in request.form:
        img=request.files['img']
        path='static/uploads/'+str(uuid.uuid4())+str(img.filename)
        img.save(path)



        # import datetime
        # d=datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")
        # with open(compiled_contract_path) as file:
        #     contract_json = json.load(file)  # load contract info as JSON
        #     contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
        #     contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
        #     id=web3.eth.get_block_number()
        # message = contract.functions.addcertificate(id,int(ids),path,d).transact()

    
    return render_template('officer_view_pllot_register_request.html',data=data)


@officer.route('/viewpayment')
def viewpayment():
    data={}
    id=request.args['id']
    q="select * from payment inner join  request using (request_id) inner join plot on (request.plot_id=plot.plot_id) inner join user using(user_id) where payment.request_id='%s'"%(id)
    res=select(q)
    if res:
        data['req']=res
    else:
        flash("The Customer Not Yet Paid For The Plot")
        return redirect(url_for('officer.viewrequests'))
    return render_template('officer_viewpayment.html',data=data)