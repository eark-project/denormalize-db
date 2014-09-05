denormalize-db
==============

eArk WP6 - reference implementation: generate CSV from archived database

Provide some kind of DWH functionality for archived databases. There are
various formats of archived databases, e.g. SIARD. These are still sort
of relational by providing meta-data on the original relations.
The first step/idea is to inline all relations into a huge, de-normalised
table which can be exported as CSV or loaded into HBase later.

See also [Implementation Notes](Notes.md).

Next Steps
----------
- HBase export
- id column
- try multiple FKs recursive

TODOs
-----
- get archived database format: Estonian buildings database
- understand given format (XML/CSV) and load
- create data model using given sample data
- understand foreign keys 
- experiment with ways to automatically follow foreign keys
- automatically de-normalise using each table as facts for once
- and/or create star schema
- see what we need to have real star schema
- define requirements for AIP level 2 (Manfred/HKI) to be useful

Future Ideas
------------
- see if zohmg or Mondrian can help analysing the data
- how to we need to put data into it?
- maybe run a Mondrian on each node using a different table as facts
- send queries to CHX and Solr and HBase parallel and reduce results for user
- understand other formats like SIARD and EPP and HKI defined one.

Design
------
- See [src/main/java/org/eu/eark/denormalizedb/model/package-info.java](Model)

Test Data
----------
- See [src/test/java/org/eu/eark/infrastructure/sqlite/SakilaTest.java](Sakila)

Resources
---------
- http://hstack.org/hbasecon-low-latency-olap-with-hbase/
- http://msdn.microsoft.com/en-us/library/cc505841.aspx
