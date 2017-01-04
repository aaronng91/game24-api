# Game24 API

The API for Game24, written in Spring Boot

Exposes websocket endpoints for live updates
 - `/refresh`: Generate a random set of cards and publishes it to `/topic/cards`

Subscribing to `/topic/cards` will return the current set of cards and listen to any new card value changes
