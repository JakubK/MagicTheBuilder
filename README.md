# MagicTheBuilder
The app allows You to browse available MTG cards. It supports a search function with many filters and sorting. You can also save cards to your own collection. 
Deck creation functionality is also available, with a built-in system for validating deck legality in a given format.

## How to run it

### Prerequisites
- project cloned/downloaded,
- [docker](https://docs.docker.com/get-docker/) installed.

To run the project use the following command in the main project folder:
```
docker compose up
```
By default, it'll try to load card data from the allCards file, which contains serialized card data from June 2022.
You can turn it off by setting the READ_CARDS_FROM_FILE environment variable to true in docker-compose.yml. Note that downloading cards from Gatherer instead will increase startup time by about 11 minutes (may vary).

The app will be then available on 
```
http://localhost
```
in your browser.
