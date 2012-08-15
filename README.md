SocialtextPOC
=============

Hadoop POC using data harvested from Socialtext.

Resources
=============
* https://www.socialtext.net/open/
* https://www.socialtext.net/st-rest-docs/offset_and_limit_query_parameters
* https://www.socialtext.net/st-rest-docs/authentication

Initial story
===============
Run Clustering algorithm on the list of Socialtext Signals to group the signals into categories.

Master plan
=============

A. Get raw data
* get data from Socialtext REST API in json format
* store the raw (json) data in hdfs (or hbase)

B. Prepare data
* filter and transform raw (json) data. keep only what we need
* vectorize data for machine learning algorithms
* normalize vectors to optimize machine learning results

C. Clustering
* run the machine learning algorithm(s) for clustering the data

Todo
======
* create skeleton POC
* list users with Socialtext's REST api
* list signals with Socialtext's REST api

Socialtext REST api
====================
* https://cegeka.socialtext.net/data/users
* https://cegeka.socialtext.net/data/signals
