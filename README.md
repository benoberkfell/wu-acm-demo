# Bestsellers

## Disclaimer

This is totally quick and dirty but should be pretty illustrative of the basic concepts!

## What's here

This is a demo app for the WU ACM Hack Day to illustrate how to access a REST API using 
Retrofit and display it in a RecyclerView.

This app fetches the various New York Times best seller book lists and allows you to 
view the ranked books within each one.  Tap a book to view its Amazon page.

## Prerequisites

In order to build this app you will need to set the API key.

1) Obtain an NYT API key from [developer.nytimes.com](https://developer.nytimes.com/), for the [Books API](https://developer.nytimes.com/books_api.json).

2) Create a file `secrets.properties` at the base of this project, where its contents are just `NYT_API_KEY=yourapikeyhere`.
