# Purchase-Analytics

## Table of Contents
1. [Approach](README.md#Approach)
2. [Preprocessing](README.md#Preprocessing)
2. [Dependencies](README.md#Dependencies)
3. [Run Instructions](README.md#Run-Instructions)

## Approach

This code uses Java for doing the purchase analytics of instacart data.

Data strcutures used - 
* 2 Hashmaps - 1 for product_id:department_id mapping and the other for the order counts for each department_id.
* Priority Queue(heap) - for sorting the data in second hashmap based on department_id

We have two files products.csv and order_products.csv
* Step 1 - Products.csv has the department_id for each product_id. In step 1, this file is read line by line and checked for junk data. After preprocessing, the product_id:department_id pairs are put in a hashmap.
* Step 2 - From the order_products.csv file, we use product_id and reordered fields. Like in step 1, this file is read line by line and checked for junk data. After preprocessing, for each product_id, we fetch the corresponding department_id and update the total orders and first orders accordingly in another Hashmap.
* Step 3 - After all the records are processed, the data in the hashmap is sorted based on department_id.
* Step 4 - We build one huge string for all the lines in the output file. Each record in the sorted output is checked, if the order count > 0, percentage is calculated and the fields are appened to the string. The entire string is pushed to the output file at once.

## Preprocessing

* Handles multiple double quotes, multiple commas than the number of fields in the files
* Handles missing records/mappings in products.csv file
* Handles junk data in all the fields in order_products.csv and only the product_id and department_id fields in products.csv file. This is with the assumption that product_name and aisle_id can have alphanumeric values(we see aisle_ids in alphabetical, numerical or a combination of both in the usual stores like Safeway)

* Future improvements - row level duplicates in orders file. Duplicates in products file doesn't impact the output. But duplicates can be possible in orders file and can lead to misleading order counts. 
We can implement this if we have more information on the orders file - For example, a user can place two orders for the same product in the same order. This is a valid scenario.

## Dependencies


## Run Instructions

