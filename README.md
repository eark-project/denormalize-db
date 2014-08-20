denormalize-db
==============

eArk WP6 - reference implementation: generate csv from archived db

TODOs
-----
- get archived db format: Estonian buildings database
- understand given format (XML/CSV) and load
- automatically understand foreign keys, create data model using given sample data
- experiment with ways to automatically follow foreign keys
- automatically denormalize using each table as facts for once
- and/or create star schema
- see what we need to have real star schema
- define requirements for AIP level 2 (Manfred/HKI) to be useful

Future
------
- see if zohmg or Mondrian can help analysing the data
- how to we need to put data into it?
- maybe run a Mondrian on each node using a different table as facts
- send queries to CHX and Solr and HBase parallel and reduce results for user
- understand other formats like SIARD and EPP and HKI defined one.

Ideas
-----
- aggregations following each dimension
- dimension auto-detect by variation of column values
- dimension auto-detect by typical data, e.g. timestamp, geo-coord, city/county-name? (need dictionary)
