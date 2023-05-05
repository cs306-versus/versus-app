# Firebase Authentication Emulator

## What data to store in the emulator

We will use the following convention for user's mail to test Versus authentication:

- User stored in the database : **auth.user+{POSITIVE_INTEGER}@test.versus.ch**. All the users following the convention above will have for password : `123456789`;
- User that cannot be in the database : **auth.user-{POSITIVE_INTEGER}@test.versus.ch**;
- User that a testing suite can create should have the following mail : **auth.user#{POSITIVE_INTEGER}@test.versus.ch**.

See [EmulatorUserProvider.java](../../app/src/androidTest/java/com/github/versus/utils/auth/EmulatorUserProvider.java)


