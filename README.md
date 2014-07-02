denormalize-db
==============

eArk WP6 - reference implementation: generate csv from archived db

TODOs
-----
- get archived db format
- understand given format (XML/CSV) and load
- automatically understand foreigh keys, create data model using given sample data
- denomralizze automatically using each table as facts once
- and/or create star schema
- experiment with ways to autmatically follow foreigh keys
- see what we need to have real star schema
- define requireemts for AIP level 2 (Manfred/HKI) to be useful

Future
------
- see if zohmg or Mondrian can help analysing the data
- how to we need to put data into it?
- maybe run a Mondrian on each node using a different table as facts
- send querys to CHX and Solr and HBase parallel and reduce results for user
- understand other formats like SIARD and EPP and HKI defined one.
