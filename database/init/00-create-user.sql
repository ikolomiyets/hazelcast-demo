CREATE DATABASE customer;
create user 'demo' IDENTIFIED BY "password";
GRANT ALL PRIVILEGES ON customer.* to demo;
flush privileges;

