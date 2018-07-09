# Hazelcast Demo
This code is taken from the Udemy course  [Lightning fast memory grids using Hazelcast IMDG](https://www.udemy.com/lightning-fast-memory-grids-using-hazelcast-imdg/learn/v4/overview).
Since the original code had performance issues while populating the cache from the MySQL database i tried various options to improve the speed of the database operations.
Original code used the JPA (Hibernate) to read the set of the six tables (one master and five slave tables with one-to-many relationships).
Unfortunately, i never managed to load the whole data set (circa 120K records) as even after couple of hours it was still halfway through on the relatively powerful MacBook Pro.

## Database
I used the docker MySQL image to run the database. In order to spin up the new instance just
~~~~
cd database; docker-compose up -d
~~~~
You can omit the `-d` option in this case docker will run in console mode hence to stop the instance just hit `Ctrl+C`.
With `-d` option in order to stop the MySQL (and destroy data) run the following command (still in the `database` directory):
```
docker-compose down
```
Database will be populated every time the container is started.
