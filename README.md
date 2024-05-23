# Stock Management

## Description

This is a stock management application for a store, developed in Java. The application allows users to perform various actions and queries related to products, orders, distributors, and stocks.

### Actions / Interrogations:

* Employee:
  * Display all Distributors
  * List Products from a Distributor
  * Place an Order
  * Display all Orders
  * Display Order Details
  * Display Order Status

* Distributor: (needs to connect with email & password)
  * Order Management:
    * Display all Orders
    * Update Order status
    * Delete Order
  * Product Management:
    * Display all Products  
    * Add Product
    * Modify Product Quantity
    * Delete Product


### Types of Objects:

* Product
    * ElectronicProduct
    * FoodProduct
    * ProductFactory
* Distributor
  * DistributorFactory
* Address
* Order
* Main 
* MainService
* DistributorService
* OrderService
* ProductService