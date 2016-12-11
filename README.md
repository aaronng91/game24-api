# Game24 API

The API for Game24, written in Spring Boot

Exposes the following endpoints that is called on load:

 - `/cards`: Gets initial set of 4 cards

Exposes websocket endpoints for live updates
 - `/refresh`: Generate a random set of cards and publishes it to `/topic/cards`
