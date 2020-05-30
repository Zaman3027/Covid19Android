#!/usr/bin/env python
# coding: utf-8

# In[1]:


# Importing the modules and packages

import pandas as pd
from flask import jsonify
from pandas import json_normalize
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import requests
import json
from datetime import date, datetime
import flask

app = flask.Flask(__name__)
app.config["DEBUG"] = True


def set_default(obj):
    if isinstance(obj, set):
        return list(obj)
    raise TypeError


# Extracting info directly from the APIs and converting them into json files

url = 'https://api.covid19india.org/data.json'

r = requests.get(url)
j = r.json()

url1 = 'https://api.covid19india.org/raw_data1.json'

r = requests.get(url1)
j1 = r.json()

url2 = 'https://api.covid19india.org/raw_data2.json'

r = requests.get(url2)
j2 = r.json()

url3 = 'https://api.covid19india.org/raw_data3.json'

r = requests.get(url3)
j3 = r.json()

url4 = 'https://api.covid19india.org/raw_data4.json'

r = requests.get(url4)
j4 = r.json()

url5 = 'https://api.covid19india.org/state_test_data.json'

r1 = requests.get(url5)
j5 = r1.json()

url6 = 'https://api.covid19india.org/states_daily.json'

r = requests.get(url6)
j6 = r.json()

# In[3]:


# Dataframe from url containing the daily and total cases 

cases = pd.DataFrame.from_dict(json_normalize(j, record_path='cases_time_series'))

# Dataframe containing the statewise cases

states = pd.DataFrame.from_dict(json_normalize(j, record_path='statewise'))

# DataFrame containg the info regarding the testing done

testing = pd.DataFrame.from_dict(json_normalize(j, record_path='tested'))

# In[4]:


# DataFrames from url1

patient1 = pd.DataFrame.from_dict(json_normalize(j1, record_path='raw_data'))

# In[5]:


# DataFrames from url2

patient2 = pd.DataFrame.from_dict(json_normalize(j2, record_path='raw_data'))

# In[6]:


# DataFrames from url3

patient3 = pd.DataFrame.from_dict(json_normalize(j3, record_path='raw_data'))

# In[7]:


# DataFrames from url4

patient4 = pd.DataFrame.from_dict(json_normalize(j4, record_path='raw_data'))

# In[8]:


# Merging all the dataframes to build the master dataframe for the patient info

patient = pd.DataFrame()

# Info about the live patient details

patient = patient1.append(patient2).append(patient3).append(patient4).reset_index(drop=True)

# Depiction of the patients information by Gender
state_testing = pd.DataFrame.from_dict(json_normalize(j5, record_path='states_tested_data'))
states_daily = pd.DataFrame.from_dict(json_normalize(j6, record_path='states_daily'))

g = patient['gender'].value_counts()
g = g.reset_index()


@app.route('/gender_ratio', methods=['GET'])
def get_gender_radio():
    return jsonify({
        'Male': int(g['gender'].iloc[1]),
        'Female': int(g['gender'].iloc[2])
    })


# fig, ax = plt.subplots(figsize=(15, 8))
#
# ax.pie(x, labels=y, startangle=90, autopct='%.1f%%', colors=['orange', 'c'], textprops={'fontsize': 15},
#        explode=[0, 0.1])
# ax.set_title('Patients Info by Gender(Sample Size = 14,367) ', fontsize=25)

# In[10]:


# Manipulating the patient dataframe to change the age values to the age ranges

patient['agebracket'] = patient['agebracket'].str.strip().replace('', np.nan).replace('28-35', '32').replace('6 Months',
                                                                                                             '0.5')
patient['agebracket'] = patient['agebracket'].astype('float')
sample = len(patient.agebracket) - patient['agebracket'].isna().sum()

plt.style.use('ggplot')
fig, ax = plt.subplots(figsize=(10, 6))
data = ax.hist(patient['agebracket'], color='green', rwidth=0.9)


@app.route('/getAgeSample')
def get_age_sample():
    data_to_array = []
    for i in range(len(data[0])):
        data_to_array.append({'range': "".join((str(i * 10), "-", str((i + 1) * 10))), 'value': int(data[0][i])})
    return jsonify(data_to_array)


# Manipulating the 'date' column of the cases dataframe in order to make it depictable

cases['date1'] = '2020'
cases['Date'] = cases['date'].str.cat(cases['date1'])
cases['Date'] = pd.to_datetime(cases['Date'])

# In[13]:


# Depiction of the total confirmed, recovered and death cases

c = cases.groupby('Date')[['totalconfirmed', 'totaldeceased', 'totalrecovered']].sum()

c['totalconfirmed'] = c['totalconfirmed'].astype('int')
c['totaldeceased'] = c['totaldeceased'].astype('int')
c['totalrecovered'] = c['totalrecovered'].astype('int')
c['totalactive'] = c['totalconfirmed'] - (c['totaldeceased'] + c['totalrecovered'])

x = c.index
y1 = c['totalconfirmed']
y2 = c['totaldeceased']
y3 = c['totalrecovered']
y4 = c['totalactive']

totalconfirmed = []
totaldeceased = []
totalrecovered = []
totalactive = []

for i in range(len(x)):
    totalconfirmed.append({
        'date': str(x[i]),
        'confirmed': str(y1[i]),
        'recovered': str(y3[i]),
        'deceased': str(y2[i]),
        'active': str(y4[i])
    })


@app.route('/gettotalcase')
def gettotalcase():
    return jsonify(totalconfirmed)


df_daily = cases.groupby('Date')[['dailyconfirmed', 'dailydeceased', 'dailyrecovered']].sum()

df_daily['dailyconfirmed'] = df_daily['dailyconfirmed'].astype('int')
df_daily['dailydeceased'] = df_daily['dailydeceased'].astype('int')
df_daily['dailyrecovered'] = df_daily['dailyrecovered'].astype('int')

dx = df_daily.index
dy1 = df_daily['dailyconfirmed']
dy2 = df_daily['dailydeceased']
dy3 = df_daily['dailyrecovered']

dailyconfirmed = []


for i in range(len(x)):
    dailyconfirmed.append({'date': str(dx[i]),
                           'confirmed': str(dy1[i]),
                           'recovered': str(dy3[i]),
                           'deceased': str(dy2[i])
                           })


@app.route('/getdailycase')
def getdailycase():
    return jsonify(dailyconfirmed)


t = testing[['updatetimestamp', 'totalsamplestested', 'testspermillion']]
t.updatetimestamp = pd.to_datetime(t['updatetimestamp'], format='%d/%m/%Y %H:%M:%S')

t.set_index('updatetimestamp', inplace=True)

t.totalsamplestested = t['totalsamplestested'].str.strip().replace('', np.nan).astype('float')
t.testspermillion = t['testspermillion'].str.strip().replace('', np.nan).astype('float')

t1 = t.totalsamplestested.resample('SM').sum()
t2 = t.testspermillion.resample('SM').mean()

t = pd.concat([t1, t2], axis='columns')


@app.route('/getTestPerMil', methods=['GET'])
def getTestPerMil():
    test_per_mil = []
    for i in range(len(t.index)):
        test_per_mil.append({
            "Date": str(t.index[i]),
            "totalsamplestested": str(t.totalsamplestested[i]),
            "testspermillion": str(t.testspermillion[i])
        })
    return jsonify(test_per_mil)


s = state_testing[['state', 'updatedon', 'testspermillion']]
s.testspermillion = s['testspermillion'].str.strip().replace('', np.nan).astype('float')
s = s.groupby('state')['testspermillion'].mean()


@app.route('/statepermill', methods=['GET'])
def statepermill():
    data_list = []
    for i in range(len(s.index)):
        data_list.append({"State": str(s.index[i]),
                          "value": str(s[i])
                          })

    return jsonify(data_list)


states_daily.date = pd.to_datetime(states_daily.date)

d = {'an': 'andaman and nicobar islands', 'ap': 'andhra pradesh', 'ar': 'arunachal pradesh', 'as': 'assam',
     'br': 'bihar', 'ch': 'chandigarh',
     'ct': 'chhattisgarh', 'dd': 'daman and diu', 'dl': 'delhi', 'dn': 'dadra and nagar haveli', 'ga': 'goa',
     'gj': 'gujrat', 'hp': 'himachal pradesh',
     'hr': 'haryana', 'jh': 'jharkhand', 'jk': 'jammu and kashmir', 'ka': 'karnataka', 'kl': 'kerela', 'la': 'ladakh',
     'ld': 'lakshadweep',
     'mh': 'maharashtra', 'ml': 'meghalaya', 'mn': 'manipur', 'mp': 'madhya pradesh', 'mz': 'mizoram', 'nl': 'nagaland',
     'or': 'odisha',
     'pb': 'punjab', 'py': 'puducherry', 'rj': 'rajasthan', 'sk': 'sikkim', 'tg': 'telangana', 'tn': 'tamil nadu',
     'tr': 'tripura',
     'up': 'uttar pradesh', 'ut': 'uttarakhand', 'wb': 'west bengal'}

states_daily.rename(columns=d, inplace=True)

c = np.array(states_daily.columns)
c = np.delete(c, [7, 32, 36, 37])

states_daily_choice = pd.pivot_table(states_daily, values=c, index='date', columns='status', aggfunc=np.sum)

df_state_tes = state_testing[['state', 'updatedon', 'totaltested']]
df_state_tes.totaltested = df_state_tes['totaltested'].str.strip().replace('', np.nan).astype('float')
df_state_tes.updatedon = pd.to_datetime(df_state_tes['updatedon'], format='%d/%m/%Y')
df_state_tes = df_state_tes.groupby(['state', 'updatedon'])['totaltested'].sum()


@app.route('/getstatedata/<statename>', methods=['GET'])
def getstatedata(statename):
    x = states_daily_choice.index
    state_choice_y1 = states_daily_choice[statename].iloc[:, 0].astype('int64').cumsum()
    state_choice_y2 = states_daily_choice[statename].iloc[:, 1].astype('int64').cumsum()
    state_choice_y3 = states_daily_choice[statename].iloc[:, 2].astype('int64').cumsum()
    state_choice_y4 = y1 - (y2 + y3)
    x_state_tes = df_state_tes[statename.title()].index
    y_state_tes = df_state_tes[statename.title()]

    data_list = []
    data_list_test = []
    for i in range(len(x)):
        data_list.append({
            "date": str(x[i]),
            "Confirmed": str(state_choice_y1[i]),
            "Deaths": str(state_choice_y2[i]),
            "Recovered": str(state_choice_y3[i]),
            "Active": str(state_choice_y4[i])
        })

    for i in range(len(x_state_tes)):
        data_list_test.append({
            'date': str(x_state_tes[i]),
            'value': str(y_state_tes[i])
        })

    return jsonify({
        'stateCase': data_list,
        'stateTest': data_list_test
    })


@app.route('/', methods=['GET'])
def home():
    return jsonify({"Hello": "World"})


app.run(host='0.0.0.0')
