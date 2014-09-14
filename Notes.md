Implementation Notes
--------------------

* start with a single table
  * [done] keep columns with meta data, from load/format
  * [done] keep content of columns
  * derive meta data from content (date or id etc)
  * [done] know some facts about columns (number of unique values), which is sort of statistical data
  * [done] aggregate fact/HBase primary column name idea, use `<table>_<smallest dimension>_<next>` and so on.
  * automatically de-normalise using each table as fact once, "for all tables"

* have a table with a foreign key
  * [done] expand columns of foreign table into table
  * [done] mark new columns as meta data from original table

* table with more (simple) foreign keys
  * recursively

* m2n
  * tbd

* how to export the de-normalised data?
  This is the goal. needs to be pre-aggregated and sorted by different aggregators counts descending
  if any groups are found, then use each group

Dimensions
----------

For larger tables we have different ways to create ID columns because we have different dimensions to aggregate for.

* predict dimensions/auto-detect dimension by variation of column values
* find columns that always change together e.g. "zip and city" or "city and country" (same dimension)
* dimension auto-detect by typical data, e.g. time-stamp, geo-coordinates, city/county name? (need dictionary)
* -> have list of dimensions with group of columns for each dimension each, maybe overlapping, 
  i.e. single a column is in more than one dimension, because we can not know for sure.
* sort columns of dimensions by unique values, as already done
* detect if a unique column is a subset of another unique column and sort these into groups (this is more than just ordering by count)

Then
  
* aggregations following each dimension
* ID Column, reduce for the key, only use what is relevant for the current dimension, in order of # uniqueness
* iterate all dimensions and create ID Columns each
* if there are more non-unique but in not overlapping dimensions, create different variations of id columns = multiple tables
