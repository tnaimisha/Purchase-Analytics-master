This test deals with the inputs where random junk characters are present in the required fields like productId, departmentId, reordered etc.

products.csv-
1. Junk characters present in first and last fields are ignored. 
2. Product name and aisleId can have alphanumeric and other character values in them. So, the records are not filtered based on these fields.

orders file-
1. All the fields are expected to be integer fields and all faulty records which do not comply are filtered
