"# ServerB" 

Sequence of events for friendship:

1: Server A sends POST request to Server B
- In the body of the POST Request there is information about the person requesting friendship, and the recipient
- This will be in the form of: HOST IP | Requester ID | DESTINATION IP | Recipient User ID
- Server B will (Hopefully) send back a 200 response code
- When the 200 response code is received, both servers will create a pending request in the request database, that has a status of "pending"

2: When User logs into Server B, Server B will query the database to check if there are any requests for the current User's ID.
- If there are requests, the User will be notified of the request, and can choose to accept or decline.

	- If a User ACCEPTS the friendship request:
		- Server B will create an entry in Server B's Friendship database that the request has been accepted (start of transaction).
		- Server B will send a POST request to Server A, with confirmation of the acceptance in the body.
			- The Body will include the same information as the initial request in the same format.
		- Server A will (hopefully) send a 200 response code to Server B and create a PUT request in Server A, to add the friendship to Server A's friendship database.
		- Server B will check that the response code is 200. 
			- If the response code is 200, the transaction will complete and the friendship will be saved in both Servers' friendship databases.
			- If the response code is not 200 (e.g. response code 400), the transaction will be reverted, and the friendship will be removed from Server B's database.
		- Server A and Server B will both create a DELETE request for their own servers to remove the request from the requests database.

	- If a User DECLINES the friendship request:
		- Server B will create a POST request to Server A, confirming that the request has been declined (start of transaction).
			- The request details will be in the body of the POST, as in the initial request.
		- Server B will create a DELETE request for it's requests database to remove the pending request.
		- Server A will (hopefully) send a 200 response code to Server B to confirm they have received the POST request.
			- At this point, Server A will also generate a DELETE request for it's own requests database, to remove the pending request from its' requests database.
		- The request will be deleted from both Servers' requests database.
		- The friendship will NOT be added to Server A or B's friendship database.
			