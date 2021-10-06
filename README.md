# development-books-kata
Code Kata for BNP Paribas Fortis application

# Running the application

To compile run mvn clean install

Execute the following command:
mvn spring-boot:run
or simply import project and run in your IDE.

# API description

The endpoint to calculate the books can be accessed on http://localhost:8081/calculatePrice
It is a POST request using the following structure:

````
[
    {
        "book":{
            "title": "Clean Code",
            "author": "Robert Martin",
            "year": 2008
    },
        "count": 5
    },
    {
        "book":{
            "title": "Test Driven Development by Example",
            "author": "Kent Beck",
            "year": 2003
        }
    }
]
````

The "count" variable is optional. Count expresses the total count/amount of the given book. If no count is given, 1 item of the book will be assumed.
Multiple combinations of the same book (with or without count) are accepted and will be added up in the final calculation.
The output is a response with details of the total price of the books - for example:

````
{
    "finalPrice": 240.0,
    "basePrice": 250.0,
    "totalDiscount": 10.0,
    "totalBooks": 5
}
````

To get the list of available books use a GET request on http://localhost:8081/books. 
This will return a list like this:

````
[
    {
        "title": "Clean Code",
        "author": "Robert Martin",
        "year": 2008
    },
    {
        "title": "The Clean Coder",
        "author": "Robert Martin",
        "year": 2011
    },
    {
        "title": "Clean Architecture",
        "author": "Robert Martin",
        "year": 2017
    },
    {
        "title": "Test Driven Development by Example",
        "author": "Kent Beck",
        "year": 2003
    },
    {
        "title": "Working Effectively With Legacy Code",
        "author": "Michael C. Feathers",
        "year": 2001
    }
]
````

The books are imported in the application from in an in memory h2 database. 
See the data.sql script to see the initial loaded data.


# Pricing rules

The pricing rules are configurable in the application.yml file

Current pricing rules:

One copy of the five books costs 50 EUR.
If, however, you buy two different books from the series, you get a 5% discount on those two books.
If you buy 3 different books, you get a 10% discount.
With 4 different books, you get a 20% discount.
If you go for the whole hog, and buy all 5, you get a huge 25% discount.
Note that if you buy, say, 4 books, of which 3 are different titles, you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR.