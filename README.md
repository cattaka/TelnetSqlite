TelnetSqlite
============


## Want to peek SQLiteDatabase of non-root device

SQLiteDatabase of non-root device cannot be peeked in the development of Android apps. 
In a strict sense it is not be able to be peeked for security reason.
I wanted to run SQL directly during development. 
So I made a way to peek into force. 


## How? 

Embedding a small program that completed in one class into the app side.
I was to be able to access there via. 
Something like a back door short. 
Note that it should be removed in release build.


## Sample application

Application that was used in the description will be here. 
MyApplication.java and build.gradle of this application is target. 

* [FastCheckList] (https://github.com/cattaka/FastCheckList) 


## How to use 

The usage is the following three steps. 

* Embed a small program 
* Add code to kick the embedded program in the Application#onCreate method
* Execute the telnet and run SQL via small program

Let's take a look one by one. 

### Embed a small program 

Add the following line to the dependancies of build.gradle. 

`` `groovy 
     debugCompile 'net.cattaka: telnetsqlite: 1.0.0' 
`` `

You should use debugCompile because it is only for debugging purposes. 
Please keep in mind that if you use "compile" instead of "debugCompile", it would be included in the release build. 


### Add code to kick the embedded program in the Application#onCreate method

Add the code like the following to the custom application class. 
If you do not have a custom application class, please create it.

```java
public class MyAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Start server to access SQLite(only for development code)
        try {
            Class<?> clazz = Class.forName("net.cattaka.android.fastchecklist.TelnetSqliteService$TelnetSqliteServer");
            Thread serverThread = (Thread)clazz.getConstructor(Context.class, int.class).newInstance(this, 12080);
            serverThread.start();
        } catch (Exception e) {
            // ignore
        }
    }
}
```

A value of 12080 when generating a serverThread is the port number of the server. 
Please change the appropriate value if necessary. 
It is using reflection in order to avoid a compilation error in the release build.


