## Webshop in Spring Boot

Simple web shop done in Spring Boot. Users can register and log in. Add items to the shopping cart and place orders. Administrators can view orders and mark them as dispatched.

Admin features are also available as a REST API.


## Technologies used

* Java 8
* Spring Boot
* MariaDB SQL-database

* MVC design pattern (model-view-controller)
* Spring Security (authorization & authentication)
* JPA / Hibernate

* ORM (object relational mapping)
* Encrypted passwords (BCrypt)
* REST API

* Thymeleaf
* HTML5 & CSS
* Maven
* JUnit & Mockito (unit testing)

##Minimal functional requirements
(text by Ulf Bilting, 2019). Translation from Swedish :sweden: by Google Translate.

"The customer must be met by a login page where they can register as a new customer or log in as
previously registered.

After logging in, the customer should be offered categories of goods, as well as a search function on
product name.

A product list must be presented and the customer should be able to put goods in their shopping basket and then enter the number of copies of the item.

The shopping basket must be able to be updated with changes in the number and removal of a particular item respectively.

The shopping cart must show the total of all the goods price.

Once the customer has filled the shopping basket, it should be able to be checked out and the customer should receive a confirmation page showing the placed order. This order has also been stored in the database so that the webshop's warehouse staff can start dispatching it.

The web shop's staff must have their own permanent administrator login through which they must
be able to see web pages for un-dispatched orders, dispatched orders, mark an order as dispatched and add new products to the product register.

These admin services should also be available as a REST interface. Tests should be available for key components, such as shopping cart. They do not need to be comprehensive, but just a few. This shows that the component in question is testable.

The amount of info in customer, product, shopping basket and order items etc. need not be as comprehensive as in a realistic webshop."
