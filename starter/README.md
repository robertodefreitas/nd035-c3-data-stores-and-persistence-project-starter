# Purpose of this Folder

I am an udacity student and in this folder is my solution available.

## Some information / Glossar
### DTO (Data Transfers Object)
* DTO is an object that carries data between processes. When you're working with a remote interface, each call it is expensive. As a result you need to reduce the number of calls. The solution is to create a Data Transfer Object that can hold all the data for the call. It needs to be serializable to go across the connection. Usually an assembler is used on the server side to transfer data between the DTO and any domain objects. It's often little more than a bunch of fields and the getters and setters for them.
* An object that carries data between processes in order to reduce the number of method calls means you combine more than one POJO entities in service layer.
* For example a GET request `/rest/customer/101/orders` is to retrieve all the orders for customer id 101 along with customer details hence you need combine entity Customer and entity Orders with details.

### DAO (Data Access Object)
* An object that provides a common interface to perform all database operations like persistence mechanism.
* A Data Access Object abstracts and encapsulates all access to the data source. The DAO manages the connection with the data source to obtain and store data.
* The DAO implements the access mechanism required to work with the data source. The data source could be a persistent store like an RDBMS, or a business service accessed via REST or SOAP.
* The DAO abstracts the underlying data access implementation for the Service objects to enable transparent access to the data source. The Service also delegates data load and store operations to the DAO.
* _Nur als Beschreibung für das Verständnis, um nicht mit DTO durcheinander zu kommen. Ich selbst habe keine DAO Object bei dem Projekt verwendet bzw. gefunden.

## Quellen
* https://www.maibornwolff.de/blog/von-schichten-zu-ringen-hexagonale-architekturen-erklaert
* https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/
* https://www.javacodegeeks.com/2012/09/spring-dao-and-service-layer.html
* https://stackoverflow.com/questions/35078383/what-are-the-dao-dto-and-service-layers-in-spring-framework
