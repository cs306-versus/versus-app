## How the user is stored in the DataBase

We use [Cloud Firestore](https://firebase.google.com/docs/firestore) as our Database. 
Users are stored in their their own [collection](https://firebase.google.com/docs/firestore/data-model#collections) called **`users`**.

In the said collection, each user is stored in a [document](https://firebase.google.com/docs/firestore/data-model#documents).
The name of the said document is the **UID** returned from the authenticator.

The structure of a user's document is as follow :
``` tree
|- first-name (string)
|- last-name (string)
|- username (string)
|- mail (string)
|- phone (number)
|- rating (number)
|- city (string)
|- zip (number)
|- preferred-sports (array)
    |- ... (string)
```